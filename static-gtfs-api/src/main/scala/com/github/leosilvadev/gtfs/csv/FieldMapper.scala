package com.github.leosilvadev.gtfs.csv

import scala.collection.mutable.ArrayBuffer

object FieldMapper {

  def mapIndexes(fieldsMapping: Map[String, Int], availableFields: List[String]): Map[Int, Option[Int]] = {
    fieldsMapping.map {
      case (name, targetIndex) =>
        val availableIndex = availableFields.indexOf(name)
        if (availableIndex >= 0) (targetIndex, Some(availableIndex)) else (targetIndex, None)
    }
  }

  def mapColumns(columns: Array[String], mappedIndexes: Map[Int, Option[Int]]): Array[String] = {
    val result = ArrayBuffer.fill(mappedIndexes.size)("")
    mappedIndexes.foreach {
      case (targetIndex, Some(sourceIndex)) =>
        result.update(targetIndex, columns(sourceIndex))
      case (targetIndex, None) =>
        result.update(targetIndex, "")
    }
    result.toArray
  }

}
