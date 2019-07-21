package org.basic.db.paging.sybase;

import java.util.List;
import java.util.Map.Entry;

import org.basic.db.paging.sybase.model.Page;
import org.basic.db.paging.sybase.model.ProvinceSpareInfoAll;
import org.basic.db.paging.sybase.service.PagingService;

public class Test {

  /**
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    // StringBuilder sql = new StringBuilder("select t.keyId,t.pvcode" + ",t.spType,t.factory,t.spareType,t.spareNum,"
    // + "t.spareFuncNum,t.spareState,t.spareSerialNum,t.spareHardVersion,t.spareSoftVersion,"
    // + "t.spareEnName,t.spareZhName,t.spareAddress " + " from province_Spare_Info_ALL t where 1=1 ");
    StringBuilder sql = new StringBuilder("select * from fault where 1=1 ");
    PagingService service = new PagingService();
    int pageSize = 15;
    int startNum = pageSize * 0;// 第一页
    // 首次加载效率
    long b = System.currentTimeMillis();
    getPageByResultObject(service, sql.toString(), startNum, pageSize);
    long e = System.currentTimeMillis();
    System.out.println("首次加载时间" + (e - b));
    // getPage(service ,sql.toString(),startNum,pageSize);
    Thread.currentThread().sleep(8000);// 缓存5.5秒后可查询 我得电脑是4.7秒
    // 缓存之后效率
    startNum = pageSize * 8000;// 第8000页
    b = System.currentTimeMillis();
    getPageByResultObject(service, sql.toString(), startNum, pageSize);
    e = System.currentTimeMillis();
    System.out.println("翻到第8000时间" + (e - b));

    startNum = pageSize * 40000;// 第40000页
    b = System.currentTimeMillis();
    getPageByResultObject(service, sql.toString(), startNum, pageSize);
    e = System.currentTimeMillis();
    System.out.println("翻到第40000时间" + (e - b));
  }

  public static void getPage(PagingService service, String sql, int startNum, int pageSize) {

    Page page = service.findPageBySql(sql, ProvinceSpareInfoAll.class, "fltid", startNum, pageSize);
    List list = page.getData();
    System.out.println(page.getTotalCount());
    for (Object object : list) {
      ProvinceSpareInfoAll p = (ProvinceSpareInfoAll) object;
      System.out.print(p.getKeyId() + " ");
    }
    System.out.println();
  }

  public static void getPageByResultObject(PagingService service, String sql, int startNum, int pageSize) {

    Page page = service.findPageBySql(sql, ResultObject.class, "fltid", startNum, pageSize);
    List list = page.getData();
    System.out.println(page.getTotalCount());
    for (Object object : list) {
      ResultObject p = (ResultObject) object;
      for (Entry<String, Object> entry : p.getData().entrySet()) {
        System.out.print(entry.getKey() + "=" + entry.getValue() + ",");
      }
    }
    System.out.println();
  }

}
