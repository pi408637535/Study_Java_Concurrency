package com.suty.currency.ch3

import java.util.concurrent.{SynchronousQueue, ThreadPoolExecutor, TimeUnit}

/**
  * Created by piguanghua on 3/29/18.
  */

class DivTask(val a: Int, val b: Int) extends Runnable {
  override def run(): Unit = {
    println(a / b)
  }
}

object SearchThreadDump {
  def main(args: Array[String]): Unit = {
    val pools = new ThreadPoolExecutor(0, Int.MaxValue, 0L, TimeUnit.SECONDS, new SynchronousQueue[Runnable]())

    for (i <- 0 until  5) {
    //  pools.submit(new DivTask(100, i))
      /**
        * 会报线程异常
        */
      pools.execute(new DivTask(100, i))

      val futuren = pools.submit(new DivTask(100, i))
      futuren.get()
    }
  }
}
