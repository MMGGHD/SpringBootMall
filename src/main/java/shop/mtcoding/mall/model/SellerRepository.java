package shop.mtcoding.mall.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


@Repository
public class SellerRepository {

    @Autowired
    private EntityManager em;

    @Transactional
    public void saveSeller(String name, String email) {
        System.out.println("saveSeller 메서드 실행 됨");
        Query query = em.createNativeQuery("insert into seller_tb(name, email) values(:name, :email)");
        query.setParameter("name", name);
        query.setParameter("email", email);
        query.executeUpdate();
    }


    public Seller FindByName(String name) throws NoResultException {
        System.out.println("FindBySellerName 메서드 실행 됨");
        Query query = em.createNativeQuery("select * from seller_tb where name = :name", Seller.class);
        query.setParameter("name", name);
        Seller seller = (Seller) query.getSingleResult();
        return seller;

    }
}
