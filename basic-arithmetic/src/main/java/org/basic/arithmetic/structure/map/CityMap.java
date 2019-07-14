package org.basic.arithmetic.structure.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * <pre>
 *   Simulated City Map
 *   
 *       (LanZhou)  -------18---------(Chongqing)
 *           |                              |
 *          36                              7
 *           |                              |
 *       (BeiJing)   -------10----------(HanKou)
 *          |  \                          /  |           
 *          |    \                      /    |
 *         18     20                  9      3
 *          |       \                /       |
 *          |        \             /         |
 *          |        (Shanghai)--/           |
 *          |            |                   |
 *          |            6                   |       
 *          |            |                   | 
 *          |----------(ShengZhen)-----------|
 *       
 *      
 *             map.addRoute("BeiJing", "LanZhou", 36);         
 *             map.addRoute("BeiJing", "HanKou", 10);
 *             map.addRoute("BeiJing", "ShangHai", 20);
 *             map.addRoute("BeiJing", "ShenZhen", 18);
 *             
 *             map.addRoute("HanKou", "ChongQing", 7);
 *             map.addRoute("HanKou", "ShangHai", 9);       
 *             map.addRoute("HanKou",  "ShenZhen",  3);
 *             
 *             map.addRoute("Shanghai",  "ShenZhen",  6);
 *             map.addRoute("LanZhou", "ChongQing", 18);
 * </pre>
 * 
 * @author ygong
 *
 */
public class CityMap {
    Map<String, List<Way>> cityMap = new HashMap<String, List<Way>>();// 储存所有城市路线

    // int shortestTime=0; //储存最短时间，用于只输出最短路径的情况
    /* ---添加路线，双向添加--- */
    public void addRoute(String from, String to, int cost) {
        List<Way> fromCityList = cityMap.get(from);// 城市1路线集合
        if (fromCityList == null) {
            fromCityList = new ArrayList<Way>();
            cityMap.put(from, fromCityList);
        }
        Way way1 = new Way(from, to, cost);
        /* ---不存在该路线，则添加--- */
        if (!fromCityList.contains(way1)) {
            fromCityList.add(way1);
        }

        List<Way> toCityList = cityMap.get(to);// 城市2路线集合
        if (toCityList == null) {
            toCityList = new ArrayList<Way>();
            cityMap.put(to, toCityList);
        }
        Way way2 = new Way(to, from, cost);
        /* ---不存在该路线，则添加--- */
        if (!toCityList.contains(way2)) {
            toCityList.add(way2);
        }
    }

    private void showShortestPath(String from, String to) {
        RouteWalker walker = new RouteWalker();
        walker.show(from, to);
    }

    /* ---城市路线对象--- */
    public class Way {
        public Way(String from_, String to_, int cost_) {
            this.from = from_;
            this.to = to_;
            this.cost = cost_;
        }

        String from;
        String to;
        int cost;
    }

    public class RouteWalker {
        List<String> reachedWay = new ArrayList<String>();// 储存到达目的地所经过的城市
        Map<Integer, String> routeMap = new HashMap<Integer, String>();// 储存到达目的地所经过的城市和所用的时间，key为时间，value为reachedWay

        /*---计算最短路径、最短时间---*/
        public void walkCity(String from, String to) {
            int tempTime = 0;// 储存所花时间的临时变量
            if (reachedWay.contains(from)) {// 走过的不走
                return;
            }
            reachedWay.add(from);// 把经过的城市加入到城市集合中
            if (reachedWay.size() > 1) {
                /* ---计算所花时间--- */
                List<Way> initList = cityMap.get(reachedWay.get(0));

                for (int j = 0; j < initList.size(); j++) {
                    Way w = (Way) initList.get(j);
                    if (w.to.equals(reachedWay.get(1))) {
                        tempTime += w.cost;

                        /* ---用于不需要循环输出所有路线，只输出最短路径，效率很高--- */
                        // if(shortestTime!=0&&tempTime>shortestTime){
                        // return;
                        // }
                    }
                }

                for (int i = 1; i < reachedWay.size(); i++) {
                    // 所经过的城市用时加起来
                    List<Way> toList = cityMap.get(reachedWay.get(i));
                    for (int j = 0; j < toList.size(); j++) {
                        Way w = (Way) toList.get(j);
                        if (i + 1 < reachedWay.size()) {
                            if (w.to.equals(reachedWay.get(i + 1))) {
                                tempTime += w.cost;

                                /* ---用于不需要循环输出所有路线，只输出最短路径，效率很高--- */
                                // if(shortestTime!=0&&tempTime>shortestTime){
                                // return;
                                // }
                            }
                        }
                    }
                }
            }

            /*---到达---*/
            if (from.equals(to)) {
                // shortestTime=tempTime;
                String route = reachedWay.get(0).toString();
                for (int i = 1; i < reachedWay.size(); i++) {
                    route += "->" + reachedWay.get(i).toString();
                }
                System.out.println("\t" + route + "\t用时：" + tempTime);
                routeMap.put(tempTime, route);
                tempTime = 0;
                reachedWay.remove(reachedWay.size() - 1);// 到达后退回去，走下一路线
                return;
            } else {
                /*---没到达---*/
            }

            // 获得from城市能够到达到城市列表
            List<Way> routeList = cityMap.get(from);
            for (Iterator<Way> iterator = routeList.iterator(); iterator.hasNext();) {
                Way way = (Way) iterator.next();
                walkCity(way.to, to);
            }
            reachedWay.remove(reachedWay.size() - 1);// 走完退回去，走下一路线
        }

        /*---输出用时最短的路线---*/
        public void show(String city1, String city2) {
            System.out.println("\n-------------------------\nAvailable Path List for " + city1 + "<-->" + city2);
            walkCity(city1, city2);
            Integer shortestTime = 0;
            Set<Integer> pathTimeSet = routeMap.keySet();
            
//            Object[] pathTimeArray = pathTimeSet.toArray();
//            shortestTime = (Integer) pathTimeArray[0];
//            for (int i = 0; i < pathTimeArray.length; i++) {
//                if ((Integer) pathTimeArray[i] < shortestTime) {
//                    shortestTime = (Integer) pathTimeArray[i];
//                }
//            }

            Optional<Integer> shortestTimeOptional = pathTimeSet.stream().min(new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            shortestTime = shortestTimeOptional.get();

            System.out.println("Shortest Path: \n\t" + routeMap.get(shortestTime) + "\t用时：" + shortestTime);
        }

    }

    /**
     * <pre>
     *   Simulated City Map
     *   
     *       (LanZhou)  -------18---------(Chongqing)
     *           |                              |
     *          36                              7
     *           |                              |
     *       (BeiJing)   -------10----------(HanKou)
     *          |  \                          /  |           
     *          |    \                      /    |
     *         18     20                  9      3
     *          |       \                /       |
     *          |        \             /         |
     *          |        (Shanghai)--/           |
     *          |            |                   |
     *          |            6                   |       
     *          |            |                   | 
     *          |----------(ShengZhen)-----------|
     *       
     *      
     *             map.addRoute("BeiJing", "LanZhou", 36);         
     *             map.addRoute("BeiJing", "HanKou", 10);
     *             map.addRoute("BeiJing", "ShangHai", 20);
     *             map.addRoute("BeiJing", "ShenZhen", 18);
     *             
     *             map.addRoute("HanKou", "ChongQing", 7);
     *             map.addRoute("HanKou", "ShangHai", 9);       
     *             map.addRoute("HanKou",  "ShenZhen",  3);
     *             
     *             map.addRoute("Shanghai",  "ShenZhen",  6);
     *             map.addRoute("LanZhou", "ChongQing", 18);
     * </pre>
     * 
     * @author ygong
     *
     */
    public static void main(String[] args) {
        CityMap map = new CityMap();
        map.addRoute("BeiJing", "LanZhou", 36);
        map.addRoute("BeiJing", "HanKou", 10);
        map.addRoute("BeiJing", "ShangHai", 20);
        map.addRoute("BeiJing", "ShenZhen", 18);

        map.addRoute("HanKou", "ChongQing", 7);
        map.addRoute("HanKou", "ShangHai", 9);
        map.addRoute("HanKou", "ShenZhen", 3);
        map.addRoute("LanZhou", "ChongQing", 18);

        map.showShortestPath("HanKou", "BeiJing");
        map.showShortestPath("ShangHai", "ShenZhen");
    }

}
