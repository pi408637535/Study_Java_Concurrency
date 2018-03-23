package com.suty.currency.ch3

import java.util.concurrent.locks.ReentrantLock

/**
  *  ReentrantLock 支持interrupt.利用此功能可以避免申请临界资源时。线程不断等待的情况
  * @param lockType
  */
class InterruptReentrantLock(val lockType: Int) extends Runnable {
  override def run() = {
    if (lockType == 1) {
      InterruptReentrantLock.reentrantLock1.lockInterruptibly()
      try {
        Thread.sleep(1000)
      } catch {
        case e: InterruptedException => {
          println("InterruptedException")
          println(Thread.currentThread().getName)
        }
      } finally {
        if (InterruptReentrantLock.reentrantLock1.isHeldByCurrentThread)
          InterruptReentrantLock.reentrantLock1.unlock()

      }
    } else {
      InterruptReentrantLock.reentrantLock2.lockInterruptibly()
      Thread.sleep(1000)
      println(Thread.currentThread().getName)
      InterruptReentrantLock.reentrantLock2.unlock()
    }
  }
}

object InterruptReentrantLock {
  val reentrantLock1: ReentrantLock = new ReentrantLock();
  val reentrantLock2: ReentrantLock = new ReentrantLock();

  def main(args: Array[String]): Unit = {
    val interruptReentrantLock1 = new InterruptReentrantLock(1)
    val interruptReentrantLock2 = new InterruptReentrantLock(2)
    val t1 = new Thread(interruptReentrantLock1)
    val t2 = new Thread(interruptReentrantLock2)
    t1.start()
    t2.start()

    Thread.sleep(200)
    t1.interrupt()

    t1.join()
    t2.join()

  }
}
