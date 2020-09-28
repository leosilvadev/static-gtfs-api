package com.github.leosilvadev.gtfs

import java.time.LocalDate

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException

class Domain {}

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

case class GtfsStop(
    id: String,
    code: Option[String],
    name: Option[String],
    description: Option[String],
    latitude: Option[Double],
    longitude: Option[Double],
    zoneId: Option[String],
    url: Option[String],
    locationType: Option[Int], //TODO: Maybe use an enum or sealed trait later
    parentStation: Option[String],
    timezone: Option[String],
    wheelchairBoarding: Option[Int],
    levelId: Option[Long],
    platformCode: Option[String]
)
