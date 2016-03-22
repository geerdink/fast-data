//import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.SparkContext._
//
//object SimpleApp {
//  def main(args: Array[String]) {
//
//    val conf = new SparkConf().setAppName("SparkING").setMaster("local[1]")
//    val sc = new SparkContext(conf)
//
//    val logFile = "README.md"
//
//    val logData = sc.textFile(logFile, 2).cache()
//    val numAs = logData.filter(_.contains("a")).count()
//    val numBs = logData.filter(_.contains("b")).count()
//
//    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
//
//    Pi.Calculate(sc)
//
//    sc.stop()
//  }
//}
