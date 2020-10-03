package com.github.leosilvadev.gtfs.domain

import java.time.LocalTime

/**
  * Times that a vehicle arrives at and departs from stops for each trip.
  *
  * @param tripId            Identifies a trip.
  *
  * @param arrivalTime       Arrival time at a specific stop for a specific trip on a route.
  *                          If there are not separate times for arrival and departure at a stop, enter the same value for arrival_time and departure_time.
  *                          For times occurring after midnight on the service day, enter the time as a value greater than 24:00:00 in HH:MM:SS local time for the day on which the trip schedule begins.
  *
  * @param departureTime     Departure time from a specific stop for a specific trip on a route.
  *                          For times occurring after midnight on the service day, enter the time as a value greater than 24:00:00 in HH:MM:SS local time for the day on which the trip schedule begins.
  *                          If there are not separate times for arrival and departure at a stop, enter the same value for arrival_time and departure_time.
  *                          See the arrival_time description for more details about using timepoints correctly.
  *
  * @param stopId            Identifies the serviced stop. All stops serviced during a trip must have a record in stop_times.txt.
  *                          Referenced locations must be stops, not stations or station entrances.
  *                          A stop may be serviced multiple times in the same trip, and multiple trips and routes may service the same stop.
  *
  * @param stopSequence      Order of stops for a particular trip. The values must increase along the trip but do not need to be consecutive.
  *
  * @param stopHeadsign      Text that appears on signage identifying the trip's destination to riders.
  *                          This field overrides the default trips.trip_headsign when the headsign changes between stops.
  *                          If the headsign is displayed for an entire trip, use trips.trip_headsign instead.
  *                          A stop_headsign value specified for one stop_time does not apply to subsequent stop_times in the same trip.
  *                          If you want to override the trip_headsign for multiple stop_times in the same trip, the stop_headsign value must be repeated in each stop_time row.
  *
  * @param pickupType        Indicates pickup method.
  *                          Valid options are:
  *                          0 or empty - Regularly scheduled pickup.
  *                          1 - No pickup available.
  *                          2 - Must phone agency to arrange pickup.
  *                          3 - Must coordinate with driver to arrange pickup.
  *
  * @param dropOffType       Indicates drop off method.
  *                          Valid options are:
  *                          0 or empty - Regularly scheduled drop off.
  *                          1 - No drop off available.
  *                          2 - Must phone agency to arrange drop off.
  *                          3 - Must coordinate with driver to arrange drop off.
  *
  * @param continuousPickup  Indicates whether a rider can board the transit vehicle at any point along the vehicle’s travel path. The path is described by shapes.txt, from this stop_time to the next stop_time in the trip’s stop_sequence.
  *                          Valid options are:
  *                          0 - Continuous stopping pickup.
  *                          1 or empty - No continuous stopping pickup.
  *                          2 - Must phone an agency to arrange continuous pickup.
  *                          3 - Must coordinate with a driver to arrange continuous stopping pickup.
  *                          The continuous pickup behavior indicated in stop_times.txt overrides any behavior defined in routes.txt.
  *
  * @param continuousDropOff Indicates whether a rider can alight from the transit vehicle at any point along the vehicle’s travel path as described by shapes.txt, from this stop_time to the next stop_time in the trip’s stop_sequence.
  *                          Valid options are:
  *                          0 - Continuous stopping drop off.
  *                          1 or empty - No continuous stopping drop off.
  *                          2 - Must phone an agency to arrange continuous drop off.
  *                          3 - Must coordinate with a driver to arrange continuous stopping drop off.
  *                          The continuous drop-off behavior indicated in stop_times.txt overrides any behavior defined in routes.txt.
  *
  * @param shapeDistTraveled Actual distance traveled along the associated shape, from the first stop to the stop specified in this record.
  *                          This field specifies how much of the shape to draw between any two stops during a trip.
  *                          Must be in the same units used in shapes.txt.
  *                          Values used for shape_dist_traveled must increase along with stop_sequence; they cannot be used to show reverse travel along a route.
  *
  * @param timepoint         Indicates if arrival and departure times for a stop are strictly adhered to by the vehicle or if they are instead approximate and/or interpolated times.
  *                          This field allows a GTFS producer to provide interpolated stop-times, while indicating that the times are approximate.
  *                          Valid options are:
  *                          0 - Times are considered approximate.
  *                          1 or empty - Times are considered exact.
  * */
case class GtfsStopTime(
    tripId: String,
    arrivalTime: Option[LocalTime],
    departureTime: Option[LocalTime],
    stopId: String,
    stopSequence: Int,
    stopHeadsign: Option[String],
    pickupType: Option[Int],
    dropOffType: Option[Int],
    continuousPickup: Option[Int],
    continuousDropOff: Option[Int],
    shapeDistTraveled: Option[Double],
    timepoint: Option[Int]
)
