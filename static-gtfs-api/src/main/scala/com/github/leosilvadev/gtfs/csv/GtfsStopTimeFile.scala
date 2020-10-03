package com.github.leosilvadev.gtfs.csv

import java.time.LocalTime

import com.github.leosilvadev.gtfs.csv.exceptions.InvalidFieldValueException
import com.github.leosilvadev.gtfs.domain.GtfsStopTime

object GtfsStopTimeFile extends GtfsFile[GtfsStopTime] {

  override val requiredColumns: Map[String, Int] = Map(
    "trip_id" -> 0,
    "stop_id" -> 1,
    "stop_sequence" -> 2
  )

  override val optionalColumns: Map[String, Int] = Map(
    "arrival_time" -> 3,
    "departure_time" -> 4,
    "stop_headsign" -> 5,
    "pickup_type" -> 6,
    "drop_off_type" -> 7,
    "continuous_pickup" -> 8,
    "continuous_drop_off" -> 9,
    "shape_dist_traveled" -> 10,
    "timepoint" -> 11
  )

  override def parse(
      cols: Array[String]
  ): Either[InvalidFieldValueException, GtfsStopTime] =
    for {
      tripId <- toString(cols(0), "trip_id")
      stopId <- toString(cols(1), "stop_id")
      stopSequence <- toInt(cols(2), "stop_sequence")
      arrivalTime = toOptionalLocalTime(cols(3))
      departureTime = toOptionalLocalTime(cols(4))
      stopHeadsign = toOptionalString(cols(5))
      pickupType = toOptionalInt(cols(6))
      dropOffType = toOptionalInt(cols(7))
      continuousPickup = toOptionalInt(cols(8))
      continuousDropOff = toOptionalInt(cols(9))
      shapeDistTraveled = toOptionalDouble(cols(10))
      timepoint = toOptionalInt(cols(11))
      _ <- validateArrivalAndDeparture(arrivalTime, departureTime)
    } yield GtfsStopTime(
      tripId = tripId,
      stopId = stopId,
      stopSequence = stopSequence,
      arrivalTime = arrivalTime,
      departureTime = departureTime,
      stopHeadsign = stopHeadsign,
      pickupType = pickupType,
      dropOffType = dropOffType,
      continuousPickup = continuousPickup,
      continuousDropOff = continuousDropOff,
      shapeDistTraveled = shapeDistTraveled,
      timepoint = timepoint
    )

  private def validateArrivalAndDeparture(
      arrivalTime: Option[LocalTime],
      departureTime: Option[LocalTime]
  ): Either[InvalidFieldValueException, Unit] =
    if (arrivalTime.isEmpty && departureTime.isEmpty)
      Left(new InvalidFieldValueException("arrival_time and departure_time", null))
    else Right(())

}
