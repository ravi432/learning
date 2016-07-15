package com.test.MavenScala

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.immutable.List
import scala.collection.mutable.ListBuffer

object App {

  def main(args: Array[String]) {

    /* val conf = new SparkConf().setAppName("Testing").setMaster("local[2]");
   val sc = SparkContext.getOrCreate(conf);
   println("welocme to scala maven");
   val textfile = sc.textFile("file:///C:/Users/669822/Git/MavenScala/input/sample.txt");
   val b = textfile.flatMap { line => line.split(" ") }
   val c = b.map { word => (word,1) }
   val d = c.reduceByKey(_+_);
   d.foreach(f=>println(f._1,f._2 ));*/

    println(balance("((if (zero? x) max (/ 1 x)))".toList))
    //I told him (that it’s not (yet) done). (But he wasn’t listening)
    println(balance("I told him (that it’s not (yet) done). (But he wasn’t listening)".toList))
    //:-)
    println(balance(":-)".toList))
    //())(
    println(balance("(())(((((((((((((())))))))))))))".toList))
    println(countChange(4, List(1, 2)));

  }
/*def balance(chars: List[Char]): Boolean = {

    var openclose=0;

    if (!chars.isEmpty)process(chars)
    def process(chars: List[Char]) {
      if (!chars.isEmpty)
        if (chars.head.equals('(') && (openclose >= 0)) {
          openclose += 1;
          process(chars.tail)
        } else if (chars.head.equals(')')) {
          openclose -= 1;
          process(chars.tail)
        } else {
          process(chars.tail)
        }

    }
    return openclose == 0
  }*/
  def balance(chars: List[Char]): Boolean = {

   
    def process(chars: List[Char], openclose: Int): Boolean = {

      if (chars.isEmpty) {
        openclose == 0;
      } else {
        val s =
          if (chars.head.equals('(')) openclose + 1;
          else if (chars.head.equals(')')) openclose - 1;
          else openclose;

        if (s >= 0) process(chars.tail, s)
        else false

      }
    }
 process(chars, 0)
  }
  def countChange(money: Int, coins: List[Int]): Int = {
    def f(lastMaxCoin_total_coll: List[(Int, Int)], count: Int): Int = {
      if (lastMaxCoin_total_coll.isEmpty) {
        count
      } else {
        val b = ListBuffer[(Int, Int)]()
        var newCount = count
        for ((lastMaxCoin, total) <- lastMaxCoin_total_coll) {
          if (total < money) {
            for (c <- coins) {
              if (c >= lastMaxCoin) {
                val e = (c, total + c)
                b += e
              }
            }
          } else if (total == money) {
            newCount += 1
          }
        }

        f(b.toList, newCount)
      }
    }

    val b = coins.map { c => (c, c) }
    f(b, 0)
  }

}