package com.kh.demo.web.form.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class UpdateForm {

  private Long productId;
  private String pname;
  private Long price;
  private Long quantity;
}
