package com.suty.currency.ch3

import java.util.concurrent.{ForkJoinPool, RecursiveTask}

import scala.collection.mutable.ListBuffer

/**
  * Created by piguanghua on 3/30/18.
  */
class CountTask(start: Long, end: Long) extends RecursiveTask[Long] {
  val THRESHOLD = 100000L

  def compute(): Long = {
    var sum = start
    val canCompute = (end - start) < THRESHOLD
    if (canCompute) {
      for (i <- start to end) {
        sum += i
      }
    } else {
      val step = (start + end) / 100
      var subTasks: ListBuffer[CountTask] = new ListBuffer[CountTask]()
      var pos = start
      for (i <- 0 to 100) {
        var lastOne = pos + step
        if (lastOne > end)
          lastOne = end
        var subTask = new CountTask(pos, lastOne)
        pos += step + 1
        subTasks += subTask
        subTask.fork()
      }
      subTasks.foreach(ele => sum += ele.join())
    }
    sum
  }
}

object RecursiveTask {
  def main(args: Array[String]): Unit = {
    val forkJoinPool = new ForkJoinPool()
    val task = new CountTask(0, 2000000L)
    val result = forkJoinPool.submit(task)

    val res = result.get()
    println("sum=" + res)
  }
}
