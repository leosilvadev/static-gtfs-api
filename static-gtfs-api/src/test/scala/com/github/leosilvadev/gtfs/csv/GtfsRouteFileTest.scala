package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.domain.GtfsRoute
import com.github.leosilvadev.gtfs.csv.FileReadOps._
import com.github.leosilvadev.gtfs.csv.exceptions.MissingFieldsException
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsRouteFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Agency file read") {
    describe("Field validation") {
      it("must read a file which contains all the required fields") {
        GtfsRouteFile(None).read("gtfs/complete_routes.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(routes) =>
            routes must have size 7
        }
      }

      it("must fail trying to read a file if any required field is missing") {
        GtfsRouteFile(None).read("gtfs/incomplete_routes.txt") match {
          case Right(_) => fail("Parse should have failed by missing fields")
          case Left(ex) =>
            ex.asInstanceOf[MissingFieldsException].fields mustBe List("route_id")
        }
      }

      it("must fail trying to read a file if any required field is invalid") {
        GtfsRouteFile(None).read("gtfs/invalid_routes.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(routes) =>
            routes must have size 7
            routes.head.isRight mustBe true
            routes(1).isRight mustBe true
            routes(2) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "route_id"
            }
            routes(3).isRight mustBe true
            routes(4) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "route_type"
            }
            routes(5).isRight mustBe true
            routes(6) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "agency_id"
            }

        }
      }

      it("must not faild when trying to read a file that miss agency data when there is a global agency") {
        GtfsRouteFile(Some("globalid")).read("gtfs/invalid_routes.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(routes) =>
            routes must have size 7
            routes(6).isRight mustBe true
        }
      }
    }

    describe("Value assignment correctness") {
      it("must assign all the values properly") {
        GtfsRouteFile(None).read("gtfs/complete_routes.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(routes) =>
            routes(4) mustBe Right(
              GtfsRoute(
                id = "19047_100",
                agencyId = "108",
                shortName = Some("RE3"),
                longName = None,
                description = Some("Wittenberg/Falkenberg <> Berlin <> Stralsund/Schwedt"),
                `type` = 100,
                url = None,
                color = Some("FF5900"),
                textColor = Some("FFFFFF"),
                sortOrder = None,
                continuousPickup = None,
                continuousDropOff = None
              )
            )
        }
      }
    }
  }

}
