package com.suty.currency.ch3

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

/**
  * Created by piguanghua on 3/26/18.
  * 通过 InterruptReentrantLockTwo，ReentrantLockTimeLock 两个实例发现：ReentrantLock,Synchronized对比，ReentrantLock
  * 有两个新功能：1.中断响应 2.设定等待时间
  */
class ReentrantLockTimeLock extends Runnable {
  override def run(): Unit = {
    try {
      if (ReentrantLockTimeLock.lock.tryLock(5, TimeUnit.SECONDS)) {
        println("tryLock success")
        Thread.sleep(6000)
      } else {
        println("get lock failed")
      }
    } catch {
      case e: InterruptedException => {
        e.printStackTrace()
      }
    } finally {
      if(ReentrantLockTimeLock.lock.isHeldByCurrentThread){
        ReentrantLockTimeLock.lock.unlock()
      }
    }
  }
}

object ReentrantLockTimeLock {
  val lock: ReentrantLock = new ReentrantLock()

  def main(args: Array[String]): Unit = {
    val reentrantLockTimeLock:ReentrantLockTimeLock = new ReentrantLockTimeLock()
    val t1 = new Thread(reentrantLockTimeLock)
    val t2 = new Thread(reentrantLockTimeLock)

    t1.start()
    t2.start()

    t1.join()
    t1.join()


  }
}
