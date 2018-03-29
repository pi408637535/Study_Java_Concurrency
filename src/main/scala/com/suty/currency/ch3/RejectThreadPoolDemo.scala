package com.suty.currency.ch3

import java.util.concurrent._

/**
  * Created by piguanghua on 3/29/18.
  */

class MyTask1 extends Runnable {
  override def run(): Unit = {
    println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId)
    Thread.sleep(100)
  }
}

object RejectThreadPoolDemo {
  def main(args: Array[String]): Unit = {
    val task = new MyTask1
    val es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MICROSECONDS
      , new LinkedBlockingQueue[Runnable](10)
      , Executors.defaultThreadFactory()
      , new RejectedExecutionHandler {
        override def rejectedExecution(r: Runnable, executor: ThreadPoolExecutor) = {
          println(r.toString + "is discard")
        }
      })

    for(i <- 1 to Int.MaxValue){
      es.submit(task)
      Thread.sleep(10)
    }
  }
}
