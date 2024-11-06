package com.kh.demo.web.form.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class SaveFormOld {
  @NotBlank(message = "상품명은 필수입니다.")
  @Size(min = 1, max = 10,message = "상품명은 10자를 초과 할 수 없습니다.")
  private String pname;

  @NotNull(message = "상품 수량은 필수입니다.")
  @Positive(message = "수량은 양수여야 합니다.")
  @Max(value = 9_999_999_999L, message = "수량은 9,999,999,999개 이하여야 합니다.")
  private Long quantity;

  @NotNull(message = "상품 가격은 필수입니다.")
  @Min(value = 1000, message = "가격은 1000미만 불가입니다.")
  @Max(value = 9_999_999_999L, message = "가격은 9,999,999,999원 이하여야 합니다.")
  private Long price;

}
