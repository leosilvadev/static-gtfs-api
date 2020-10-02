package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.domain.GtfsTrip
import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException

object GtfsTripFile extends GtfsFile[GtfsTrip] {

  override val requiredColumns: Map[String, Int] = Map(
    "route_id" -> 0,
    "service_id" -> 1,
    "trip_id" -> 2
  )

  override val optionalColumns: Map[String, Int] = Map(
    "trip_headsign" -> 3,
    "trip_short_name" -> 4,
    "direction_id" -> 5,
    "block_id" -> 6,
    "shape_id" -> 7,
    "wheelchair_accessible" -> 8,
    "bikes_allowed" -> 9
  )

  override def parse(
      cols: Array[String]
  ): Either[InvalidFieldValueException, GtfsTrip] =
    for {
      routeId <- toString(cols(0), "route_id")
      serviceId <- toString(cols(1), "service_id")
      id <- toString(cols(2), "trip_id")
      headsign = toOptionalString(cols(3))
      shortName = toOptionalString(cols(4))
      directionId = toOptionalInt(cols(5))
      blockId = toOptionalString(cols(6))
      shapeId = toOptionalString(cols(7))
      wheelchairAccessible = toOptionalInt(cols(8))
      bikesAllowed = toOptionalInt(cols(9))
    } yield GtfsTrip(
      routeId = routeId,
      serviceId = serviceId,
      id = id,
      headsign = headsign,
      shortName = shortName,
      directionId = directionId,
      blockId = blockId,
      shapeId = shapeId,
      wheelchairAccessible = wheelchairAccessible,
      bikesAllowed = bikesAllowed
    )

}
