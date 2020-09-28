package com.github.leosilvadev.gtfs.csv

import java.nio.file.Path

import com.github.leosilvadev.gtfs.csv.exceptions.{FileReadException, InvalidFieldValueException}
import com.github.leosilvadev.gtfs.{GtfsCalendarDate, GtfsCalendarDateException}

object GtfsCalendarDateFile extends GtfsFile[GtfsCalendarDate] {

  override val requiredColumns: Map[String, Int] = Map(
    "service_id" -> 0,
    "date" -> 1,
    "exception_type" -> 2
  )

  override def read(
      filePath: Path
  ): Either[FileReadException, LazyList[Either[InvalidFieldValueException, GtfsCalendarDate]]] = {
    readLines(filePath).map { rows =>
      rows.map { cols =>
        for {
          id <- toLong(cols(0), "service_id")
          date <- toDate(cols(1), "date")
          calendarException <- GtfsCalendarDateException.from(cols(2), "exception_type")
        } yield GtfsCalendarDate(id, date, calendarException)
      }
    }
  }
}
