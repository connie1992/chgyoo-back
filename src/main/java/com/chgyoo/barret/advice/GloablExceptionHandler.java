package com.chgyoo.barret.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chgyoo.barret.model.ResultJson;


@RestControllerAdvice
public class GloablExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResultJson handleException(Exception e) {
    // 记录错误信息
    String msg = e.getMessage();
    if (msg == null || msg.equals("")) {
      msg = "服务器出错";
      e.printStackTrace();
    }
    System.err.println(msg);
    return new ResultJson(true, msg);
  }
}
