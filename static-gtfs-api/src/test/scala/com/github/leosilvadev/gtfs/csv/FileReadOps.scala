package com.github.leosilvadev.gtfs.csv

import java.nio.file.{Path, Paths}

object FileReadOps {

  implicit def pathOps(filePath: String): Path =
    Paths.get(GtfsCalendarFile.getClass.getClassLoader.getResource(filePath).toURI)
}
