package com.github.leosilvadev.gtfs.csv

import java.time.LocalDate

import better.files.File
import com.github.leosilvadev.gtfs.Calendar

object GtfsCalendarFile extends GtfsFile[Calendar] {

  override val requiredColumns: Map[String, Int] = Map(
    "service_id" -> 0,
    "monday" -> 1,
    "tuesday" -> 2,
    "wednesday" -> 3,
    "thursday" -> 4,
    "friday" -> 5,
    "saturday" -> 6,
    "sunday" -> 7,
    "start_date" -> 8,
    "end_date" -> 9
  )

  override def read(file: File): Iterator[Calendar] = {
    readLines(file).map(cols => {
      Calendar(
        cols(0).toLong,
        cols(1),
        cols(2),
        cols(3),
        cols(4),
        cols(5),
        cols(6),
        cols(7),
        LocalDate.parse(cols(8), dateFormatter),
        LocalDate.parse(cols(9), dateFormatter)
      )
    })
  }

}
