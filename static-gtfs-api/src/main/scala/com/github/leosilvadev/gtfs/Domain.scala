package com.github.leosilvadev.gtfs

import java.time.LocalDate

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
  def apply(value: String): CalendarDateException = this(value.toInt)

  def apply(value: Int): CalendarDateException =
    if (value == 1) AddDate
    else if (value == 2) RemoveDate
    else throw new IllegalArgumentException(s"Invalid calendar date exception: $value")
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
