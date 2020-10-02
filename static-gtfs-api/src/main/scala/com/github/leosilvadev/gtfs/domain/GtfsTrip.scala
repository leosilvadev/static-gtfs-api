package com.github.leosilvadev.gtfs.domain

/**
  * Trips for each route. A trip is a sequence of two or more stops that occur during a specific time period.
  *
  * @param routeId              Identifies a route.
  *
  * @param serviceId            Identifies a set of dates when service is available for one or more routes.
  *
  * @param id                   Identifies a trip.
  *
  * @param headsign             Text that appears on signage identifying the trip's destination to riders.
  *                             Use this field to distinguish between different patterns of service on the same route.
  *                             If the headsign changes during a trip, trip_headsign can be overridden by specifying values for the stop_times.stop_headsign.
  *
  * @param shortName            Public facing text used to identify the trip to riders, for instance, to identify train numbers for commuter rail trips.
  *                             If riders do not commonly rely on trip names, leave this field empty.
  *                             A trip_short_name value, if provided, should uniquely identify a trip within a service day; it should not be used for destination names or limited/express designations.
  *
  * @param directionId          Indicates the direction of travel for a trip.
  *                             This field is not used in routing; it provides a way to separate trips by direction when publishing time tables.
  *                             Valid options are:
  *                             0 - Travel in one direction (e.g. outbound travel).
  *                             1 - Travel in the opposite direction (e.g. inbound travel).
  *
  * @param blockId              Identifies the block to which the trip belongs.
  *                             A block consists of a single trip or many sequential trips made using the same vehicle, defined by shared service days and block_id.
  *                             A block_id can have trips with different service days, making distinct blocks.
  *
  * @param shapeId              Identifies a geospatial shape that describes the vehicle travel path for a trip.
  *
  * @param wheelchairAccessible Indicates wheelchair accessibility.
  *                             Valid options are:
  *                             0 or empty - No accessibility information for the trip.
  *                             1 - Vehicle being used on this particular trip can accommodate at least one rider in a wheelchair.
  *                             2 - No riders in wheelchairs can be accommodated on this trip.
  *
  * @param bikesAllowed         Indicates whether bikes are allowed.
  *                             Valid options are:
  *                             0 or empty - No bike information for the trip.
  *                             1 - Vehicle being used on this particular trip can accommodate at least one bicycle.
  *                             2 - No bicycles are allowed on this trip.
  * */
case class GtfsTrip(
    routeId: String,
    serviceId: String,
    id: String,
    headsign: Option[String],
    shortName: Option[String],
    directionId: Option[Int],
    blockId: Option[String],
    shapeId: Option[String],
    wheelchairAccessible: Option[Int],
    bikesAllowed: Option[Int]
)
