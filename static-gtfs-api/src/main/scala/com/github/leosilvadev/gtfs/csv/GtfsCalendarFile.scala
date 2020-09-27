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
          tuesday <- toBoolean(cols(1), "tuesday")
          wednesday <- toBoolean(cols(1), "wednesday")
          thursday <- toBoolean(cols(1), "thursday")
          friday <- toBoolean(cols(1), "friday")
          saturday <- toBoolean(cols(1), "saturday")
          sunday <- toBoolean(cols(1), "sunday")
          startDate <- toDate(cols(1), "start_date")
          endDate <- toDate(cols(1), "end_date")
        } yield Calendar(serviceId, monday, tuesday, wednesday, thursday, friday, saturday, sunday, startDate, endDate)
      )
    )
  }

}
