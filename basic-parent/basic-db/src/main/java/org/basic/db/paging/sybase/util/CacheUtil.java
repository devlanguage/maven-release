package org.basic.db.paging.sybase.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.basic.db.paging.sybase.model.SQLCacheInfo;

	/**
	 * 给数据库表记录数和Id增加缓存
	 * @author wangmeng 
	 *
	 */
	public class CacheUtil {
		private static LinkedHashMap<String,Object> lmap = new LinkedHashMap<String,Object>(){
			private static final long serialVersionUID = -3432076593791024110L;
			//创建一个LinkedHashMap匿名内部类最大size是30超过30自动删除第一个
			private final static int MAX_SIZE = 30;
			protected boolean removeEldestEntry(java.util.Map.Entry<String,Object> eldest) {
				return size()>MAX_SIZE;
			};
		};
		private CacheUtil(){};
		/**
		 * 缓存总数
		 * @param key
		 * @param value
		 */
		public static void  setTotal(String key,int value){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				info = new SQLCacheInfo();
			}
			info.setTotalSize(value);
			lmap.put(key, info);
		}
		/**
		 * 根据表名把缓存改表的数据移除
		 * add by wangmeng 2013-4-27
		 * @param tableNameKey 大小写无所谓
		 */
		public static void removeCache(String tableName){
			if(tableName == null){
				return;
			}
			Iterator i = lmap.keySet().iterator();
			List<String> keys = new ArrayList<String>();
			while(i.hasNext()){
				String key = (String)i.next();
				if(key.toLowerCase().contains(tableName.toLowerCase())){
					keys.add(key);
				}
			}
			for (String key : keys) {
				System.out.println("移除缓存："+key);
				lmap.remove(key);
			}
		}
		public static int getTotalSize(String key){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				return -1;
			}
			return info.getTotalSize();
		}
		/**
		 * 缓存id位置
		 * @param key
		 * @param map
		 */
		public static void initPageIndex(String key, Map<Integer,Object> map){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				info = new SQLCacheInfo();
			}
			info.put(map);
		}
		/**
		 * 返回id位置信息
		 * 
		 * @param key 
		 * @param index
		 * @return
		 */
		public static Entry<Integer, Object> getFloorEntry(String key,Integer index){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				return null;
			}
			return info.getEntry(index);
		}
		public static Entry<Integer, Object> getCeilEntry(String key,Integer index){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				return null;
			}
			return info.getCeilEntry(index);
		}
		/**
		 * 返回缓存id位置的数量
		 * @param key
		 * @return
		 */
		public static int getIndexSize(String key){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				return -1;
			}
			return info.getIndexSize();
		}
		public static boolean isInitIndex(String key){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				throw new RuntimeException(key+"没有找到");
			}
			return info.isInitIndex();
		}
		public static boolean isPlaceholder(String key){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				return false;
			}
			return info.isPlaceholder();
		}
		public static void setPlaceholder(String key){
			SQLCacheInfo info = (SQLCacheInfo)lmap.get(key);
			if(info == null){
				info = new SQLCacheInfo();
			}
			info.setPlaceholder(true);
		}
		public static void main(String[] args) {
			setTotal("a",1);
			setTotal("b",1);
			removeCache("a");
		}
	}
