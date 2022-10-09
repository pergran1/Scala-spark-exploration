package Exploration.RockTheJVMSparkCourse

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._

object dataframe_basic extends App{
  // Create a Sparksession
  val spark = SparkSession.builder()
    .appName("Datafram Basics")
    .config("spark.master", "local")
    .getOrCreate()

  // reading a DF
  val first_df = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/main/data/cars.json")

  val sc = spark.sparkContext
  sc.setLogLevel("ERROR") //less error text so easier to see

  // showing a df
  val test = first_df.limit(5)

  test.show()



  //print the structure
  println("The sctructure is:")
  first_df.printSchema()

  // get rows
  println("The rows are")
  first_df.take(10).foreach(println)

  // create a schema
  val cars_schema = StructType(Array(
    StructField("Name", StringType),
    StructField("Miles_per_Gallon", DoubleType),
    StructField("Cylinders", LongType),
    StructField("Displacement", DoubleType),
    StructField("Horsepower", LongType),
    StructField("Weight_in_lbs", LongType),
    StructField("Acceleration", DoubleType),
    StructField("Year", StringType),
    StructField("Origin", StringType)
  ))


  val cars_df_schema = first_df.schema
  println(s"\nThe type for the df is: " + cars_df_schema)


  // Read the data with own schema
  val new_df = spark.read
    .format("json")
    .schema(cars_schema)  // using the specificed schema
    .load("src/main/data/cars.json")

  // create row by hand
  val my_row = Row("chevrolet chevelle malibu",18,8,307,130,3504,12.0,"1970-01-01","USA")

  // create DF from tuples
  val cars_tuples = Seq(
    ("chevrolet chevelle malibu",18,8,307,130,3504,12.0,"1970-01-01","USA"),
    ("buick skylark 320",15,8,350,165,3693,11.5,"1970-01-01","USA"),
    ("plymouth satellite",18,8,318,150,3436,11.0,"1970-01-01","USA"),
    ("amc rebel sst",16,8,304,150,3433,12.0,"1970-01-01","USA"),
    ("ford torino",17,8,302,140,3449,10.5,"1970-01-01","USA"),
    ("ford galaxie 500",15,8,429,198,4341,10.0,"1970-01-01","USA"),
    ("chevrolet impala",14,8,454,220,4354,9.0,"1970-01-01","USA"),
    ("plymouth fury iii",14,8,440,215,4312,8.5,"1970-01-01","USA"),
    ("pontiac catalina",14,8,455,225,4425,10.0,"1970-01-01","USA"),
    ("amc ambassador dpl",15,8,390,190,3850,8.5,"1970-01-01","USA")
  )

  val cars_tuples_df = spark.createDataFrame(cars_tuples) // schema auto-inferred

  // note: DFs have schemas, rows do not
  import spark.implicits._
  val manual_cars = cars_tuples.toDF("Name", "MPG", "Cylinders", "Displacement", "HP", "Weight", "Acceleration", "Year", "CountryOrigin")

  println(manual_cars)

  manual_cars.show()


  /**
   * Exercise:
   * 1) Create a manual DF describing smartphones
   *   - make
   *   - model
   *   - screen dimension
   *   - camera megapixels
   *
   * 2) Read another file from the data/ folder, e.g. movies.json
   *   - print its schema
   *   - count the number of rows, call count()
   */

  // Task 1 --> Here I will use sets
  val phones = Seq(
    ("apple", "iphone 10", "10", "64"),
    ("sony", "Xperia", "8.4", "32"),
    ("samsung", "Galaxy S", "10.5", "64"),
  )

  val phone_df = phones.toDF("make", "model", "screen_dimension", "megapixels")
  phone_df.show()


  val movies = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/main/data/movies.json")

  movies.printSchema()
  println(s"The number of rows is: ${movies.count()}")




}
