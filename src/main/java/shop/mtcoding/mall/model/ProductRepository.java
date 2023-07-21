package shop.mtcoding.mall.model;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Repository // 컴포넌트 스캔
public class ProductRepository {

    @Autowired
    // EntityManager는 스프링 내부적으로 구현되있어서 가져다 쓰기만 하면 된다.
    // EntityManager는 DBConnection을 포함하고있는 객체이다.
    // EntityManager는 @Entity를 가진 객체만 Object Mapping을 해준다.
    private EntityManager em;

    // createNativeQuery(sql, 매핑할 클래스.class) 형식을 이용한다.
    public List<Product> findByIdJoinSeller(Integer id){
        Query query = em.createNativeQuery("select * from product_tb pt inner join seller_tb st on pt.seller_id = st.id where st.id = :id", Product.class);
        query.setParameter("id", id);
        List<Product> productList =  query.getResultList();
        return productList;
    }

    // 상세보기처럼 조회하는데 DB에 없는 des를 같이 뽑아 보는것 ( 기존의 createNativeQuery(sql, "매핑할 클래스") 를 이용하지 않는다.)
    // ProductDTO는 @Entity를 가지고 있지 않기 때문에 "매핑할 클래스"에 해당하지 않는다.
    // 상세보기와 같은 기능을 테스트 하려면 더미데이터 등록, 목록보기 등 이전 과정이 필요하다.
    public ProductDTO findByIdDTO(int id){
       Query query =  em.createNativeQuery("select id, name, price, qty, '설명' as des from product_tb where id = :id");
        query.setParameter("id", id);

        JpaResultMapper mapper = new JpaResultMapper();
        ProductDTO productDTO = mapper.uniqueResult(query, ProductDTO.class);

        return productDTO;
    }


    // Transactional << Transaction과정(연산후 commit 혹은 rollback)까지 다뤄줌
    // insert, update, delete등 DB를 수정하는 작업시 @Transactional달아준다
    @Transactional
    public void save(String name, int price, int qty) {
        // DB에 적용되는 쿼리이자 버퍼, 캡슐화된 포맷에 따라서 작성만 하면 된다.
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty) values(:name, :price, :qty)");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();
    }

    @Transactional
    public void saveWithFK(String name, Integer price, Integer qty, Integer sellerId) {
        // DB에 적용되는 쿼리이자 버퍼, 캡슐화된 포맷에 따라서 작성만 하면 된다.
        System.out.println("saveWithFK 메서드 실행됨");
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty, seller_id) values(:name, :price, :qty, :sellerId)");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.setParameter("sellerId", sellerId);
        query.executeUpdate();
    }


    public List<Product> findAll() {
        Query query = em.createNativeQuery("select * from product_tb", Product.class);
        List<Product> productList = query.getResultList();
        return productList;
    }

    public Product findById(int id) {
        Query query = em.createNativeQuery("select * from product_tb where id = :id", Product.class);
        query.setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        return product;
    }

    public Product findById2(int id) {
        Query query = em.createNativeQuery("select * from product_tb where id = :id");
        query.setParameter("id", id);
        // (Object[])는 컴파일시 JVM이 배열로 인식하게 하기위해 캐스팅 해 준것. Object[]가 Object의 하위자료형은 아님.
        Object[] object = (Object[])query.getSingleResult();
        int id2 = (int) object[0];
        String name2 = (String) object[1];
        int price2 = (int) object[2];
        int qty2 = (int) object[3];



        Product product = new Product();
        product.setId(id2);
        product.setName(name2);
        product.setPrice(price2);
        product.setQty(qty2);
        return product;
    }

    @Transactional
    public void deleteById(int id){
        System.out.println("deleteById 호출됨");
        Query query = em.createNativeQuery("delete from product_tb where id= :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public void updateById(Integer id, String name, Integer price, Integer qty, Integer sellerId){
        System.out.println("updateById 호출됨");
        Query query = em.createNativeQuery("Update product_tb Set name = :name, price = :price, qty = :qty, seller_id = :sellerId  Where id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.setParameter("sellerId", sellerId);
        query.executeUpdate();
    }
}
