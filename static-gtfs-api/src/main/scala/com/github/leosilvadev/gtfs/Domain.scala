package com.github.leosilvadev.gtfs

case class GtfsRoute(
    id: String,
    agencyId: Long,
    shortName: Option[String],
    longName: Option[String],
    `type`: Int,
    color: Option[String],
    textColor: Option[String],
    description: Option[String]
)

case class GtfsStop(
    id: String,
    code: Option[String],
    name: Option[String],
    description: Option[String],
    latitude: Option[Double],
    longitude: Option[Double],
    zoneId: Option[String],
    url: Option[String],
    locationType: Option[Int], //TODO: Maybe use an enum or sealed trait later
    parentStation: Option[String],
    timezone: Option[String],
    wheelchairBoarding: Option[Int],
    levelId: Option[Long],
    platformCode: Option[String]
)
