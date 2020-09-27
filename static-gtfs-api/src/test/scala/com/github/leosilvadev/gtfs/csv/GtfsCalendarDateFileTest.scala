package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.csv.FileReadOps._
import com.github.leosilvadev.gtfs.csv.exceptions.MissingFieldsException
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsCalendarDateFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Calendar file read") {
    it("must read a file which contains all the required fields") {
      GtfsCalendarDateFile.read("gtfs/complete_calendar_dates.txt") match {
        case Left(ex) => fail("Failed because of unexpected error", ex)
        case Right(calendars) =>
          calendars must have size 3
      }
    }

    it("must fail trying to read a file if any required field is missing") {
      GtfsCalendarDateFile.read("gtfs/incomplete_calendar_dates.txt") match {
        case Right(_) => fail("Parse should have failed by missing fields")
        case Left(ex) =>
          ex.asInstanceOf[MissingFieldsException].fields mustBe List("exception_type")
      }
    }

    it("must fail trying to read a file if any required field is invalid") {
      GtfsCalendarDateFile.read("gtfs/invalid_calendar_dates.txt") match {
        case Left(ex) => fail("Failed because of unexpected error", ex)
        case Right(calendars) =>
          calendars must have size 3
          calendars.head.isRight mustBe true
          calendars(1).isRight mustBe true
          calendars(2) match {
            case Right(_) => fail("Parse should have failed by invalid field")
            case Left(ex) =>
              ex.field mustBe "date"
          }

      }
    }
  }

}
