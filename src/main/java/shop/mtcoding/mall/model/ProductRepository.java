package shop.mtcoding.mall.model;

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
    private EntityManager em;

    @Transactional
    public void save(String name, int price, int qty) {
        // DB에 적용되는 쿼리이자 버퍼, 캡슐화된 포맷에 따라서 작성만 하면 된다.
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty) values(:name, :price, :qty)");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();
    }

    public List<Product> findAll() {
        Query query = em.createNativeQuery("select * from product_tb", Product.class);
        List<Product> productList = query.getResultList();
        return productList;
    }
}