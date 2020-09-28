package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException
import com.github.leosilvadev.gtfs.{GtfsCalendarDate, GtfsCalendarDateException}

object GtfsCalendarDateFile extends GtfsFile[GtfsCalendarDate] {

  override val requiredColumns: Map[String, Int] = Map(
    "service_id" -> 0,
    "date" -> 1,
    "exception_type" -> 2
  )

  override def parse(
      cols: Array[String]
  ): Either[InvalidFieldValueException, GtfsCalendarDate] =
    for {
      id <- toLong(cols(0), "service_id")
      date <- toDate(cols(1), "date")
      calendarException <- GtfsCalendarDateException.from(cols(2), "exception_type")
    } yield GtfsCalendarDate(id, date, calendarException)
}
