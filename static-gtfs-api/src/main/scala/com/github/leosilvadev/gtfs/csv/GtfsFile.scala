package com.github.leosilvadev.gtfs.csv

import java.time.format.DateTimeFormatter

import better.files.File

import scala.collection.mutable

trait GtfsFile[T] {

  implicit def asBoolean(value: String) = value.toInt == 1
  implicit def asOption(value: String): Option[String] = if (value == null || value.trim.isEmpty) None else Some(value)

  private[csv] val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

  val requiredColumns: Map[String, Int] = Map.empty
  val optionalColumns: Map[String, Int] = Map.empty

  def read(file: File): Iterator[T]

  private[csv] def readLines(file: File): Iterator[Array[String]] = {
    val iterator = file.lineIterator
    val headers = iterator.next().split(",").map(_.replace("\"", ""))
    val requiredKeys = requiredColumns.keys.toArray.sorted

    if (headers.intersect(requiredKeys).sorted.sameElements(requiredKeys)) {
      val mapTo = mutable.Map[Int, Int]()
      headers.zipWithIndex.collect {
        case (header, index) if optionalColumns.contains(header) =>
          mapTo.put(index, optionalColumns(header))
        case (header, index) if requiredColumns.contains(header) =>
          mapTo.put(index, requiredColumns(header))
      }

      iterator.map(_.split(",")).map(cols => {
        mapTo.values.toList.sorted.map(toIndex => {
          cols(mapTo(toIndex))
        }).toArray
      })
    } else {
      throw new IllegalArgumentException("Missing required fields")
    }
  }

}
