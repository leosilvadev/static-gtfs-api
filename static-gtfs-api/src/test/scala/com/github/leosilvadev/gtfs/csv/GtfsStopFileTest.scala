package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.domain.GtfsStop
import com.github.leosilvadev.gtfs.csv.FileReadOps._
import com.github.leosilvadev.gtfs.csv.exceptions.MissingFieldsException
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsStopFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Agency file read") {
    describe("Field validation") {
      it("must read a file which contains all the required fields") {
        GtfsStopFile.read("gtfs/complete_stops.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(stops) =>
            stops must have size 8
        }
      }

      it("must fail trying to read a file if any required field is missing") {
        GtfsStopFile.read("gtfs/incomplete_stops.txt") match {
          case Right(_) => fail("Parse should have failed by missing fields")
          case Left(ex) =>
            ex.asInstanceOf[MissingFieldsException].fields mustBe List("stop_id")
        }
      }

      it("must fail trying to read a file if any required field is invalid") {
        GtfsStopFile.read("gtfs/invalid_stops.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(stops) =>
            stops must have size 8
            stops.head.isRight mustBe true
            stops(1).isRight mustBe true
            stops(2).isRight mustBe true
            stops(3).isRight mustBe true
            stops(4).isRight mustBe true
            stops(5) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "stop_id"
            }
            stops(6).isRight mustBe true
            stops(7).isRight mustBe true
        }
      }
    }

    describe("Value assignment correctness") {
      it("must assign all the values properly") {
        GtfsStopFile.read("gtfs/complete_stops.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(stops) =>
            stops.head mustBe Right(
              GtfsStop(
                id = "000008012713",
                code = None,
                name = Some("Rangsdorf, Bahnhof"),
                description = None,
                latitude = Some(52.294125000000),
                longitude = Some(13.431112000000),
                zoneId = None,
                url = None,
                locationType = Some(0),
                parentStation = Some("900000245025"),
                timezone = None,
                wheelchairBoarding = None,
                levelId = None,
                platformCode = None
              )
            )
        }
      }
    }
  }

}
