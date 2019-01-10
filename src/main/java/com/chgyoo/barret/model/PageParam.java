package com.chgyoo.barret.model;

import lombok.Data;

@Data
public class PageParam {
  private int pageIndex;
  private int pageSize;
  private String sortKey;
  private String sortType;
  // 如果分页查询的结果是多表关联的，则可以指定排序字段来源于查询结果表别名
  private String sortTableAlias = "";
  private int pageStart;
  private int pageEnd;
}
