package com.suty.currency.ch3

import java.util.concurrent._

import com.sun.org.apache.xerces.internal.parsers.CachingParserPool.SynchronizedGrammarPool

import scala.concurrent.duration.TimeUnit

/**
  * Created by piguanghua on 3/29/18.
  */
class TrackThreadPoolExecutor
(val corePoolSize: Int, val maximumPoolSize: Int, val keepAliveTime:Int, val timeUnit: TimeUnit, val workQueue: BlockingQueue[Runnable])
  extends ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workQueue) {

  override def execute(command: Runnable): Unit = {
    super.execute(warp(command, clientTrace(), Thread.currentThread().getName))
  }

  override def submit(task: Runnable): Future[_] = {
    super.submit(warp(task, clientTrace(), Thread.currentThread().getName))
  }

  def clientTrace(): Exception = {
    return new Exception("Client stack trace")
  }

  def warp(task: Runnable, clientStack: Exception, clientThreadName: String): Runnable = {
    new Runnable {
      override def run() = {
        try {
          task.run()
        } catch {
          case e: Exception => {
            e.printStackTrace()
            throw e
          }
        }
      }
    }
  }



}

object TrackThreadPoolExecutor{
  def main(args: Array[String]): Unit = {
    val pool = new TrackThreadPoolExecutor(0, Int.MaxValue, 0, TimeUnit.SECONDS, new SynchronousQueue[Runnable]());
    for(i <- 0 until 10){
      pool.execute(new DivTask(100, i))
    }
  }
}
