package com.chgyoo.barret.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.chgyoo.barret.model.ValidDateObject;


public class Utils {
  public static String createUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static String[] convertIds(String ids) {
    return "".equals(ids) ? new String[0] : ids.split(",");
  }

  public static void checkValid (List<?> list) {
    for (int i = 0; i < list.size(); i++) {
      ValidDateObject validObject = (ValidDateObject) list.get(i);
      if (validObject.getInvalidTime() != null) {
        int gap = (int) ((validObject.getInvalidTime().getTime() - new Date().getTime())/(24*60*60*1000));
        validObject.setValidDays(gap);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        validObject.setInvalidTimeFmt(sd.format(validObject.getInvalidTime()));
        if (gap <= 0) {
          validObject.setStatus("失效");
        }
        // 因为前台获取时间相差8个小时，所以先加上8个小时
        validObject.setInvalidTime(new Date(validObject.getInvalidTime().getTime() + 8*60*60*1000));
      }
      if (validObject.getValidDays() == null || validObject.getValidDays() > 0) {
        validObject.setStatus("生效");
      }
    }
  }
}
