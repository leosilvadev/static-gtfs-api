package com.github.leosilvadev.gtfs.domain

import java.time.LocalDate

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException

/**
  * Exceptions for the services defined in the calendar.txt. If calendar.txt is omitted, then calendar_dates.txt is required and must contain all dates of service.
  * A Gtfs calendar date can be either an GtfsAddedCalendarDate or GtfsExcludedCalendarDate, so it is an exception to add or remove a date of service
  *
  * The serviceId Identifies a set of dates when a service exception occurs for one or more routes.
  *               Each (service_id, date) pair can only appear once in calendar_dates.txt if using calendar.txt and calendar_dates.txt in conjunction.
  *               If a service_id value appears in both calendar.txt and calendar_dates.txt, the information in calendar_dates.txt modifies the service information specified in calendar.txt.
  *
  * The date is when service exception occurs.
  * */
sealed trait GtfsCalendarDate {
  val serviceId: String
  val date: LocalDate
}
case class GtfsAddedCalendarDate(serviceId: String, date: LocalDate) extends GtfsCalendarDate
case class GtfsExcludedCalendarDate(serviceId: String, date: LocalDate) extends GtfsCalendarDate

object GtfsCalendarDate {

  def apply(
      serviceId: String,
      date: LocalDate,
      exceptionType: String,
      exceptionTypeField: String
  ): Either[InvalidFieldValueException, GtfsCalendarDate] =
    if (exceptionType == "1") Right(GtfsAddedCalendarDate(serviceId, date))
    else if (exceptionType == "2") Right(GtfsExcludedCalendarDate(serviceId, date))
    else Left(new InvalidFieldValueException(exceptionTypeField, exceptionType))
}
