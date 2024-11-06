package com.kh.demo.domain.entity;

import lombok.Data;

@Data

public class Product {

  private Long productId; // 상품아이디 PRODUCT_ID	NUMBER(10,0) camel case와 _사용하는건 서로 뭐 해준다고함
  private String pname;   // 상품명 PNAME	VARCHAR2(30 BYTE)
  private Long quantity;  // 삼품수량 QUANTITY	NUMBER(10,0)
  private Long price;     // 상품가격 PRICE	NUMBER(10,0)

//  public Product(Long product_id, String pname, Long quantity, Long price) {
//    this.product_id = product_id;
//    this.pname = pname;
//    this.quantity = quantity;
//    this.price = price;
//  }
//
//  public Long getProduct_id() {
//    return product_id;
//  }
//
//  public void setProduct_id(Long product_id) {
//    this.product_id = product_id;
//  }
//
//  public String getPname() {
//    return pname;
//  }
//
//  public void setPname(String pname) {
//    this.pname = pname;
//  }
//
//  public Long getQuantity() {
//    return quantity;
//  }
//
//  public void setQuantity(Long quantity) {
//    this.quantity = quantity;
//  }
//
//  public Long getPrice() {
//    return price;
//  }
//
//  public void setPrice(Long price) {
//    this.price = price;
//  }
}
