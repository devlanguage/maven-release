package org.basic.jdk.core.ucf.barrier;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.List;  
import java.util.concurrent.BrokenBarrierException;  
import java.util.concurrent.CyclicBarrier;  
  
//通关任务  
public class GameBarrierTask implements Runnable {  
  
  //游戏关集合  
  private List<GameBarrier> gameBarriers;  
   
  //玩家名称  
  private String gamePlayer;  
   
  //通关控制器  
  private CyclicBarrier  cyclicBarrier;  
   
  private GameBarrierTask() {}  
  
  public GameBarrierTask(List<GameBarrier> gameBarriers, String gamePlayer, CyclicBarrier  cyclicBarrier) {  
    this.cyclicBarrier = cyclicBarrier;  
    this.gameBarriers = gameBarriers;  
    this.gamePlayer = gamePlayer;  
  }  
   
  //获得当前时间  
  private String currenttime() {  
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
    return sdf.format(new Date()) + ": ";  
  }  
  
  public void run() {  
    try {  
      for (GameBarrier gameBarrier : gameBarriers) {  
        Thread.sleep(gameBarrier.getPassTime() * 1000);  
        System.out.println(currenttime() + gamePlayer + " passed game barrier " + gameBarrier.getBarrierName());  
        cyclicBarrier.await();  
      }  
    } catch (InterruptedException e) {  
    } catch (BrokenBarrierException e) {  
    }  
  }  
}  