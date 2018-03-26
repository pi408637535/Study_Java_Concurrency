package com.suty.currency.ch3

import java.util.concurrent.locks.ReentrantLock

/**
  * Created by piguanghua on 3/26/18.
  * ReentrantLock 指定是否实现公平锁。非公平锁，获取锁是随机的。公平锁是按照先后顺序。
  * 由于公平锁的获取是按照先后顺序，所有内部需要维护一个队列。下降了锁的性能。如果不是必要，建议不使用公平锁。
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
