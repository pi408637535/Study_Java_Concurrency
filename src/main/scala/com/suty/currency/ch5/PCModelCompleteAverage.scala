package com.suty.currency.ch5

import java.util.concurrent._
import java.util.concurrent.atomic.AtomicInteger

import scala.util.Random

/**
  * Created by piguanghua on 3/30/18.
  */

class Producer(var queue:BlockingQueue[PCData]) extends Runnable{

  private var isRunning:Boolean = true

  override def run(): Unit = {
    val random = new Random()
    println("start producer id=" + Thread.currentThread().getId)

    while(isRunning){
      Thread.sleep(random.nextInt(Consumer.SLEEPTIME))
      println("data" + "is put into queue")
      if(!queue.offer(new PCData(Producer.count.incrementAndGet()), 2, TimeUnit.SECONDS)){
        println("failed to put data")
      }
    }
  }

  def stop()={
    isRunning = false
  }
}

object Producer{
  val count:AtomicInteger = new AtomicInteger()
}


class Consumer(var queue:BlockingQueue[PCData]) extends Runnable{
  override def run(): Unit = {
    println(Thread.currentThread().getId)
    val random = new Random()
    try{
      while (true){
        val data = queue.take()
        if(data != null){
          val re  = data.intData * data.intData
          println(" pow(2)" + re)
          Thread.sleep(random.nextInt(Consumer.SLEEPTIME))
        }
      }
    }
  }
}

object Consumer{
  val SLEEPTIME = 1000
}


class PCData(val intData:Int){
  override def toString: String = "data" + intData
}

object PCModelCompleteAverage {
  def main(args: Array[String]): Unit = {
    val queue = new LinkedBlockingQueue[PCData](10)
    val producer1 = new Producer(queue)
    val producer2 = new Producer(queue)
    val producer3 = new Producer(queue)

    val consumer1 = new Consumer(queue)
    val consumer2 = new Consumer(queue)
    val consumer3 = new Consumer(queue)

    val es = Executors.newFixedThreadPool(4)
    es.submit(producer1)
    es.submit(producer2)
    es.submit(producer3)

    es.submit(consumer1)
    es.submit(consumer2)
    es.submit(consumer3)

    Thread.sleep( 10 * 1000)

    producer1.stop()
    producer2.stop()
    producer3.stop()

    es.shutdown()



  }
}
