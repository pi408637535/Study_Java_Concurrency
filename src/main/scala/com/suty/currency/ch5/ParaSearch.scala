package com.suty.currency.ch5

import java.util.concurrent.{Callable, Executors, Future}
import java.util.concurrent.atomic.AtomicInteger

import scala.collection.mutable.ListBuffer

/**
  * Created by piguanghua on 4/2/18.
  */

class SearchTask(val searchValue: Int, val begin: Int, val end: Int) extends Callable[Int] {

  def search(searchValue: Int, beginPos: Int, endPost: Int): Int = {
    for (i <- beginPos to endPost) {
      if (SearchTask.result.get() >= 0) {
        SearchTask.result.get()
      }


      if (SearchTask.array(i) == searchValue) {
        if (SearchTask.result.compareAndSet(-1, i))
          SearchTask.result.get()
      }
      i
    }
    -1
  }

  override def call(): Int = {
    search(searchValue, begin, end)
  }

}

object SearchTask {
  val result = new AtomicInteger(-1)
  val Thread_Num = 2
  val pool = Executors.newCachedThreadPool()
  var array = new Array[Int](3)

  def pSearch(searchValue:Int)={
    val subArraySize = array.length / Thread_Num + 1
    var listBuffer = List[Future[Int]]
    var i = 0
    while( i < array.length){
      var end = i + subArraySize
      if(end >= array.length)
        end = array.length


      //Java Future无此功能 +=
    }
  }
}


object ParaSearch {

}
