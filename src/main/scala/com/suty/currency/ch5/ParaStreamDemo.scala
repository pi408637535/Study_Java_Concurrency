package com.suty.currency.ch5

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

/**
  * Created by piguanghua on 4/2/18.
  */

class Msg(var i:Double, var j:Double, var orgStr:String)

class Plus extends Runnable{
  override def run(): Unit = {
    while (true){
      val msg = Plus.bq.take()
      msg.j = msg.i + msg.j
      Multiply.bq.add(msg)
    }
  }
}

object Plus{
  var bq:BlockingQueue[Msg] = new LinkedBlockingQueue[Msg]()
}

class Multiply extends Runnable{
  override def run(): Unit = {
    while (true){
      val msg = Multiply.bq.take()
      msg.i = msg.i * msg.j
      Div.bq.add(msg)
    }
  }
}

object Multiply{
  var bq:BlockingQueue[Msg] = new LinkedBlockingQueue[Msg]()
}

class Div extends Runnable{
  override def run(): Unit = {
    while (true){
      val msg = Div.bq.take()
      msg.i = msg.i / 2
      println(msg.orgStr + "=" + msg.i)
    }
  }
}

object Div{
  var bq:BlockingQueue[Msg] = new LinkedBlockingQueue[Msg]()
}


object ParaStreamDemo {
  def main(args: Array[String]): Unit = {
    new Thread(new Plus).start()
    new Thread(new Multiply).start()
    new Thread(new Div).start()

    for(i <- 1 to 100){
      for(j <- 1 to 100){
        val msg = new Msg(i, j, s"i=$i,j=$j = ")
        Plus.bq.add(msg)
      }
    }
  }
}
