package Exploration.Fanfiction
import Exploration.RockTheJVMSparkCourse.SparkIntro.spark
import org.apache.spark.sql.SparkSession

object KPIs_creation extends App{

  //create the spark session
  val spark = SparkSession.builder()
    .appName("KPIs_Creation")
    .config("spark.master", "local")
    .getOrCreate()

  //reading the data
  val df = spark.read
    .format("csv")
    .option("inferSchema", "true")
    .option("header", "true")
    .option("delimiter", ",")
    .option("multiline", "true")
    .option("escape", """"""") // in pyspark it was: escape = '"' which was much cleaner
    .load("src/main/data/fanfiction_data_2022-06-05.csv")

  val sc = spark.sparkContext
  sc.setLogLevel("ERROR") //less error text so easier to see

  df.show()



  // create a function that saves dataframe in a good format, example txt to showcase the dataframe
}
