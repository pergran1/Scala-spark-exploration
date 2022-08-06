package Exploration.Fanfiction

// https://stackoverflow.com/questions/7560083/jsoup-divclass-syntax-works-whereas-div-class-syntax-doesnt-why
// https://jsoup.org/cookbook/extracting-data/selector-syntax

import org.jsoup.Jsoup

import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`
import scala.collection.mutable
import scala.xml.Document



object WebScrape extends App{

    def get_raw_data_ids(nbr: Int): List[String] ={
        /*
      Input: The nbr of the page for fanfiction.
      Example: nbr = 1 will get the first page with the latest fanfictions (newest) and nbr = 2 will get the second

      Returns: A list with raw strings that contains ids for each fanfiction that can be used to find them
       */
      val doc = Jsoup.connect(s"https://archiveofourown.org/works/search?commit=Search&page=$nbr&utf8=%E2%9C%93&work_search%5Bbookmarks_count%5D=&work_search%5Bcharacter_names%5D=&work_search%5Bcomments_count%5D=&work_search%5Bcomplete%5D=&work_search%5Bcreators%5D=&work_search%5Bcrossover%5D=&work_search%5Bfandom_names%5D=&work_search%5Bfreeform_names%5D=&work_search%5Bhits%5D=&work_search%5Bkudos_count%5D=&work_search%5Blanguage_id%5D=&work_search%5Bquery%5D=&work_search%5Brating_ids%5D=&work_search%5Brelationship_names%5D=&work_search%5Brevised_at%5D=&work_search%5Bsingle_chapter%5D=0&work_search%5Bsort_column%5D=created_at&work_search%5Bsort_direction%5D=desc&work_search%5Btitle%5D=&work_search%5Bword_count%5D=").get() // try to see if it works
      val list_ids = doc.select("h4").toList
      list_ids.map(_.toString) // Convert org.jsoup.nodes.Element into String

    }

    def get_clean_ids(nbr: Int): List[String] ={
      /*
      Input: Nbr: The page nbr that should be scrape
      Return: A list of only fanficion ids, List(40752909, 40752888)
       */
      val raw_ids = get_raw_data_ids(nbr)
      raw_ids.map(str => if (str.indexOf("/works/")>0) str.substring(37,45) else "no fanfic" ).filter(_ != "no fanfic")
    }

    def get_fanfiction_ids(start: Int, nbr_of_pages: Int): List[String]= {
      /*
      Inputs: User choose the start index and how many pages (I start at 15 so the fanfictions have more hits)
      Returns: A full list of all ids, 2 pages provides with 40 ids, 20 for each page
       */
      var list: List[String] = List()
      /*
      for (i <- Range(0, nbr_of_pages)) {
        list = list ++ get_clean_ids(i+1)
      }
       */

      // The loop above does the same thing as below, concate one list from each list from get_clean_ids
      // ++ concate the list
      (start to start + nbr_of_pages-1).foreach(n => list = list ++ get_clean_ids(n) )
      return list
  }

  // Here is the list that contains all the fanfic ids
  val all_ids = get_fanfiction_ids(15,2)




  def get_fanfic(fanfic_link: String): org.jsoup.nodes.Document = {
    /*
    Input: One fanfic id
    Output the webscraped html for the corresponding id
     */
    Jsoup.connect(fanfic_link).get()
  }

  def insert_metric_values(fanfic_id: String, link: String, fanfic_text: String, html_soup: org.jsoup.nodes.Document,
                           fanfic_map: mutable.Map[String, List[String]]): Unit = {

    // Map for Fanfiction, the keys are the heading and the values are the class when webscraping
    val metric_map = scala.collection.mutable.Map(
      "dd" -> List("rating", "category", "fandom", "relationship",
        "character", "language", "published", "words",
        "chapters", "comments", "kudos", "bookmarks",
        "hits"),
      "h2" -> List("title"),
      "h3" -> List("byline")
    )

    fanfic_map.updateWith("id")({ case Some(list) => Some(fanfic_id :: list) })
    fanfic_map.updateWith("link")({ case Some(list) => Some(link :: list) })

    metric_map.keys.foreach( key => metric_map(key).
      foreach(list_value => if (fanfic_map.exists(_._1 == list_value)) {
        val fanfic_value = html_soup.select(s"$key.$list_value").text()
        fanfic_map.updateWith(list_value)({ case Some(list) => Some(fanfic_value :: list) })
      } else {
        println(s"$list_value did not exist ")
      }) )
  }


  // Example of the structure to scrape category: html_soup.select("dd.category").text()

  def read_fanfic(fanfic_id_list: List[String]): mutable.Map[String, List[String]] = {
    // Create map that will contain all the data when webscraping a fanfic
    // byline is for author
    val fanfic_map: mutable.Map[String, List[String]]  = mutable.Map("id" -> List(), "link"-> List(), "text"-> List(),
      "link"-> List(), "rating"-> List(), "category"-> List(), "fandom"-> List(), "relationship"-> List(),
      "character"-> List(), "language" ->List(), "published"-> List(), "words"-> List(), "chapters"-> List(),
      "comments"-> List(), "kudos"-> List(), "bookmarks"-> List(), "hits"-> List(), "title"-> List(),
      "byline"-> List()
    )
    // This is a for loop
    for (id <- fanfic_id_list) {
      Thread.sleep(6000)  // let it sleep a little
      val fanfic_link = f"https://archiveofourown.org/works/$id?view_adult=true"
      println(fanfic_link)
      val id_doc: org.jsoup.nodes.Document = get_fanfic(fanfic_link) // get the html do
      val fanfic_text = id_doc.select("div.userstuff").text()// get the fanfic text


      // use the function to insert values intp metric map
      insert_metric_values(id, fanfic_link, fanfic_text, id_doc, fanfic_map)

    }

    return fanfic_map
  }


 val testar = read_fanfic(all_ids)

 println(testar)















}
