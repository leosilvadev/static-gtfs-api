package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException
import com.github.leosilvadev.gtfs.domain.GtfsShape

object GtfsShapeFile extends GtfsFile[GtfsShape] {

  override val requiredColumns: Map[String, Int] = Map(
    "shape_id" -> 0,
    "shape_pt_lat" -> 1,
    "shape_pt_lon" -> 2,
    "shape_pt_sequence" -> 3
  )

  override val optionalColumns: Map[String, Int] = Map(
    "shape_dist_traveled" -> 4
  )

  override def parse(
      cols: Array[String]
  ): Either[InvalidFieldValueException, GtfsShape] = {
    for {
      id <- toString(cols(0), "shape_id")
      latitude <- toDouble(cols(1), "shape_pt_lat")
      longitude <- toDouble(cols(2), "shape_pt_lon")
      sequence <- toInt(cols(3), "shape_pt_sequence")
      distanceTraveled = toOptionalDouble(cols(4))
    } yield GtfsShape(
      id = id,
      latitude = latitude,
      longitude = longitude,
      sequence = sequence,
      distanceTraveled = distanceTraveled
    )
  }
}
