package com.suty.currency.ch5

import java.util.concurrent.{Callable, Executors, FutureTask}

/**
  * Created by piguanghua on 4/2/18.
  * Future设计模式
  *
  */

class RealData(val para:String) extends Callable[String]{
  override def call(): String = {
    var string = ""
    for(i <- 0 to 10){
        string += para
      Thread.sleep(100)
    }
    string
  }
}

object JavaFutureDemo extends App{
  val futureTask = new FutureTask[String](new RealData("a"))
  val es = Executors.newFixedThreadPool(10)
  es.submit(futureTask)

  Thread.sleep(2000)
  println(futureTask.get())
}
