package com.github.leosilvadev.gtfs

import java.time.LocalDate

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException

class Domain {}

case class Agency(id: Long, name: String, url: String, timezone: String, lang: Option[String], phone: Option[String])

case class Calendar(
    serviceId: Long,
    monday: Boolean,
    tuesday: Boolean,
    wednesday: Boolean,
    thursday: Boolean,
    friday: Boolean,
    saturday: Boolean,
    sunday: Boolean,
    startDate: LocalDate,
    endDate: LocalDate
)

sealed trait CalendarDateException

object CalendarDateException {

  def from(value: String, field: String): Either[InvalidFieldValueException, CalendarDateException] =
    try {
      if (value == "1") Right(AddDate)
      else if (value == "2") Right(RemoveDate)
      else Left(new InvalidFieldValueException(field, value))
    } catch {
      case _: Throwable => Left(new InvalidFieldValueException(field, value))
    }
}
object AddDate extends CalendarDateException
object RemoveDate extends CalendarDateException

case class CalendarDate(serviceId: Long, date: LocalDate, exceptionType: CalendarDateException)

case class Route(
    id: String,
    agencyId: Long,
    shortName: Option[String],
    longName: Option[String],
    `type`: Int,
    color: Option[String],
    textColor: Option[String],
    description: Option[String]
)
