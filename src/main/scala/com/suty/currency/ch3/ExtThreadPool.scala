package com.suty.currency.ch3

import java.util.concurrent.{LinkedBlockingQueue, ThreadPoolExecutor, TimeUnit}

/**
  * Created by piguanghua on 3/29/18.
  * 监控线程池的线程的声明周期
  */
object ExtThreadPool {

  def main(args: Array[String]): Unit = {
    val es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MICROSECONDS
    ,new LinkedBlockingQueue[Runnable]()){
      override def beforeExecute(t: Thread, r: Runnable): Unit = {
        println("准备执行：" + Thread.currentThread().getName)
      }

      override def afterExecute(r: Runnable, t: Throwable): Unit = {
        println("准备完毕：" + Thread.currentThread().getName)
      }

      override def terminated(): Unit = {
        println("线程退出")
      }
    }

    for(i <- 1 to Int.MaxValue){
      es.submit(new MyTask)
      Thread.sleep(10)
    }

  }

}
