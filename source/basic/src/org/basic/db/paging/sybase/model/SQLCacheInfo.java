package org.basic.db.paging.sybase.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class SQLCacheInfo {
	/**
	 * 第多少条，id值多少
	 */
	private  TreeMap<Integer,Object> IndexId = new TreeMap<Integer,Object>();
	public static final int MOD = 50;
	public static final int MINMOD = 5;
	public static final int CAPTION = 10000;
	public static final int MINCAPTION = 50;
	private int totalSize;
	private boolean isPlaceholder;
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	/**
	 * 根据条数返回id值
	 * @param index
	 * @return
	 */
	public Entry<Integer,Object> getEntry(int index){
		return IndexId.floorEntry(index);
	}
	public Entry<Integer,Object> getCeilEntry(int index){
		return IndexId.ceilingEntry(index);
	}
	public void put(Map<Integer,Object> map){
		IndexId.putAll(map);
	}
	public int getIndexSize(){
		if(totalSize < 10000)
		{
			return Math.max(MINCAPTION, totalSize/MINMOD);
		}
		return Math.max(CAPTION,(totalSize / 50));
	}
	public static void main(String[] args) {
		SQLCacheInfo info = new SQLCacheInfo();
		HashMap map = new HashMap();
		map.put(10, 2);
		map.put(40, 2);
		map.put(60, 2);
		map.put(80, 2);
		info.put(map);
		System.out.println(info.getEntry(100).getKey());
	}
	public boolean isInitIndex(){
		return this.IndexId.size()>0;
	}
	public boolean isPlaceholder() {
		return isPlaceholder;
	}
	public void setPlaceholder(boolean isPlaceholder) {
		this.isPlaceholder = isPlaceholder;
	}
	
}
