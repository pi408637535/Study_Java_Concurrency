package com.suty.currency.ch5

import java.util.concurrent.{Callable, Executors, Future}
import java.util.concurrent.atomic.AtomicInteger

import scala.collection.mutable.ListBuffer


/**
  * Created by piguanghua on 4/2/18.
  */

class SearchTask(val searchValue: Int, val begin: Int, val end: Int) extends Callable[Int] {

  def search(searchValue: Int, beginPos: Int, endPost: Int): Int = {
    for (i <- beginPos until endPost) {
      if (SearchTask.result.get() >= 0) {
        SearchTask.result.get()
      }


      if (SearchTask.array(i) == searchValue) {
        //如果设置失败，其他线程已经找到了位置
        if (SearchTask.result.compareAndSet(-1, i)) {
          SearchTask.result.get()
        }
        return i
      }
    }
    return -1
  }

  override def call(): Int = {
    search(searchValue, begin, end)
  }

}

object SearchTask {
  var result = new AtomicInteger(-1)
  val Thread_Num = 5
  val pool = Executors.newFixedThreadPool(10)
  var array: Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 0, 11, 34, 56, 7, 89, 90)

  def pSearch(searchValue: Int): Int = {
    val subArraySize = array.length / Thread_Num + 1
    var re: ListBuffer[Future[Int]] = new ListBuffer[Future[Int]]


    var i = 0
    while (i < array.length) {
      var end = i + subArraySize
      if (end >= array.length)
        end = array.length

      val futureResult = pool.submit(new SearchTask(searchValue, i, end))
      re.+=(futureResult)

      i += subArraySize
    }
    Thread.sleep(1000)

    for (future <- re) {
      while (!future.isDone){}
      if (future.get() > 0) {
        return future.get()
      }
    }

    -1
  }

  def main(args: Array[String]): Unit = {
    println(SearchTask.pSearch(4))
  }
}


object ParaSearch {

}
