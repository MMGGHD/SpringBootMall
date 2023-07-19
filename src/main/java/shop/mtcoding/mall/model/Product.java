package shop.mtcoding.mall.model;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "product_tb")
@Entity
public class Product {
    // model은 JPA를 통해 데이터베이스를 구축하기 위해 필요하다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
}
