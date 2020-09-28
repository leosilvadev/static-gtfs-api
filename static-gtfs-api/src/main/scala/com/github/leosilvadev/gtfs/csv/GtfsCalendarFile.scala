package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.GtfsCalendar
import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException

object GtfsCalendarFile extends GtfsFile[GtfsCalendar] {

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

  override def parse(
      cols: Array[String]
  ): Either[InvalidFieldValueException, GtfsCalendar] =
    for {
      serviceId <- toLong(cols(0), "service_id")
      monday <- toBoolean(cols(1), "monday")
      tuesday <- toBoolean(cols(2), "tuesday")
      wednesday <- toBoolean(cols(3), "wednesday")
      thursday <- toBoolean(cols(4), "thursday")
      friday <- toBoolean(cols(5), "friday")
      saturday <- toBoolean(cols(6), "saturday")
      sunday <- toBoolean(cols(7), "sunday")
      startDate <- toDate(cols(8), "start_date")
      endDate <- toDate(cols(9), "end_date")
    } yield GtfsCalendar(
      serviceId,
      monday,
      tuesday,
      wednesday,
      thursday,
      friday,
      saturday,
      sunday,
      startDate,
      endDate
    )

}
