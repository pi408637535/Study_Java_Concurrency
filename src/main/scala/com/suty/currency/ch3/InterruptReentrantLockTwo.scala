package com.suty.currency.ch3

import java.util.concurrent.locks.ReentrantLock

/**
  * InterruptReentrantLock 利用InterruptReentrantLock特性：终端响应
  * synchronized: ① 等待锁 ② 运行
  * @param lockType
  */
class InterruptReentrantLockTwo(val lockType: Int) extends Runnable {
  override def run() = {
    try {
      if (lockType == 1) {
        InterruptReentrantLockTwo.reentrantLock1.lockInterruptibly()
        println(Thread.currentThread().getName + "  Sleep...")
        Thread.sleep(60000)
        InterruptReentrantLockTwo.reentrantLock2.lockInterruptibly()
      }else{
        InterruptReentrantLockTwo.reentrantLock2.lockInterruptibly()
        println(Thread.currentThread().getName + "  Sleep...")
        Thread.sleep(100000)
        InterruptReentrantLockTwo.reentrantLock1.lockInterruptibly()
      }
      println("running")
      println(Thread.currentThread().getName)

    } catch {
      case interrupt: InterruptedException => {
       interrupt.printStackTrace()
      }
    } finally {
      if (InterruptReentrantLockTwo.reentrantLock1.isHeldByCurrentThread) {
        InterruptReentrantLockTwo.reentrantLock1.unlock()
      }
      if (InterruptReentrantLockTwo.reentrantLock2.isHeldByCurrentThread) {
        InterruptReentrantLockTwo.reentrantLock1.unlock()
      }
    }
  }
}

object InterruptReentrantLockTwo {
  val reentrantLock1: ReentrantLock = new ReentrantLock();
  val reentrantLock2: ReentrantLock = new ReentrantLock();

  def main(args: Array[String]): Unit = {
    val interruptReentrantLockTwo1 = new InterruptReentrantLockTwo(1)
    val interruptReentrantLockTwo2 = new InterruptReentrantLockTwo(2)
    val t1 = new Thread(interruptReentrantLockTwo1)
    val t2 = new Thread(interruptReentrantLockTwo2)
    t1.start()
    t2.start()
    println(Thread.currentThread().getName + "  Sleep...")
    Thread.sleep(80000)
    println(Thread.currentThread().getName + "  Wake up...")
    t1.interrupt()

    t1.join()
    t2.join()
  }
}
