package com.kh.demo.domain.product.svc;

import com.kh.demo.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest

public class ProductSVCImplTest {

  @Autowired
  ProductSVC productSVC;


  @Test
  void findById(){
    Long productId =  2L;
    Optional<Product> product =  productSVC.findByID(productId);
    // 조회된 상품이 없으면 예외 발생 있으면 변수에 저장

    Product findedProduct = product.orElseThrow();

    // log.info("findedProduct : {}" , findedProduct);

    Assertions.assertThat(findedProduct.getPname()).isEqualTo("모니터");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(5L);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(500000L);

  }


}
