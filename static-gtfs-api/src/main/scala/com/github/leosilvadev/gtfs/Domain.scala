package com.github.leosilvadev.gtfs

import java.time.LocalDate

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException

class Domain {}

case class GtfsAgency(
    id: Long,
    name: String,
    url: String,
    timezone: String,
    lang: Option[String],
    phone: Option[String]
)

case class GtfsCalendar(
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

sealed trait GtfsCalendarDateException

object GtfsCalendarDateException {

  def from(value: String, field: String): Either[InvalidFieldValueException, GtfsCalendarDateException] =
    try {
      if (value == "1") Right(AddDate)
      else if (value == "2") Right(RemoveDate)
      else Left(new InvalidFieldValueException(field, value))
    } catch {
      case _: Throwable => Left(new InvalidFieldValueException(field, value))
    }
}
object AddDate extends GtfsCalendarDateException
object RemoveDate extends GtfsCalendarDateException

case class GtfsCalendarDate(serviceId: Long, date: LocalDate, exceptionType: GtfsCalendarDateException)

case class GtfsRoute(
    id: String,
    agencyId: Long,
    shortName: Option[String],
    longName: Option[String],
    `type`: Int,
    color: Option[String],
    textColor: Option[String],
    description: Option[String]
)
