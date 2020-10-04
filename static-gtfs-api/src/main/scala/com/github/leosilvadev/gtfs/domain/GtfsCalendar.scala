package com.github.leosilvadev.gtfs.domain

import java.time.LocalDate

/**
  * Service dates specified using a weekly schedule with start and end dates. This file is required unless all dates of service are defined in calendar_dates.txt.
  *
  * @param serviceId Uniquely identifies a set of dates when service is available for one or more routes.
  *                  Each service_id value can appear at most once in a calendar.txt file.
  *
  * @param monday    Indicates whether the service operates on all Mondays in the date range specified by the start_date and end_date fields.
  *                  Note that exceptions for particular dates may be listed in calendar_dates.txt.
  *
  * @param tuesday   Indicates whether the service operates on all Tuesday in the date range specified by the start_date and end_date fields.
  *                  Note that exceptions for particular dates may be listed in calendar_dates.txt.
  *
  * @param wednesday Indicates whether the service operates on all Wednesday in the date range specified by the start_date and end_date fields.
  *                  Note that exceptions for particular dates may be listed in calendar_dates.txt.
  *
  * @param thursday  Indicates whether the service operates on all Thursday in the date range specified by the start_date and end_date fields.
  *                  Note that exceptions for particular dates may be listed in calendar_dates.txt.
  *
  * @param friday    Indicates whether the service operates on all Friday in the date range specified by the start_date and end_date fields.
  *                  Note that exceptions for particular dates may be listed in calendar_dates.txt.
  *
  * @param saturday  Indicates whether the service operates on all Saturday in the date range specified by the start_date and end_date fields.
  *                  Note that exceptions for particular dates may be listed in calendar_dates.txt.
  *
  * @param sunday    Indicates whether the service operates on all Sunday in the date range specified by the start_date and end_date fields.
  *                  Note that exceptions for particular dates may be listed in calendar_dates.txt.
  *
  * @param startDate Start service day for the service interval.
  *
  * @param endDate   End service day for the service interval. This service day is included in the interval.
  * */
case class GtfsCalendar(
    serviceId: String,
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
