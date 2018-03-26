package com.suty.currency.ch3

import java.util.concurrent.locks.ReentrantLock

/**
  * Created by piguanghua on 3/26/18.
  */
class FairLock extends Runnable{
  override def run(): Unit = {
    while (true){
      try{
        FairLock.reentrantLock.lock()
        println(Thread.currentThread().getName + "get Fair Lock")
      }finally {
        FairLock.reentrantLock.unlock()
      }
    }
  }
}

object FairLock {
  val reentrantLock:ReentrantLock = new ReentrantLock(true)

  def main(args: Array[String]): Unit = {
    val fairLock:FairLock = new FairLock();
    val thread1 = new Thread(fairLock, "Thread_t1")
    val thread2 = new Thread(fairLock, "Thread_t2")


    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()
  }
}
