package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.GtfsStop
import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException

object GtfsStopFile extends GtfsFile[GtfsStop] {

  override val requiredColumns: Map[String, Int] = Map(
    "stop_id" -> 0
  )

  override val optionalColumns: Map[String, Int] = Map(
    "stop_code" -> 1,
    "stop_name" -> 2,
    "stop_desc" -> 3,
    "stop_lat" -> 4,
    "stop_lon" -> 5,
    "zone_id" -> 6,
    "stop_url" -> 7,
    "location_type" -> 8,
    "parent_station" -> 9,
    "stop_timezone" -> 10,
    "wheelchair_boarding" -> 11,
    "level_id" -> 12,
    "platform_code" -> 13
  )

  override def parse(
      cols: Array[String]
  ): Either[InvalidFieldValueException, GtfsStop] =
    for {
      id <- toString(cols(0), "stop_id")
      code = toOptionalString(cols(1))
      name = toOptionalString(cols(2))
      description = toOptionalString(cols(3))
      latitude = toOptionalDouble(cols(4))
      longitude = toOptionalDouble(cols(5))
      zoneId = toOptionalString(cols(6))
      url = toOptionalString(cols(7))
      locationType = toOptionalInt(cols(8))
      parentStation = toOptionalString(cols(9))
      timezone = toOptionalString(cols(10))
      wheelchairBoarding = toOptionalInt(cols(11))
      levelId = toOptionalLong(cols(12))
      platformCode = toOptionalString(cols(13))

    } yield GtfsStop(
      id = id,
      code = code,
      name = name,
      description = description,
      latitude = latitude,
      longitude = longitude,
      zoneId = zoneId,
      url = url,
      locationType = locationType,
      parentStation = parentStation,
      timezone = timezone,
      wheelchairBoarding = wheelchairBoarding,
      levelId = levelId,
      platformCode = platformCode
    )

}
