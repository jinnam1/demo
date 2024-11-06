package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.form.product.AllForm;
import com.kh.demo.web.form.product.DetailForm;
import com.kh.demo.web.form.product.SaveForm;
import com.kh.demo.web.form.product.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor

public class ProductController {

  private final ProductSVC productSVC;


//  public ProductController(ProductSVC productSVC) {
//    this.productSVC = productSVC;
//  }


  //등록양식
  @GetMapping("/add")
  public String addForm(Model model){

    // 오류가 발생 했을때 새로운 페이지를 로딩하는데 아무런 값이 없으면 서버 오류가 뜨기 때문에
    // 처음에 빈 값을 가진 saveForm 객체를 생성해서 페이지에 전달
    model.addAttribute("saveForm",new SaveForm());
    return "/product/add";//상품등록화면
  }
  //등록처리
  @PostMapping("/add")  // "post / product/add"
  
  public String add(
          @Valid  // form 객체에 필드별 유효성 체크
          @ModelAttribute SaveForm saveForm, // modelAttribute 는 뒤에 나오는 객체를 모델 객체에 추가해서 view에서 참조
                                                      // 지금은 saveForm이라는 객체를 saveForm이라는 이름으로 참조
          BindingResult bindingResult,  //BindingResult : 검증 결과를 담는 객체
          RedirectAttributes redirectAttributes){



    //사용자가 입력한정보
    log.info("pname={}, price={}, quantity={}",saveForm.getPname() , saveForm.getPrice() ,saveForm.getQuantity());
    
    // 요청 데이터 유효성 체크
    // 1. 어노테이션 기반의 필드 검증
    // 이 과정을 먼저 수행한 뒤에 비즈니스 로직 즉 글로벌에러나 내가 로직상 검증을 해야할 코딩을 만든다. 그게 맞다.
    if(bindingResult.hasErrors()) {
      log.info("bindingResult = {}",bindingResult);
      return "/product/add";
    }

    // 2. 코드 기반 필드 및 글로벌 오류(필드 2개 이상) 검증
    // 2 -1. 필드 오류 상품 수량 100 초과 불가

    if(saveForm.getQuantity() > 100) {
      // 필드에 뒤에 나오는 메세지를 bindingResult 객체에 담아둔다.
//      bindingResult.rejectValue("quantity",null, "상품수량 100 초과 불가");
      bindingResult.rejectValue("quantity","product",new Object[]{100}, null);  // product.saveForm.quantity, product.quantity , product
    }

    // 2-2. 글로벌 오류(필드가 2개이상) : 총액(가격*수량) 1000만원 초과 불가
    if (saveForm.getPrice()* saveForm.getQuantity() > 10_000_000L){
//      bindingResult.reject("totalPrice","총액(상품수량*단가) 1000 만원 초과 불가");
      bindingResult.reject("totalPrice",new Object[]{1000},null);  //totalPrice.saveForm , totalPrice
    }


    // 여기서 오류가 발생할 시에 BindingResult가 오류 정보와 함께 오류가 발생한 saveForm의 데이터를 가지고 add페이지로 이동

    if(bindingResult.hasErrors()) {
      log.info("bindingResult = {}",bindingResult);
      return "/product/add";
    }


    //상품테이블에 저장
    Product product = new Product();

    product.setPname(saveForm.getPname());
    product.setPrice(saveForm.getPrice());
    product.setQuantity(saveForm.getQuantity());


    Long pid = productSVC.save(product);
    
    // 리다이렉트 url 경로 변수에 값을 동적으로 할당하는 용도
    redirectAttributes.addAttribute("id",pid);

    return "redirect:/products/{id}"; //상품상세화면 302 get http://localhost:9080/products/2/detail
  }

  //목록양식
  @GetMapping  // get / products
  public String findAll(Model model){
    List<Product> list = productSVC.findAll();
    ArrayList<AllForm> all = new ArrayList<>();

    for (Product product : list) {
      AllForm allForm = new AllForm();
      allForm.setProductId(product.getProductId());
      allForm.setPname(product.getPname());
      all.add(allForm);
    }

    model.addAttribute("all",all);

    return "/product/all"; // view이름
  }

  // 상품 단건 조회
  @GetMapping("/{id}")    // get /product/2/detail
  public String findById(
          @PathVariable("id") Long productId
          ,Model model){
    log.info("productId = {}" , productId);

    Optional<Product> findedProduct = productSVC.findByID(productId);
    Product product = findedProduct.orElseThrow();

    DetailForm detailForm = new DetailForm();

    detailForm.setProductId(product.getProductId());
    detailForm.setPname(product.getPname());
    detailForm.setPrice(product.getPrice());
    detailForm.setQuantity(product.getQuantity());



    model.addAttribute("detailForm",detailForm);

    return "/product/detailform";
  }

  // 상품 단건 삭제
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long productId){
    log.info("productId={}", productId);

    int rows = productSVC.deleteById(productId);

    return "redirect:/products";    // 302 get redirectUrl : http://localhost:9080/products

  }


  // 상세 화면에서 수정화면을 요첳했을때
  @GetMapping("/{id}/edit")
  public String updateForm(@PathVariable("id") Long productId , Model model){

    Optional<Product> optionalProduct = productSVC.findByID(productId);
    Product findedProduct = optionalProduct.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setProductId(findedProduct.getProductId());
    updateForm.setPname(findedProduct.getPname());
    updateForm.setPrice(findedProduct.getPrice());
    updateForm.setQuantity(findedProduct.getQuantity());

    model.addAttribute("updateForm",updateForm);

    return "/product/updateForm";
  }




  // 상품 단건 수정 처리
  @PostMapping("/{id}/edit")
  public String updateById(
          @PathVariable("id") Long productId,
          UpdateForm updateForm,
          RedirectAttributes redirectAttributes){

    log.info("productId={}", productId);
    log.info("UpdateForm={}", updateForm);

    Product product = new Product();
    product.setPname(updateForm.getPname());
    product.setPrice(updateForm.getPrice());
    product.setQuantity(updateForm.getQuantity());

    int rows = productSVC.updateById(productId,product);

    redirectAttributes.addAttribute("id", productId);
    return "redirect:/products/{id}";  // 302 get redirectUrl http://localhost:9080/products/2/detail
  }


  // 여러 건 삭제
  @PostMapping("/del")
  public String deleteByIds(@RequestParam("productIds") List<Long> productIds){

    log.info("productIds={}",productIds);

    int rows = productSVC.deleteByIds(productIds);


    return "redirect:/products";    // 302 get redirectUrl : http://localhost:9080/products
  }
}
