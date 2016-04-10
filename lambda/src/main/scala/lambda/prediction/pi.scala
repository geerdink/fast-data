package lambda.prediction

/**
  * Created by Bas on 22-3-2016.
  */

import org.apache.spark._

import scala.math.random

object Pi {
  def Calculate(sc: SparkContext) = {
    val slices = 2
    val n = 100000 * slices

    val count = sc.parallelize(1 to n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x * x + y * y < 1) 1 else 0
    }.reduce(_ + _)

    println("Pi is roughly " + 4.0 * count / n)
  }
}
