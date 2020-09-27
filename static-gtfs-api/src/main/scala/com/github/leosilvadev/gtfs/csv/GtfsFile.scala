package com.github.leosilvadev.gtfs.csv

import java.nio.file.{Files, Path}
import java.time.format.DateTimeFormatter

import scala.collection.mutable
import scala.jdk.StreamConverters._

trait GtfsFile[T] {

  implicit def asBoolean(value: String) = value.toInt == 1
  implicit def asOption(value: String): Option[String] = if (value == null || value.trim.isEmpty) None else Some(value)

  private[csv] val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

  val requiredColumns: Map[String, Int] = Map.empty
  val optionalColumns: Map[String, Int] = Map.empty

  def read(filePath: Path): LazyList[T]

  private[csv] def readLines(filePath: Path): LazyList[Array[String]] = {
    val lazyLines = Files.lines(filePath).toScala(LazyList)

    val headers = lazyLines.head.split(",").map(_.replace("\"", ""))
    val requiredKeys = requiredColumns.keys.toArray.sorted

    if (headers.intersect(requiredKeys).sorted.sameElements(requiredKeys)) {
      val mapTo = mutable.Map[Int, Int]()
      headers.zipWithIndex.collect {
        case (header, index) if optionalColumns.contains(header) =>
          mapTo.put(index, optionalColumns(header))
        case (header, index) if requiredColumns.contains(header) =>
          mapTo.put(index, requiredColumns(header))
      }

      lazyLines.tail
        .map(_.split(","))
        .map(cols => {
          mapTo.values.toList.sorted
            .map(toIndex => {
              cols(mapTo(toIndex))
            })
            .toArray
        })
    } else {
      throw new IllegalArgumentException("Missing required fields")
    }
  }

}
