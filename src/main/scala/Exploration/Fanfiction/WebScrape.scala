package Exploration.Fanfiction

import org.jsoup.Jsoup



object WebScrape extends App{

    def get_raw_data_ids(nbr: Int): List[String] ={
        /*
      Input: The nbr of the page for fanfiction.
      Example: nbr = 1 will get the first page with the latest fanfictions (newest), nbr = 2 will get page 2 and so on

      Returns: A list with raw strings that contains ids for each fanfiction that can be used to find them
       */
      val doc = Jsoup.connect(s"https://archiveofourown.org/works/search?commit=Search&page=${1}&utf8=%E2%9C%93&work_search%5Bbookmarks_count%5D=&work_search%5Bcharacter_names%5D=&work_search%5Bcomments_count%5D=&work_search%5Bcomplete%5D=&work_search%5Bcreators%5D=&work_search%5Bcrossover%5D=&work_search%5Bfandom_names%5D=&work_search%5Bfreeform_names%5D=&work_search%5Bhits%5D=&work_search%5Bkudos_count%5D=&work_search%5Blanguage_id%5D=&work_search%5Bquery%5D=&work_search%5Brating_ids%5D=&work_search%5Brelationship_names%5D=&work_search%5Brevised_at%5D=&work_search%5Bsingle_chapter%5D=0&work_search%5Bsort_column%5D=created_at&work_search%5Bsort_direction%5D=desc&work_search%5Btitle%5D=&work_search%5Bword_count%5D=").get() // try to see if it works
      val list_ids = doc.select("h4").toList
      list_ids.map(_.toString) // Convert org.jsoup.nodes.Element into String

    }

  val raw_ids = get_raw_data_ids(1)

  print(raw_ids(0).indexOf("/works/")) // -1 if it does not exist









}
