package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException
import com.github.leosilvadev.gtfs.domain.GtfsCalendarDate

object GtfsCalendarDateFile extends GtfsFile[GtfsCalendarDate] {

  override val requiredColumns: Map[String, Int] = Map(
    "service_id" -> 0,
    "date" -> 1,
    "exception_type" -> 2
  )

  override def parse(
      cols: Array[String]
  ): Either[InvalidFieldValueException, GtfsCalendarDate] = {
    for {
      id <- toString(cols(0), "service_id")
      date <- toDate(cols(1), "date")
      exceptionType <- toString(cols(2), "exception_type")
      calendarDate <- GtfsCalendarDate(id, date, exceptionType, "exception_type")
    } yield calendarDate
  }
}
