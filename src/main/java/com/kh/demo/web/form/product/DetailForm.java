package com.kh.demo.web.form.product;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailForm {
  private Long productId;
  private String pname;
  private Long price;
  private Long quantity;
}
