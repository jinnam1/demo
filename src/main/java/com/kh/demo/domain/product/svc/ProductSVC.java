package com.kh.demo.domain.product.svc;

import com.kh.demo.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {
  // 등록
  Long save(Product product);

  // 목록
  List<Product> findAll();

  // 조회
  Optional<Product> findByID(Long productId);

  // 삭제
  int deleteById(Long productId);

  // 수정
  int updateById(Long productId, Product product);

  // 여러 건 삭제
  int deleteByIds(List<Long> productIds);
}
