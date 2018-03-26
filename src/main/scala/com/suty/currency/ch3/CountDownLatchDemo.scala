package com.suty.currency.ch3

import java.util.concurrent.{CountDownLatch, Executors}

/**
  * Created by piguanghua on 3/26/18.
  */
class CountDownLatchDemo extends Runnable{
  override def run(): Unit = {
    Thread.sleep(1000)
    println("check complete")
    CountDownLatchDemo.end.countDown()
  }
}

object CountDownLatchDemo{
  val  end:CountDownLatch = new CountDownLatch(4)

  def main(args: Array[String]): Unit = {
    val exec = Executors.newFixedThreadPool(10)
    val demo = new CountDownLatchDemo()
    for(i <- 1 to 10){
      exec.submit(demo )
    }
    end.await()
    println("Fire!....")
    exec.shutdown()
  }
}
