package com.suty.currency.ch6

import java.util.concurrent.CountDownLatch

/**
  * Created by piguanghua on 4/9/18.
  * 其实并行化数组,就是将数组先分成小段，然后在最终在一个数组上进行排序
  */
class OddEventSortTask(val i:Int, val latch:CountDownLatch) extends Runnable{
  override def run(): Unit = {
    if(OddEvenSort.array(i) > OddEvenSort.array(i+1)){
      val temp = OddEvenSort.array(i)
      OddEvenSort.array(i) = OddEvenSort.array(i+1)
      OddEvenSort.array(i+1) = temp

      OddEvenSort.setExchFlay(1)
    }
    latch.countDown()
  }
}

class OddEvenSort {


}


object OddEvenSort {
  var exchFlag: Int = 1
  val array = Array(1,2,3,4,5,6)
  def setExchFlay(v: Int) = {
    synchronized {
      OddEvenSort.exchFlag = v
    }
  }
}
