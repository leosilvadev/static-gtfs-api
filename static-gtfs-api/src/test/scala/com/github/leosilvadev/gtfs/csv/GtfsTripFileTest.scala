package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.csv.FileReadOps._
import com.github.leosilvadev.gtfs.csv.exceptions.MissingFieldsException
import com.github.leosilvadev.gtfs.domain.GtfsTrip
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsTripFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Calendar file read") {
    describe("Field validation") {
      it("must read a file which contains all the required fields") {
        GtfsTripFile.read("gtfs/complete_trips.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(trips) =>
            trips must have size 6
        }
      }

      it("must fail trying to read a file if any required field is missing") {
        GtfsTripFile.read("gtfs/incomplete_trips.txt") match {
          case Right(_) => fail("Parse should have failed by missing fields")
          case Left(ex) =>
            ex.asInstanceOf[MissingFieldsException].fields mustBe List("service_id")
        }
      }

      it("must fail trying to read a file if any required field is invalid") {
        GtfsTripFile.read("gtfs/invalid_trips.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(trips) =>
            trips must have size 6
            trips.head match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "route_id"
            }
            trips(1).isRight mustBe true
            trips(2).isRight mustBe true
            trips(3) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "service_id"
            }
            trips(4).isRight mustBe true
            trips(5) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "trip_id"
            }
        }
      }
    }

    describe("Value assignment correctness") {
      it("must assign all the values properly") {
        GtfsTripFile.read("gtfs/complete_trips.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(calendars) =>
            calendars.head mustBe Right(
              GtfsTrip(
                routeId = "18804_3",
                serviceId = "1",
                id = "139537339",
                headsign = Some("Seegefeld, Bahnhof"),
                shortName = None,
                directionId = Some(0),
                blockId = None,
                shapeId = Some("512"),
                wheelchairAccessible = None,
                bikesAllowed = None
              )
            )
        }
      }
    }
  }

}
