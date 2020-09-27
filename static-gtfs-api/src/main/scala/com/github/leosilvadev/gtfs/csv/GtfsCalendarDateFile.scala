package com.github.leosilvadev.gtfs.csv

import java.time.LocalDate

import better.files.File
import com.github.leosilvadev.gtfs.{CalendarDate, CalendarDateException}

object GtfsCalendarDateFile extends GtfsFile[CalendarDate] {

  override val requiredColumns: Map[String, Int] = Map(
    "service_id" -> 0,
    "date" -> 1,
    "exception_type" -> 2
  )

  override def read(file: File): Iterator[CalendarDate] = {
    readLines(file).map { cols =>
      CalendarDate(cols(0).toLong, LocalDate.parse(cols(1), dateFormatter), CalendarDateException(cols(2)))
    }
  }
}
