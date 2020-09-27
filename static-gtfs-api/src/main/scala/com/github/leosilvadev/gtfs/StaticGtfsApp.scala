package com.github.leosilvadev.gtfs

object StaticGtfsApp extends App {

  /*
  def download(url: String, filename: String) = {
    new URL(url) #> new io.File(filename) !!
  }
  println("Downloading...")
  download(
    "https://transitfeeds.com/p/verkehrsverbund-berlin-brandenburg/213/latest/download",
    "/var/tmp/VBB_20200904.zip"
  )

  val file = file"/var/tmp/VBB_20200904.zip"
  println(s"Downloaded at $file, unzipping...")
  val outDir = file"/var/tmp/VBB_20200904_result"
  file.unzipTo(outDir)

  val outDir = file"/var/tmp/VBB_20200904_result"
  GtfsCalendarFile
    .read(Paths.get("file:/var/tmp/VBB_20200904_result/calendar.txt"))
    .foreach(calendar => println(s"Calendar found: $calendar"))
  GtfsCalendarDateFile
    .read(Paths.get("file:/var/tmp/VBB_20200904_result/calendar_dates.txt"))
    .foreach(calendar => println(s"Calendar date found: $calendar"))
   */
}
