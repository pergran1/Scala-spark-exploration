package Exploration.Fanfiction

// https://stackoverflow.com/questions/7560083/jsoup-divclass-syntax-works-whereas-div-class-syntax-doesnt-why
// https://jsoup.org/cookbook/extracting-data/selector-syntax

import org.jsoup.Jsoup

import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`
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

  val all_ids = get_fanfiction_ids(15,2)



  // create a function that takes the list of ids and webscrape
  def read_fanfic(fanfic_id: String): org.jsoup.nodes.Document = {
    s"https://archiveofourown.org/works/$fanfic_id?view_adult=true"
    val doc = Jsoup.connect(s"https://archiveofourown.org/works/$fanfic_id?view_adult=true").get() // try to see if it works
    return doc
  }


  println(all_ids(15))
  var testar = read_fanfic(all_ids(15))
  val fanfic_text = testar.select("div.userstuff").text().substring(13)
  val rating = testar.select("dd.rating").text() // ok
  println("category")
  println(testar.select("dd.category").text()) // remove the first part
  println("fandom")
  println(testar.select("dd.fandom").text()) // remove the first part
  println("relationship")
  println(testar.select("dd.relationship").text()) // remove the first part
  println("character")
  println(testar.select("dd.character").text()) // remove the first part
  println("language")
  println(testar.select("dd.language").text()) // remove the first part
  println("published")
  println(testar.select("dd.published").text()) // remove the first part
  println("words")
  println(testar.select("dd.words").text()) // remove the first part
  println("chapters")
  println(testar.select("dd.chapters").text()) // remove the first part
  println("comments")
  println(testar.select("dd.comments").text()) // remove the first part
  println("kudos")
  println(testar.select("dd.kudos").text()) // remove the first part
  println("bookmarks")
  println(testar.select("dd.bookmarks").text()) // remove the first part
  println("hits")
  println(testar.select("dd.hits").text()) // remove the first part
  println("title heading")
  println(testar.select("h2.title")) // remove the first part
  println("author")
  println(testar.select("h3.byline").text()) // remove the first part
  println("NÄSTA \n")
  println(testar)















}
