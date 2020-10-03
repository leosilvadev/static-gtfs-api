package com.github.leosilvadev.gtfs.csv

import java.time.LocalTime

import com.github.leosilvadev.gtfs.domain.GtfsStopTime
import com.github.leosilvadev.gtfs.csv.FileReadOps._
import com.github.leosilvadev.gtfs.csv.exceptions.MissingFieldsException
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsStopTimeFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Agency file read") {
    describe("Field validation") {
      it("must read a file which contains all the required fields") {
        GtfsStopTimeFile.read("gtfs/complete_stop_times.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(times) =>
            times must have size 26
        }
      }

      it("must fail trying to read a file if any required field is missing") {
        GtfsStopTimeFile.read("gtfs/incomplete_stop_times.txt") match {
          case Right(_) => fail("Parse should have failed by missing fields")
          case Left(ex) =>
            ex.asInstanceOf[MissingFieldsException].fields mustBe List("stop_id")
        }
      }

      it("must fail trying to read a file if any required field is invalid") {
        GtfsStopTimeFile.read("gtfs/invalid_stop_times.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(times) =>
            times must have size 13
            times.head.isRight mustBe true
            times(1) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "trip_id"
            }
            times(2).isRight mustBe true
            times(3) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "stop_id"
            }
            times(4) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "stop_sequence"
            }
            times(5).isRight mustBe true
            times(6).isRight mustBe true
            times(7).isRight mustBe true
            times(8).isRight mustBe true
            times(9).isRight mustBe true
            times(10).isRight mustBe true
            times(11) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "arrival_time and departure_time"
            }
            times(12).isRight mustBe true
        }
      }
    }

    describe("Value assignment correctness") {
      it("must assign all the values properly") {
        GtfsStopTimeFile.read("gtfs/complete_stop_times.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(stops) =>
            //139537339,5:05:00,5:05:00,100000710003,0,0,0,""
            stops.head mustBe Right(
              GtfsStopTime(
                tripId = "139537339",
                arrivalTime = Some(LocalTime.of(5, 5, 0)),
                departureTime = Some(LocalTime.of(5, 5, 0)),
                stopId = "100000710003",
                stopSequence = 0,
                stopHeadsign = None,
                pickupType = Some(0),
                dropOffType = Some(0),
                continuousPickup = None,
                continuousDropOff = None,
                shapeDistTraveled = None,
                timepoint = None
              )
            )
        }
      }
    }
  }

}
