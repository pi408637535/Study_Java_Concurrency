package com.suty.currency.ch3

import java.util.concurrent.locks.ReentrantLock


class ReentrantLockSimple extends Runnable{
  override def run() = {
    for(i <- 1 to 10000){
      ReentrantLockSimple.lock.lock()
      println(ReentrantLockSimple.lock.hashCode())
      println(Thread.currentThread.getName)
      println("run: " + i)
      ReentrantLockSimple.lock.unlock()
    }
  }
}
object ReentrantLockSimple extends {
  val lock: ReentrantLock = new ReentrantLock()
  def main(args: Array[String]): Unit = {
    val reentrantLockSimple = new ReentrantLockSimple
    val t1 = new Thread(reentrantLockSimple)
    val t2 = new Thread(reentrantLockSimple)

    t1.start()
    t2.start()

    t1.join()
    t2.join()
  }
}
