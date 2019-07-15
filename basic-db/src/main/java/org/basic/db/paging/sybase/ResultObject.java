package org.basic.db.paging.sybase;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created on Jan 30, 2014, 12:03:38 PM
 */
public class ResultObject {
  Map<String, Object> data = new LinkedHashMap<String, Object>();

  public void addData(String name, Object data) {
    this.data.put(name, data);
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

}
