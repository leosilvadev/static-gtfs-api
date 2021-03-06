package com.github.leosilvadev.gtfs.domain

/**
  * Transit routes. A route is a group of trips that are displayed to riders as a single service.
  *
  * @param id Identifies a route.
  *
  * @param agencyId          Agency for the specified route. This field is required when the dataset provides data for routes from more than one agency in agency.txt, otherwise it is optional.
  *
  * @param shortName         Short name of a route. This will often be a short, abstract identifier like "32", "100X", or "Green" that riders use to identify a route, but which doesn't give any indication of what places the route serves.
  *                          Either route_short_name or route_long_name must be specified, or potentially both if appropriate.
  *
  * @param longName          Full name of a route. This name is generally more descriptive than the route_short_name and often includes the route's destination or stop.
  *                          Either route_short_name or route_long_name must be specified, or potentially both if appropriate.
  *
  * @param description       Description of a route that provides useful, quality information. Do not simply duplicate the name of the route.
  *                          Example: "A" trains operate between Inwood-207 St, Manhattan and Far Rockaway-Mott Avenue, Queens at all times.
  *                          Also from about 6AM until about midnight, additional "A" trains operate between Inwood-207 St and Lefferts Boulevard (trains typically alternate between Lefferts Blvd and Far Rockaway).
  *
  * @param `type`            Indicates the type of transportation used on a route.
  *                          Valid options are:
  *                          0 - Tram, Streetcar, Light rail. Any light rail or street level system within a metropolitan area.
  *                          1 - Subway, Metro. Any underground rail system within a metropolitan area.
  *                          2 - Rail. Used for intercity or long-distance travel.
  *                          3 - Bus. Used for short- and long-distance bus routes.
  *                          4 - Ferry. Used for short- and long-distance boat service.
  *                          5 - Cable tram. Used for street-level rail cars where the cable runs beneath the vehicle, e.g., cable car in San Francisco.
  *                          6 - Aerial lift, suspended cable car (e.g., gondola lift, aerial tramway). Cable transport where cabins, cars, gondolas or open chairs are suspended by means of one or more cables.
  *                          7 - Funicular. Any rail system designed for steep inclines.
  *                          11 - Trolleybus. Electric buses that draw power from overhead wires using poles.
  *                          12 - Monorail. Railway in which the track consists of a single rail or a beam.
  *                          There are also extended types documented at https://developers.google.com/transit/gtfs/reference/extended-route-types
  *
  * @param url               URL of a web page about the particular route. Should be different from the agency.agency_url value.
  *
  * @param color             Route color designation that matches public facing material. Defaults to white (FFFFFF) when omitted or left empty.
  *                          The color difference between route_color and route_text_color should provide sufficient contrast when viewed on a black and white screen.
  *
  * @param textColor         Legible color to use for text drawn against a background of route_color. Defaults to black (000000) when omitted or left empty.
  *                          The color difference between route_color and route_text_color should provide sufficient contrast when viewed on a black and white screen.
  *
  * @param sortOrder         Orders the routes in a way which is ideal for presentation to customers. Routes with smaller route_sort_order values should be displayed first.
  *
  * @param continuousPickup  Indicates whether a rider can board the transit vehicle anywhere along the vehicle’s travel path.
  *                          The path is described by shapes.txt on every trip of the route.
  *                          Valid options are:
  *                          0 - Continuous stopping pickup.
  *                          1 or empty - No continuous stopping pickup.
  *                          2 - Must phone an agency to arrange continuous stopping pickup.
  *                          3 - Must coordinate with a driver to arrange continuous stopping pickup.
  *                          The default continuous pickup behavior defined in routes.txt can be overridden in stop_times.txt.
  *
  * @param continuousDropOff Indicates whether a rider can alight from the transit vehicle at any point along the vehicle’s travel path. The path is described by shapes.txt on every trip of the route. Valid options are:
  *                          0- Continuous stopping drop-off.
  *                          1 or empty - No continuous stopping drop-off.
  *                          2 - Must phone an agency to arrange continuous stopping drop-off.
  *                          3 - Must coordinate with a driver to arrange continuous stopping drop-off.
  *                          The default continuous drop-off behavior defined in routes.txt can be overridden in stop_times.txt.
  * */
case class GtfsRoute(
    id: String,
    agencyId: String,
    shortName: Option[String],
    longName: Option[String],
    description: Option[String],
    `type`: Int,
    url: Option[String],
    color: Option[String],
    textColor: Option[String],
    sortOrder: Option[Int],
    continuousPickup: Option[String],
    continuousDropOff: Option[String]
)
