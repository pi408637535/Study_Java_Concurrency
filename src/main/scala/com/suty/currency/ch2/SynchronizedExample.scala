package com.suty.currency.ch2

/**
  * synchronized 同步时两种状态 a.等待锁 b.获取锁，然后执行
  * 在synchronize中出现异常，锁会自动回收
  */
class SynchronizedExample extends Runnable {
  override def run() = {

    try
      SynchronizedExample.a.synchronized {
        var i = 0
        while (true) {
          Thread.sleep(1010)
          println(Thread.currentThread.getName)
          i += 1
          if (i > 5) {
            var b = i / 0
          }
        }
      }
    catch {
      case e: Exception => {
        e.printStackTrace
        println(Thread.currentThread.getName)
      }
    }
    finally println(Thread.currentThread.getId + ":线程退出")
  }
}

object SynchronizedExample {
  /**
    * scala 类继承结构 Any:AnyVal,AnyRef(Java 的 Object + Scala Object)
    *
    */
  val a: String = "0"

  def main(args: Array[String]): Unit = {
    val t1 = new Thread(new SynchronizedExample, "MyThread1")
    val t2 = new Thread(new SynchronizedExample, "MyThread2")

    t1.start()
    t2.start()
    Thread.sleep(1000)

    t2.interrupt()

    t1.join()
    t2.join()
  }
}
