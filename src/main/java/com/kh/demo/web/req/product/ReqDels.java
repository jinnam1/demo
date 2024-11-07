package com.kh.demo.web.req.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class ReqDels {
  private List<Long> productIds;
}
