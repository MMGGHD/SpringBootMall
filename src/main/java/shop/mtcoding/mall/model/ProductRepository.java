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
        // row가 1건
        // 1, 바나나, 1000, 50
        Object[] object = (Object[]) query.getSingleResult();
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
    public void updateById(int id, String name, int price, int qty){
        System.out.println("updateById 호출됨");
        Query query = em.createNativeQuery("Update product_tb Set name = :name, price = :price, qty = :qty Where id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();

    }
}
