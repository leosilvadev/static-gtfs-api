package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.GtfsAgency
import com.github.leosilvadev.gtfs.csv.FileReadOps._
import com.github.leosilvadev.gtfs.csv.exceptions.MissingFieldsException
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsAgencyFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Agency file read") {
    describe("Field validation") {
      it("must read a file which contains all the required fields") {
        GtfsAgencyFile(None).read("gtfs/complete_agency.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(calendars) =>
            calendars must have size 38
        }
      }

      it("must fail trying to read a file if any required field is missing") {
        GtfsAgencyFile(None).read("gtfs/incomplete_agency.txt") match {
          case Right(_) => fail("Parse should have failed by missing fields")
          case Left(ex) =>
            ex.asInstanceOf[MissingFieldsException].fields mustBe List("agency_name")
        }
      }

      it("must fail trying to read a file if any required field is invalid") {
        GtfsAgencyFile(None).read("gtfs/invalid_agency.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(calendars) =>
            calendars must have size 3
            calendars.head match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "agency_id"
            }
            calendars(1).isRight mustBe true
            calendars(2) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "agency_name"
            }

        }
      }
    }

    describe("Value assignment correctness") {
      it("must assign all the values properly") {
        GtfsAgencyFile(None).read("gtfs/complete_agency.txt") match {
          case Left(ex)         => fail("Failed because of unexpected error", ex)
          case Right(calendars) =>
            //1,"S-Bahn Berlin GmbH","http://www.s-bahn-berlin.de","Europe/Berlin","de",""
            calendars.head mustBe Right(
              GtfsAgency(
                1,
                "S-Bahn Berlin GmbH",
                "http://www.s-bahn-berlin.de",
                "Europe/Berlin",
                Some("de"),
                None,
                None,
                None
              )
            )
        }
      }
    }
  }

}
