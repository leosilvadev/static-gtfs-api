package com.github.leosilvadev.gtfs.csv

import com.github.leosilvadev.gtfs.csv.FileReadOps._
import com.github.leosilvadev.gtfs.csv.exceptions.MissingFieldsException
import com.github.leosilvadev.gtfs.domain.GtfsShape
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class GtfsShapeFileTest extends AnyFunSpec with Matchers {

  describe("Gtfs Calendar file read") {
    describe("Field validation") {
      it("must read a file which contains all the required fields") {
        GtfsShapeFile.read("gtfs/complete_shapes.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(shapes) =>
            shapes must have size 11
        }
      }

      it("must fail trying to read a file if any required field is missing") {
        GtfsShapeFile.read("gtfs/incomplete_shapes.txt") match {
          case Right(_) => fail("Parse should have failed by missing fields")
          case Left(ex) =>
            ex.asInstanceOf[MissingFieldsException].fields mustBe List("shape_pt_lat")
        }
      }

      it("must fail trying to read a file if any required field is invalid") {
        GtfsShapeFile.read("gtfs/invalid_shapes.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(shapes) =>
            shapes must have size 11
            shapes.head.isRight mustBe true
            shapes(1) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "shape_pt_lat"
            }
            shapes(2).isRight mustBe true
            shapes(3).isRight mustBe true
            shapes(4) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "shape_pt_lon"
            }
            shapes(5).isRight mustBe true
            shapes(6).isRight mustBe true
            shapes(7).isRight mustBe true
            shapes(8) match {
              case Right(_) => fail("Parse should have failed by invalid field")
              case Left(ex) =>
                ex.field mustBe "shape_id"
            }
            shapes(9).isRight mustBe true
            shapes(10).isRight mustBe true
        }
      }
    }

    describe("Value assignment correctness") {
      it("must assign all the values properly") {
        GtfsShapeFile.read("gtfs/complete_shapes.txt") match {
          case Left(ex) => fail("Failed because of unexpected error", ex)
          case Right(shapes) =>
            shapes must have size 11
            shapes.head mustBe Right(GtfsShape(
              id = "192",
              latitude = 52.390812,
              longitude = 13.066689,
              sequence = 0,
              distanceTraveled = None
            ))
        }
      }
    }
  }

}
