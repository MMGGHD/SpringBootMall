package shop.mtcoding.mall.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

// 상세보기와 같은 기능을 테스트 하려면 더미데이터 등록, 목록보기 등 이전 과정이 필요하다.


//@Rollback(false) << Test메서드는 끝나면 Rollback되기 때문에 Rollback을 false
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) << 가짜 DB말고 찐DB를 써서 Test 하겠다.
// 위 2개는 h2 DB를 쓰면 안붙여야 함
// * h2 : 메모리 데이터 베이스로, Rollback을 자동 수행

// ProductRepository에있는 메서드를 시험하기 때문에 Import

@Import({
    ProductRepository.class,
    SellerRepository.class
})
// Tomcat -> DS -> Contller -> Reps -> DB 에서 Reps이하 과정 (Reps-DB) 만 테스트하기 위해 아래 설정
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    // @Autowired 어노테이션은 각각 달아줘야 한다.
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    public void findByIdDTO() {
        // Given (테스트를 하기 위해서 필요한 데이터 만들기)
        productRepository.save("바나나", 5000, 50);

        // When (테스트 진행)
        ProductDTO productDTO = productRepository.findByIdDTO(1);
        System.out.println(productDTO.getId());
        System.out.println(productDTO.getName());
        System.out.println(productDTO.getPrice());
        System.out.println(productDTO.getQty());
        System.out.println(productDTO.getDes());

        // Then (테스트 확인)
    }

    @Test
    public void saveSellerTest() {
        sellerRepository.saveSeller("홍길동", "ssar@nate.com");
    }

    @Test
    public void findByIdJoinSeller_test() {
        // Given (테스트를 하기 위해서 필요한 데이터 만들기)
        sellerRepository.saveSeller("홍길동", "ssar@nate.com");
        sellerRepository.saveSeller("임꺽", "ssar@naver.com");
        productRepository.saveWithFK("바나나", 5000, 50, 1);
        productRepository.saveWithFK("딸기", 4564, 22, 1);
        productRepository.saveWithFK("사과", 5000, 50, 2);
        productRepository.saveWithFK("참외", 5000, 50, 2);

        // When (테스트 진행)
        List<Product> productList = productRepository.findByIdJoinSeller(2);

        // Then (테스트 확인)
        for (Product product : productList) {
            System.out.println("=================");
            System.out.println(product.getId());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getQty());
            System.out.println(product.getSeller().getId());
            System.out.println(product.getSeller().getName());
            System.out.println(product.getSeller().getEmail());
        }System.out.println("=================");
    }
    // 여기서 Rollback되어 DB초기화

    @Test
    public void findById_test(){
        // Given (테스트를 하기 위해서 필요한 데이터 만들기)
        sellerRepository.saveSeller("홍길동", "ssar@nate.com");
        productRepository.saveWithFK("바나나", 5000, 50, 1);

        // When (테스트 진행)
        Product product = productRepository.findById(1);

        // Then (테스트 확인)
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQty());
        System.out.println("=========="+product.getSeller().getId());
        System.out.println("=========="+product.getSeller().getName());
        System.out.println("=========="+product.getSeller().getEmail());
    }
    // 여기서 Rollback되어 DB초기화


    @Test
    public void findAll_test() {
        // Given (테스트를 하기 위해서 필요한 데이터 만들기)
        productRepository.save("바나나", 5000, 50);
        productRepository.save("바나나", 4000, 40);

        // When (테스트 진행)
        List<Product> productList = productRepository.findAll();

        // Then (테스트 확인)
        for (Product product : productList) {
            System.out.println("==============");
            System.out.println(product.getId());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getQty());
        }
        System.out.println("==============");
    }

    @Test
    public void FindByName() {
        sellerRepository.saveSeller("홍길동", "aaa.naver.com");
        Seller seller = sellerRepository.FindByName("홍길동");
        System.out.println("seller id : "+seller.getId());
    }
}
