package com.github.leosilvadev.gtfs.csv

import java.nio.file.Path
import java.time.LocalDate

import com.github.leosilvadev.gtfs.csv.exceptions.FileReadException
import com.github.leosilvadev.gtfs.{CalendarDate, CalendarDateException}

object GtfsCalendarDateFile extends GtfsFile[CalendarDate] {

  override val requiredColumns: Map[String, Int] = Map(
    "service_id" -> 0,
    "date" -> 1,
    "exception_type" -> 2
  )

  override def read(filePath: Path): Either[FileReadException, LazyList[CalendarDate]] = {
    readLines(filePath).map { rows =>
      rows.map { cols =>
        CalendarDate(cols(0).toLong, LocalDate.parse(cols(1), dateFormatter), CalendarDateException(cols(2)))
      }
    }
  }
}
