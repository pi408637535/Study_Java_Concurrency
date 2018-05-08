package com.suty.currency.ch3

import java.util.concurrent.{CountDownLatch, Executors}

/**
  * Created by piguanghua on 3/26/18.
  */
class CountDownLatchDemo extends Runnable{
  override def run(): Unit = {

    println("check complete")
    println(Thread.currentThread().getName)
    Thread.sleep(10000)
    CountDownLatchDemo.end.countDown()
  }
}

object CountDownLatchDemo{
  val  end:CountDownLatch = new CountDownLatch(2)

  def main(args: Array[String]): Unit = {

    val starttime=System.nanoTime


    val exec = Executors.newFixedThreadPool(2)
    val demo = new CountDownLatchDemo()
    for(i <- 1 to 2){
      exec.submit(demo )
    }
    end.await()
    println("Fire!....")
    exec.shutdown()

    val endtime=System.nanoTime
    val delta=(endtime-starttime)/1000000d
    println(s"time = $delta")
  }
}
