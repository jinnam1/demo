package com.kh.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

  @GetMapping("/1")
  public String test1(Model model){
    List<String> items = new ArrayList<>();
    items.add("홍길동1");
    items.add("홍길동2");
    items.add("홍길동3");
    model.addAttribute("members", items);
    return "/test/test1"; // thymeleaf 템플림 이름
  }
}
