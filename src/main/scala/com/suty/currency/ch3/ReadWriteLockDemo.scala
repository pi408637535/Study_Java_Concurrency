package com.suty.currency.ch3

import java.util.concurrent.locks.{Lock, ReentrantReadWriteLock}

import scala.util.Random

/**
  * Created by piguanghua on 3/26/18.
  */
class ReadWriteLockDemo(var value:Int) {

  def handleRead(lock:Lock):Unit={
    lock.lock()
    Thread.sleep(1000)
    lock.unlock()
  }

  def handleWrite(lock:Lock, index:Int)={
    lock.lock()
    Thread.sleep(1000)
    lock.unlock()
    value = index
  }
}



object ReadWriteLockDemo {
  val readWriteLock = new ReentrantReadWriteLock()
  val readLock = readWriteLock.readLock()
  val writeLock = readWriteLock.writeLock()

  def main(args: Array[String]): Unit = {

    val readWriteLockDemo:ReadWriteLockDemo = new ReadWriteLockDemo(1)

    val writeRunnable = new Runnable {
      override def run() = {
        readWriteLockDemo.handleWrite(ReadWriteLockDemo.writeLock, new Random().nextInt())
      }
    }


    val readRunnable = new Runnable {
      override def run() = {
        readWriteLockDemo.handleRead(ReadWriteLockDemo.readLock)
      }
    }

   /* val startTime=System.nanoTime
    for(i <-  1 to 20){
      val thread =  new Thread(readRunnable)
      thread.start()
    }
    val endtime=System.nanoTime
    val delta=endtime-startTime
    println( delta/1000000d )

    Thread.sleep(30000)*/

    val startTime=System.nanoTime
    for(i <-  1 to 20){
      val thread = new Thread(writeRunnable)
      thread.start()
      thread.join()
    }
    val endtime=System.nanoTime
    val delta=endtime-startTime
    println( delta/1000000d )


  }

}
