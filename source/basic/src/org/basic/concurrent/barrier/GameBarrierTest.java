package org.basic.concurrent.barrier;

import java.util.ArrayList;  
import java.util.List;  
import java.util.concurrent.CyclicBarrier;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
  
//对CyclicBarrier的测试(线程相互等待，直到都达到某一个点，然后开始继续执行)  
//这个测试类进行的是：3个玩家同时进行通关游戏,当他们都到达或通过某一关后，才可以  
//继续往下过关  
public class GameBarrierTest {  
  
  public static void main(String[] args) {  
    // 建立一个带有三个玩家的通关控制器  
    CyclicBarrier barrier = new CyclicBarrier(4);  
     
    // 为玩家1建立通关任务  
    List<GameBarrier> gameBarrier1 = new ArrayList<GameBarrier>(4);  
    gameBarrier1.add(new GameBarrier("第一关",5));  
    gameBarrier1.add(new GameBarrier("第二关",2));  
    gameBarrier1.add(new GameBarrier("第三关",8));  
    gameBarrier1.add(new GameBarrier("第四关",6));  
    GameBarrierTask task1 = new GameBarrierTask(gameBarrier1, "张三", barrier);  
     
    // 为玩家2建立通关任务  
    List<GameBarrier> gameBarrier2 = new ArrayList<GameBarrier>(4);  
    gameBarrier2.add(new GameBarrier("第一关",1));  
    gameBarrier2.add(new GameBarrier("第二关",6));  
    gameBarrier2.add(new GameBarrier("第三关",3));  
    gameBarrier2.add(new GameBarrier("第四关",8));  
    GameBarrierTask task2 = new GameBarrierTask(gameBarrier2, "李四", barrier);  
  
   
  
    // 为玩家3建立通关任务  
    List<GameBarrier> gameBarrier3 = new ArrayList<GameBarrier>(4);  
    gameBarrier3.add(new GameBarrier("第一关",7));  
    gameBarrier3.add(new GameBarrier("第二关",6));  
    gameBarrier3.add(new GameBarrier("第三关",4));  
    gameBarrier3.add(new GameBarrier("第四关",3));  
    GameBarrierTask task3 = new GameBarrierTask(gameBarrier3, "王五", barrier);  
  
     
  
   //建立线程池，执行游戏通关任务  
    ExecutorService executorService = Executors.newFixedThreadPool(3);  
    executorService.submit(task1);  
    executorService.submit(task2);  
    executorService.submit(task3);  
     
    //关闭线程池  
    executorService.shutdown();  
  
  }  
}  
  