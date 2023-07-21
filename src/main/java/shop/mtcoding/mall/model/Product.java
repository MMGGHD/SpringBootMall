package shop.mtcoding.mall.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Table(name = "product_tb")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    // private Integer sellerId; << 하면되지만 새로 모델을 만들지 않으면 매핑안됨, JPA에서는 쓰지않고 ORM을 쓴다.

    // (ORM)Object Relation Mapping 어노테이션 @ManyToOne등을 이용해 관계설정을 바로 할수있다.
    // 아래 객체를 담는 변수를 만들면 Product와 seller가 join된 Model을 따로 만들지 않아도 된다.
    // 자바는 DB와 다르게 객체를 다룰수 있기 때문에 가능
    @ManyToOne
    private Seller seller;
}
