package com.suty.currency.ch3

import java.util.concurrent.{Executors, Semaphore}

/**
  * Created by piguanghua on 3/26/18.
  */
class SemapDemo extends Runnable{
  override def run(): Unit = {
    try{
      SemapDemo.semp.acquire()
      Thread.sleep(5000)
      println(Thread.currentThread().getName + "   "  +  "done")
      SemapDemo.semp.release()
    }catch{
      case e:InterruptedException =>{
        e.printStackTrace()
      }
    }
  }
}

object SemapDemo{
  val semp:Semaphore = new Semaphore(5)

  def main(args: Array[String]): Unit = {
    val exec = Executors.newFixedThreadPool(20)
    val semapDemo:SemapDemo = new SemapDemo()

    for(i <- 1 to 20){
      exec.submit(semapDemo)
    }
  }
}
