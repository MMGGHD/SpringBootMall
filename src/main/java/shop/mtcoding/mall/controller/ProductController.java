package shop.mtcoding.mall.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.mall.model.Product;
import shop.mtcoding.mall.model.ProductRepository;
import shop.mtcoding.mall.model.Seller;
import shop.mtcoding.mall.model.SellerRepository;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @PostMapping("/product/delete")
    public void delete(int id, HttpServletResponse response) throws IOException {
        productRepository.deleteById(id);
        response.sendRedirect("/");
    }

    // Integer price처럼 wrapper class를 사용하면 null값으로 지정할수 있어 int price보다 예외처리를 하기 수월하다.
    // 에러 페이지는 index.jsp 처럼 error.jsp가 기본값으로 호출된다.
    @PostMapping("/product/update")
    public String update(Integer id, String name, Integer price, Integer qty, String sellerName) {
        System.out.println("id : " + id);
        System.out.println("name : " + name);
        System.out.println("price : " + price);
        System.out.println("qty : " + qty);
        System.out.println("sellerName : " + sellerName);
        Integer sellerId = null;
        if (price == null || name.equals("") || qty == null) {
            return "error";
        }
        try {
            sellerId = sellerRepository.FindByName(sellerName).getId();
        } catch (NoResultException e) {
            return "error2";
        }
        productRepository.updateById(id, name, price, qty, sellerId);
        return "redirect:/";
    }

    // {id} << pathValuable : 안에 변수를 받을수 있음
    @GetMapping("/product/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request) {
        Product product = productRepository.findById(id);
        request.setAttribute("p", product);
        return "detail";
    }

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        List<Product> productList = productRepository.findAll();
        //setAttribute() << HttpServletRequest 객체에 데이터 값을 담는 메서드
        request.setAttribute("productList", productList);
        return "home";
    }

    @GetMapping("/write")
    public String writePage() {
        return "write";
    }

    // '상품(product)'을 '줄게'
    @PostMapping("/product")
    public void write(String name, int price, int qty, String sellerName, HttpServletResponse response) throws IOException {

        // 또는 public String write(String name, int price, int qty, String sellerName) throws IOException {
        System.out.println("name : " + name);
        System.out.println("price : " + price);
        System.out.println("qty : " + qty);
//        return "write";
        Integer sellerId = sellerRepository.FindByName(sellerName).getId();

        // HttpServletResponse << 응답의 소켓객체, 응답데이터를 클라이언트에게 write 한다.
        // sendRedirect("aa") << 데이터 header에 "aa" location을 달아 준다.
        // 클라이언트는 데이터 도달시 자동으로 location에 Redirect요청을 하게 된다.
        productRepository.saveWithFK(name, price, qty, sellerId);
        response.sendRedirect("/");
//      return "redirect:/";
    }
}
