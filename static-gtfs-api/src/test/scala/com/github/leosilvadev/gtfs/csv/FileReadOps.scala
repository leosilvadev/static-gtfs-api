package com.github.leosilvadev.gtfs.csv

import java.nio.file.Paths

object FileReadOps {

  implicit def pathOps(filePath: String) =
    Paths.get(GtfsCalendarFile.getClass.getClassLoader.getResource(filePath).toURI)
}
