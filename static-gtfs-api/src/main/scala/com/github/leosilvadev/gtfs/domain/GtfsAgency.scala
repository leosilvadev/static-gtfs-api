package com.github.leosilvadev.gtfs.domain

/**
  * Transit agencies with service represented in this dataset.
  *
  * @param id Identifies a transit brandwhich is often synonymous with a transit agency. Note that in some cases, such as when a single agency operates multiple separate services, agencies and brands are distinct. This document uses the term "agency" in place of "brand". A dataset may contain data from multiple agencies. This field is required when the dataset contains data for multiple transit agencies, otherwise it is optional.
  * @param name Full name of the transit agency.
  * @param url URL of the transit agency.
  * @param timezone Timezone where the transit agency is located. If multiple agencies are specified in the dataset, each must have the same agency_timezone.
  * @param lang Primary language used by this transit agency. This field helps GTFS consumers choose capitalization rules and other language-specific settings for the dataset.
  * @param phone A voice telephone number for the specified agency. This field is a string value that presents the telephone number as typical for the agency's service area. It can and should contain punctuation marks to group the digits of the number. Dialable text (for example, TriMet's "503-238-RIDE") is permitted, but the field must not contain any other descriptive text.
  * @param fareUrl URL of a web page that allows a rider to purchase tickets or other fare instruments for that agency online.
  * @param email Email address actively monitored by the agency’s customer service department. This email address should be a direct contact point where transit riders can reach a customer service representative at the agency.
  * */
case class GtfsAgency(
    id: String,
    name: String,
    url: String,
    timezone: String,
    lang: Option[String],
    phone: Option[String],
    fareUrl: Option[String],
    email: Option[String]
)
