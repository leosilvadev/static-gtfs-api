package com.github.leosilvadev.gtfs.csv

import java.time.LocalDate

import FileReadOps._
import com.github.leosilvadev.gtfs.domain.GtfsCalendar
import com.github.leosilvadev.gtfs.csv.exceptions.MissingFieldsException
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsCalendarFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Calendar file read") {
    describe("Field validation") {
      it("must read a file which contains all the required fields") {
        GtfsCalendarFile.read("gtfs/complete_calendar.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(calendars) =>
            calendars must have size 3
        }
      }

      it("must fail trying to read a file if any required field is missing") {
        GtfsCalendarFile.read("gtfs/incomplete_calendar.txt") match {
          case Right(_) => fail("Parse should have failed by missing fields")
          case Left(ex) =>
            ex.asInstanceOf[MissingFieldsException].fields mustBe List("friday")
        }
      }

      it("must fail trying to read a file if any required field is invalid") {
        GtfsCalendarFile.read("gtfs/invalid_calendar.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(calendars) =>
            calendars must have size 3
            calendars.head match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "tuesday"
            }
            calendars(1).isRight mustBe true
            calendars(2) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "start_date"
            }

        }
      }
    }

    describe("Value assignment correctness") {
      it("must assign all the values properly") {
        GtfsCalendarFile.read("gtfs/complete_calendar.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(calendars) =>
            calendars.head mustBe Right(
              GtfsCalendar(
                "1",
                monday = false,
                tuesday = false,
                wednesday = false,
                thursday = false,
                friday = false,
                saturday = true,
                sunday = true,
                LocalDate.of(2020, 9, 24),
                LocalDate.of(2020, 12, 12)
              )
            )
        }
      }
    }
  }

}
