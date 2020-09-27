package com.github.leosilvadev.gtfs.csv

import java.nio.file.Path

import com.github.leosilvadev.gtfs.Calendar
import com.github.leosilvadev.gtfs.csv.exceptions.{FileReadException, InvalidFieldValueException}

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

  override def read(
      filePath: Path
  ): Either[FileReadException, LazyList[Either[InvalidFieldValueException, Calendar]]] = {
    readLines(filePath).map(lines =>
      lines.map(cols =>
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
        } yield Calendar(serviceId, monday, tuesday, wednesday, thursday, friday, saturday, sunday, startDate, endDate)
      )
    )
  }

}
