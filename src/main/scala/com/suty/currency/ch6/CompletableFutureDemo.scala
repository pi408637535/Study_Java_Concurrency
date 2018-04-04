package com.suty.currency.ch6

import java.util.concurrent.{CompletableFuture, Executors}

/**
  * Created by piguanghua on 4/4/18.
  */

class AskThread(val re: CompletableFuture[Int]) extends Runnable {


  override def run(): Unit = {
    var myRe = 0
    myRe = re.get() * re.get()
    println(myRe)
  }
}

object CompletableFutureDemo {
  def main(args: Array[String]): Unit = {
    val future:CompletableFuture[Int] = new CompletableFuture[Int]()
    val ex = Executors.newSingleThreadExecutor()
    ex.submit(new AskThread(future))

    Thread.sleep(1000)
    //数据若没有准备好，请求线程就进入等待状态
    //可以手动设置future的完成状态
    future.complete(60)
  }
}
