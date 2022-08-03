package Exploration.Fanfiction

import org.jsoup.Jsoup

import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`



object WebScrape extends App{

    def get_raw_data_ids(nbr: Int): List[String] ={
        /*
      Input: The nbr of the page for fanfiction.
      Example: nbr = 1 will get the first page with the latest fanfictions (newest) and nbr = 2 will get the second

      Returns: A list with raw strings that contains ids for each fanfiction that can be used to find them
       */
      val doc = Jsoup.connect(s"https://archiveofourown.org/works/search?commit=Search&page=${nbr}&utf8=%E2%9C%93&work_search%5Bbookmarks_count%5D=&work_search%5Bcharacter_names%5D=&work_search%5Bcomments_count%5D=&work_search%5Bcomplete%5D=&work_search%5Bcreators%5D=&work_search%5Bcrossover%5D=&work_search%5Bfandom_names%5D=&work_search%5Bfreeform_names%5D=&work_search%5Bhits%5D=&work_search%5Bkudos_count%5D=&work_search%5Blanguage_id%5D=&work_search%5Bquery%5D=&work_search%5Brating_ids%5D=&work_search%5Brelationship_names%5D=&work_search%5Brevised_at%5D=&work_search%5Bsingle_chapter%5D=0&work_search%5Bsort_column%5D=created_at&work_search%5Bsort_direction%5D=desc&work_search%5Btitle%5D=&work_search%5Bword_count%5D=").get() // try to see if it works
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
      (start to start + nbr_of_pages-1).foreach(n => list = list ++ get_clean_ids(n) )
      return list
  }

  println(get_fanfiction_ids(15,2))

  // create a function that takes the list of ids and webscrape

  //












}
