package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException
import com.github.leosilvadev.gtfs.domain.GtfsRoute

case class GtfsRouteFile(globalAgencyId: Option[String] = None) extends GtfsFile[GtfsRoute] {

  override val requiredColumns: Map[String, Int] = Map(
    "route_id" -> 0,
    "agency_id" -> 1,
    "route_type" -> 2
  )

  override val optionalColumns: Map[String, Int] = Map(
    "route_short_name" -> 3,
    "route_long_name" -> 4,
    "route_desc" -> 5,
    "route_url" -> 6,
    "route_color" -> 7,
    "route_text_color" -> 8,
    "route_sort_order" -> 9,
    "continuous_pickup" -> 10,
    "continuous_drop_off" -> 11
  )

  override def parse(
      cols: Array[String]
  ): Either[InvalidFieldValueException, GtfsRoute] =
    for {
      id <- toString(cols(0), "route_id")
      agencyId <- toString(cols(1), "agency_id", globalAgencyId)
      routeType <- toInt(cols(2), "route_type")
      shortName = toOptionalString(cols(3))
      longName = toOptionalString(cols(4))
      description = toOptionalString(cols(5))
      url = toOptionalString(cols(6))
      color = toOptionalString(cols(7))
      textColor = toOptionalString(cols(8))
      sortOrder = toOptionalInt(cols(9))
      continuousPickup = toOptionalString(cols(10))
      continuousDropOff = toOptionalString(cols(10))
    } yield GtfsRoute(
      id = id,
      agencyId = agencyId,
      shortName = shortName,
      longName = longName,
      description = description,
      `type` = routeType,
      url = url,
      color = color,
      textColor = textColor,
      sortOrder = sortOrder,
      continuousPickup = continuousPickup,
      continuousDropOff = continuousDropOff
    )

}
