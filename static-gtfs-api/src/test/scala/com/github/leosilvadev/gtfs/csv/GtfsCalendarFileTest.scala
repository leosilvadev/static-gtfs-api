package com.github.leosilvadev.gtfs.csv

import java.nio.file.Paths

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsCalendarFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Calendar file read") {
    it("must read a file which contains all the required fields") {

      val calendars = GtfsCalendarFile.read(Paths.get(GtfsCalendarFile.getClass.getClassLoader.getResource("gtfs/complete_calendar.txt").toURI))
      calendars must have size 3
    }
  }

}
