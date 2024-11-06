package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest


public class ProductDAOImplTest {

  @Autowired
  ProductDAO productDAO;

  @Test
  @DisplayName("상품 등록")
  void save(){
    log.info("save() 호출됨");
    Product product = new Product();
    product.setPname("의자");
    product.setPrice(1000000L);
    product.setQuantity(10L);

    Long pid = productDAO.save(product);
    log.info("상품아이디 = {}", pid);

  }

  @Test
  @DisplayName("상품 목록")
  void findAll(){

    List<Product> list = productDAO.findAll();
    for (Product product : list) {
      log.info("product = {}" , product);
    }

  }

  @Test
  @DisplayName("상품 단건 조회")
  void findById(){
    long productId = 2L;
    Optional<Product> product = productDAO.findByID(productId);
    // 조회된 상품이 없으면 예외 발생 있으면 변수에 저장
    Product findedProduct = product.orElseThrow();

//    log.info("findedProduct : {}" , findedProduct);
    
    Assertions.assertThat(findedProduct.getPname()).isEqualTo("모니터");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(5L);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(500000L);

  }


  @Test
  @DisplayName("상품 단건 삭제")
  void deleteById(){
    Long productId = 58L;
    int rows = productDAO.deleteById(productId);

    Assertions.assertThat(rows).isEqualTo(1);
  }


  @Test
  @DisplayName("상품 수정")
  void updateById(){
     Long productId = 104L;
     Product product = new Product();
     product.setPname("만년필4");
     product.setQuantity(40000L);
     product.setPrice(20L);

    productDAO.updateById(productId,product);
    Optional<Product> optionalProduct = productDAO.findByID(productId);
    log.info("updatedProduct={}",optionalProduct);

    Product findedProduct = optionalProduct.orElseThrow();

    Assertions.assertThat(findedProduct.getPname()).isEqualTo("만년필4");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(40000L);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(20L);


  }

  @Test
  @DisplayName("여러 건 삭제")
  void deleteByIds(){
    List<Long> productIds = List.of(101L, 102L, 103L);

    int rows = productDAO.deleteByIds(productIds);

    log.info("rows = {}", rows);
    log.info("productIds = {}", productIds);

  }
}
