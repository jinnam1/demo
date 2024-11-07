package com.kh.demo.web.req.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ReqUpdate {
  private String pname;
  private Long price;
  private Long quantity;
}
