package org.basic.grammar.ucf.barrier;
//游戏关卡  
public class GameBarrier {  
  //关名称  
  private String barrierName;  
   
  //玩家通关时间  
  private int passTime;  
   
  private GameBarrier(){}  
   
  public GameBarrier(String barrierName, int passTime){  
    if(barrierName == null || barrierName.trim().length() == 0)  
      this.barrierName = "默认关";  
    else  
      this.barrierName = barrierName;  
     
    if(passTime < 1)  
      this.passTime = Integer.MAX_VALUE;//无限大,表示不通关  
    else  
      this.passTime = passTime;  
  } 
  
  public String getBarrierName() {  
    return barrierName;  
  }  
  
  public int getPassTime() {  
    return passTime;  
  }  
}  
  