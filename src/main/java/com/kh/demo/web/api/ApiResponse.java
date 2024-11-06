package com.kh.demo.web.api;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Getter
@ToString
// 클래스를 정의 할때 <>가 들어가는 경우
// 클래스를 사용할때 정의된 타입으로 생성, 현재 T 가 들어간 자리
// 관례적으로 대문자 사용
// 타입이 유동적인 특징, 제네릭 타입이라고 불림
// 타입의 변동될수 있는 상황에서 클래스에 고정적으로 받기엔 너무 많은 상황이 생기기에 타입을 미정인 상태로 놓는다.
public class ApiResponse<T> {
  private Header header;    //응답헤더
  private T body;           //응답바디
  private int totalCnt;     //총건수

  private ApiResponse(Header header, T body, int totalCnt) {
    this.header = header;
    this.body = body;
    this.totalCnt = totalCnt;
  }

  @Getter
  @ToString
  private static class Header{
    private String rtcd;      //응답코드 return code
    private String rtmsg;     //응답메시지 return message

    Header(String rtcd, String rtmsg) {
      this.rtcd = rtcd;
      this.rtmsg = rtmsg;
    }
  }

  public static <T> ApiResponse<T> createApiResponse(String rtcd, String rtmsg, T body){
    int totalCnt = 0;

    if(body != null) {
      // 바디가 collection계열인지 요소갯수 가져오기
      if (ClassUtils.isAssignable(Collection.class, body.getClass())) {
        totalCnt = ((Collection<?>) body).size();
        // 바디가 Map계열인지 체크하여 요소갯수 가져오기
      } else if (ClassUtils.isAssignable(Map.class, body.getClass())) {
        totalCnt = ((Map<?, ?>) body).size();
      } else {
        totalCnt = 1;
      }
    }
    return new ApiResponse<>(new Header(rtcd,rtmsg), body, totalCnt);
  }
}