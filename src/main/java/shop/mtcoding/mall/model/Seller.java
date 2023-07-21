package shop.mtcoding.mall.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// 화면 따로 만들지 않는다 (join해서 띄우는 용도)

@Getter
@Setter
@Table(name = "seller_tb")
@Entity
public class Seller {

    // 1명의 Seller는 여러개의 Product를 팔수있다.
    // 1개의 Product는 1명의 Seller에게만 팔린다. (N : 1의 관계)
    // N인 상품이 FK를 가지고있어야 한다.
    // Driving 테이블은 N쪽 << Product이 되어야 한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
}
