package com.suty.currency.ch3

import java.util.concurrent.Executors

/**
  * Created by piguanghua on 3/29/18.
  */
object ExecutorServiceDemo {
  def main(args: Array[String]): Unit = {
    val executorService = Executors.newSingleThreadExecutor();
    val future = executorService.submit(new Runnable() {
      override def run(): Unit = {
        println("Asynchronous task")
      }
    })

    while(!future.isDone){}
  }
}
