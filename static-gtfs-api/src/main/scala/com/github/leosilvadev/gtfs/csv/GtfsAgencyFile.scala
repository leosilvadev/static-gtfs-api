package com.github.leosilvadev.gtfs.csv

import java.nio.file.Path

import com.github.leosilvadev.gtfs.GtfsAgency
import com.github.leosilvadev.gtfs.csv.exceptions.{FileReadException, InvalidFieldValueException}

case class GtfsAgencyFile(globalAgencyId: Option[Long] = None) extends GtfsFile[GtfsAgency] {

  override val requiredColumns: Map[String, Int] = Map(
    "agency_name" -> 1,
    "agency_url" -> 2,
    "agency_timezone" -> 3
  )

  override val optionalColumns: Map[String, Int] = Map(
    "agency_id" -> 0,
    "agency_lang" -> 4,
    "agency_phone" -> 5,
    "agency_fare_url" -> 6,
    "agency_email" -> 7
  )

  override def read(
      filePath: Path
  ): Either[FileReadException, LazyList[Either[InvalidFieldValueException, GtfsAgency]]] = {
    readLines(filePath).map { rows =>
      rows.map { cols =>
        for {
          id <- toLong(cols(0), "agency_id", globalAgencyId)
          name <- toString(cols(1), "agency_name")
          url <- toString(cols(2), "agency_url")
          timezone <- toString(cols(3), "agency_timezone")
          lang = toOptionalString(cols(4))
          phone = toOptionalString(cols(5))
          fareUrl = toOptionalString(cols(6))
          email = toOptionalString(cols(7))
        } yield GtfsAgency(id, name, url, timezone, lang, phone, fareUrl, email)
      }
    }
  }
}
