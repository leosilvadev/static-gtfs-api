package com.github.leosilvadev.gtfs.csv

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class FieldMapperTest extends AnyFunSpec with Matchers {

  describe("Field mapper test") {
    describe("Mapping the indexes") {
      it("must map the field indexes even when not all mapped fields are present") {
        val fieldsMapping = Map(
          "f1" -> 0,
          "f2" -> 1,
          "f3" -> 2
        )

        val availableFields = List("f1", "f3")
        val mapped = FieldMapper.mapIndexes(fieldsMapping, availableFields)
        mapped.size mustBe 3
        mapped(0) mustBe Some(0)
        mapped(1) mustBe None
        mapped(2) mustBe Some(1)
      }

      it("must map the field indexes even when all the present fields are not mapped") {
        val fieldsMapping = Map(
          "f1" -> 0,
          "f3" -> 2
        )

        val availableFields = List("f1", "f4", "f3", "f2")
        val mapped = FieldMapper.mapIndexes(fieldsMapping, availableFields)
        mapped.size mustBe 2
        mapped(0) mustBe Some(0)
        mapped(2) mustBe Some(2)
      }
    }

    describe("Mapping the columns") {
      it("must map the columns even if some of them does not exist") {
        val columns = Array(
          "000008012713",
          "",
          "Rangsdorf, Bahnhof",
          "",
          "52.294125000000",
          "13.431112000000",
          "0",
          "900000245025",
          "",
          "",
          ""
        )

        val mappedIndexes = Map(
          0 -> Some(2),
          1 -> Some(0),
          2 -> Some(4),
          3 -> Some(5),
          4 -> None,
          5 -> Some(7)
        )
        val mappedColumns = FieldMapper.mapColumns(columns, mappedIndexes)
        mappedColumns.length mustBe 6
        mappedColumns.head mustBe "Rangsdorf, Bahnhof"
        mappedColumns(1) mustBe "000008012713"
        mappedColumns(2) mustBe "52.294125000000"
        mappedColumns(3) mustBe "13.431112000000"
        mappedColumns(4) mustBe ""
        mappedColumns(5) mustBe "900000245025"
      }
    }
  }

}
