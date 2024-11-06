package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;

@Slf4j
@SpringBootTest

public class DAOTest {

  @Autowired
  ProductDAO productDAO;

  @Autowired
  NamedParameterJdbcTemplate template;

  @Test
  @DisplayName("단일행 단일열 테스트")
  void sqlTest1(){

    String sql = "select count(*) from product" ;
    String sql2 = "select pname from product where pname = '모니터' " ;


    Integer count = template.queryForObject(sql.toString(), new HashMap<>(), Integer.class);
    log.info("count = {}", count);

    String pname = template.queryForObject(sql2.toString(), new HashMap<>(), String.class);
    log.info("pname = {}", pname);
  }
}
