package shop.mtcoding.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/write")
    public String write(){
        return "write";
    }
}
