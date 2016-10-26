package etl

import org.apache.spark.sql.SparkSession

object ETL {
  def main(args: Array[String]) {

    // initialize Spark for batch processing
    val spark = SparkSession.builder
      .appName("spark-etl")
      .master("local[*]")
      .getOrCreate()

    // get data from HDFS
    val textFile = spark.sparkContext
      .textFile("hdfs://data/exported_raw.csv")

    // cache to do multiple operations
    textFile.cache()



    // stop batch
    spark.stop()
  }
}