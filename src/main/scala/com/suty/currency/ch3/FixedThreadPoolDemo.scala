package com.suty.currency.ch3

import java.util.concurrent.Executors

/**
  * Created by piguanghua on 3/28/18.
  */

class MyTask extends Runnable{
  override def run(): Unit = {
    println(System.currentTimeMillis() + "Thread ID:" + Thread.currentThread().getId)
    Thread.sleep(1000)
    println("End")
  }
}

object FixedThreadPoolDemo {
  def main(args: Array[String]): Unit = {
    val task = new MyTask()
    val es = Executors.newFixedThreadPool(5)
    for(i <- 1 to 20){
      es.submit(task)
      es.execute(task)
      println(i)
    }
    es.shutdown()
  }

}
