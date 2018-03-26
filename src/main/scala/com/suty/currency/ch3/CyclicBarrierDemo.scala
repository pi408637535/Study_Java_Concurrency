package com.suty.currency.ch3

import java.util.concurrent.CyclicBarrier

/**
  * Created by piguanghua on 3/26/18.
  */

class Soldier(cylic:CyclicBarrier, soldierName: String) extends Runnable {

  override def run(): Unit = {
    cylic.await() // 等待所有士兵到齐
    Thread.sleep(1000)
    cylic.await() // 等待所有士兵完成工作
  }
}

class BarriderRun(var flag:Boolean, n:Int) extends Runnable{
  override def run(): Unit = {
    if(flag){
      println(s"司令:[士兵 $n 个] 任务完成")
    }else{
      println(s"司令:[士兵 $n 个] 集合完毕")
      flag = true
    }
  }
}

object CyclicBarrierDemo {
  def main(args: Array[String]): Unit = {
    val N:Int = 10
    val allSoldier =  new Array[Thread](20)
    val flag = false

    val cyclic = new CyclicBarrier(N, new BarriderRun(flag, N))

    println("集合队伍")

    for(i <-  1 to N){
      println("士兵 " + i + " 报道")
      allSoldier(i) = new Thread(new Soldier(cyclic, "士兵" + i ))
      allSoldier(i).start()
    }


  }
}
