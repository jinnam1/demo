package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.req.product.ReqDels;
import com.kh.demo.web.req.product.ReqSave;
import com.kh.demo.web.req.product.ReqUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/api/products")
@RequiredArgsConstructor
//@Controller
@RestController  // @Controller + @ResponseBody

public class ApiProductController {

  private final ProductSVC productSVC;

  @GetMapping("/{pid}")
//  @ResponseBody
  public ApiResponse<Product> findById(@PathVariable("pid") Long pid){
    ApiResponse<Product> res = null;
    Optional<Product> optionalProduct = productSVC.findByID(pid);

    if(optionalProduct.isPresent()){
      Product product = optionalProduct.get();
      res = ApiResponse.createApiResponse("00","success",product);
    }else{
      res = ApiResponse.createApiResponse("01","not found",null);
    }

    return res;
  }
  @GetMapping
//  @ResponseBody
  public ApiResponse<List<Product>> all(){
    ApiResponse<List<Product>> res = null;
    List<Product> products = productSVC.findAll();

    if(products.size() != 0) {
      res = ApiResponse.createApiResponse("00", "success", products);
    }else{
      res = ApiResponse.createApiResponse("01", "not found", null);
    }

    return res;
  }

  // 상품 등록
  @PostMapping
  // 요청 바디에서 속성명 같은 걸 읽어와서 매칭 시켜서 객체생성
  public ApiResponse<Product> add(@RequestBody ReqSave reqSave){
    log.info("reqSave={}",reqSave);
    ApiResponse<Product> res = null;

    Product product = new Product();
    BeanUtils.copyProperties(reqSave, product);

    Long pid = productSVC.save(product);

    Optional<Product> optionalProduct = productSVC.findByID(pid);

    if (optionalProduct.isPresent()) {
      Product savedpProduct = optionalProduct.get();
      res = ApiResponse.createApiResponse("00","success", savedpProduct);
    }else {
      res = ApiResponse.createApiResponse("99","fail", null);
    }


    return res;
  }

  // 단건 삭제
  @DeleteMapping("/{pid}")  // 요청 메소드 DELETE http://localhost:9080/api/products/301
  public ApiResponse<String> delete(@PathVariable("pid") Long pid){
    ApiResponse<String> res = null;

    int rows = productSVC.deleteById(pid);  //반환 삭제 건수
    if(rows == 1){
      res = ApiResponse.createApiResponse("00", "success",null);
    }else{
      res = ApiResponse.createApiResponse("01", "not found",null);
    }

    return res;
  }

  // 수정
  @PatchMapping("{pid}")
  public ApiResponse<Product> update(@PathVariable("pid") Long pid,
                            @RequestBody ReqUpdate reqUpdate){

    log.info("pid = {} ,reqUpdate = {}", pid,reqUpdate);
    ApiResponse<Product> res = null;

    Product product = new Product();
    BeanUtils.copyProperties(reqUpdate, product);


    int rows = productSVC.updateById(pid,product);
    if(rows == 1){
      Product updateProduct = productSVC.findByID(pid).get();
      res = ApiResponse.createApiResponse("00", "success",updateProduct);
    }else{
      res = ApiResponse.createApiResponse("01", "not found",null);
    }


   return res;
  }

  // 여러 건 삭제
  @DeleteMapping
  public ApiResponse<String> deleteByIds(@RequestBody ReqDels reqDels){
    log.info("reqDels = {}", reqDels);
    ApiResponse<String> res = null;

    int rows = productSVC.deleteByIds(reqDels.getProductIds());
    if(rows > 0){
      res = ApiResponse.createApiResponse("00", "success","삭제건수 : " + rows);
    }else{
      res = ApiResponse.createApiResponse("01", "not found",null);
    }

    return res;
  }

}
