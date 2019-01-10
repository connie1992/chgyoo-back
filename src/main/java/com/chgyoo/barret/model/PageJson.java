package com.chgyoo.barret.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data public class PageJson {
  private List<?> dataList;
  private int totalSize;

  public PageJson() {
    this.dataList = new ArrayList<>(0);
  }

  public PageJson(int size) {
    this.totalSize = size;
    this.dataList = new ArrayList<>();
  }
}
