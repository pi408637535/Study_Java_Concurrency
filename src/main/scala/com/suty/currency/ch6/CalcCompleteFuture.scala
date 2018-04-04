package com.suty.currency.ch6

import java.util.concurrent.CompletableFuture

/**
  * Created by piguanghua on 4/4/18.
  */
object CalcCompleteFuture {

  def calc(para:Int):Int={
    Thread.sleep(1000)
    return para * para
  }

  def main(args: Array[String]): Unit = {
    // val future:CompletableFuture[Int] = CompletableFuture.supplyAsync(()=>calc)
  }
}
