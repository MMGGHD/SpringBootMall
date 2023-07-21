package shop.mtcoding.mall.model;

import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;

// @AllArgsConstructor << lombok이 생성자 순서를 보장하지 않기 때문에 직접 만드는게 안전하다.
@Setter
@Getter
@NoArgsConstructor

public class ProductDTO {
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    private String des; // 상품 설명

    @Builder
    public ProductDTO(Integer id, String name, Integer price, Integer qty, String des) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.des = des;
    }
}
