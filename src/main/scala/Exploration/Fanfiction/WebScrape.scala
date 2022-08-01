package Exploration.Fanfiction

import org.jsoup.Jsoup

object WebScrape extends App{
  val doc = Jsoup.connect("http://en.wikipedia.org/").get() // try to see if it works
  println(doc)


}
