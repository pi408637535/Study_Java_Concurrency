package com.suty.currency.ch3

import java.util.concurrent.locks.{Condition, ReentrantLock}

/**
  * Created by piguanghua on 3/26/18.
  */
class ReentrantLockCondition extends Runnable{
  override def run(): Unit = {
    try{
    ReentrantLockCondition.lock.lock()
    ReentrantLockCondition.condition.await()  //释放锁
    println("Thread is going on")

    }catch {
      case e:InterruptedException =>{
        e.printStackTrace()
      }
    }finally {
      ReentrantLockCondition.lock.unlock()
    }
  }
}

object ReentrantLockCondition{
  val lock:ReentrantLock = new ReentrantLock()
  val condition : Condition = lock.newCondition()

  def main(args: Array[String]): Unit = {
    val reentrantLockCondition = new ReentrantLockCondition();
    val thread = new Thread(reentrantLockCondition)
    thread.start()

    Thread.sleep(2000)
    println("Main Thread is running... ")
    ReentrantLockCondition.lock.lock()
    ReentrantLockCondition.condition.signal()
    ReentrantLockCondition.lock.unlock()
  }
}