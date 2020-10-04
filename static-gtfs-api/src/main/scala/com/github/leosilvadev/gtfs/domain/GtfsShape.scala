package com.github.leosilvadev.gtfs.domain

/**
  * Rules for mapping vehicle travel paths, sometimes referred to as route alignments.
  *
  * @param id               Identifies a shape.
  *
  * @param latitude         Latitude of a shape point. Each record in shapes.txt represents a shape point used to define the shape.
  *
  * @param longitude        Longitude of a shape point.
  *
  * @param sequence         Sequence in which the shape points connect to form the shape.
  *                         Values must increase along the trip but do not need to be consecutive.
  *
  * @param distanceTraveled Actual distance traveled along the shape from the first shape point to the point specified in this record.
  *                         Used by trip planners to show the correct portion of the shape on a map.
  *                         Values must increase along with shape_pt_sequence; they cannot be used to show reverse travel along a route.
  *                         Distance units must be consistent with those used in stop_times.txt.
  * */
case class GtfsShape(
    id: String,
    latitude: Double,
    longitude: Double,
    sequence: Int,
    distanceTraveled: Option[Int]
)
