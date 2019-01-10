package com.chgyoo.barret.model;

import lombok.Data;

@Data
public class ResultJson {
  private static int SUCCESS = 200;
  private static int FAILD = 500;
  private int code;
  private String msg;
  private Object data;

  public ResultJson(String message) {
    this.msg = message;
    this.code = SUCCESS;
  }

  public ResultJson(String message, Object obj) {
    this.msg = message;
    this.code = SUCCESS;
    this.data = obj;
  }

  public ResultJson(Boolean faild, String message) {
    this.code = FAILD;
    this.msg = message;
  }
}
