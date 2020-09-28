package com.github.leosilvadev.gtfs.csv

import java.nio.file.{Files, Path}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.github.leosilvadev.gtfs.csv.exceptions.{
  FileReadException,
  InvalidFieldValueException,
  MissingFieldsException
}

import scala.collection.mutable
import scala.jdk.StreamConverters._

trait GtfsFile[T] {

  implicit def asBoolean(value: String) = value.toInt == 1
  implicit def asOption(value: String): Option[String] = if (value == null || value.trim.isEmpty) None else Some(value)

  private[csv] val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

  val requiredColumns: Map[String, Int] = Map.empty
  val optionalColumns: Map[String, Int] = Map.empty

  def parse(columns: Array[String]): Either[InvalidFieldValueException, T]

  def read(filePath: Path): Either[FileReadException, LazyList[Either[InvalidFieldValueException, T]]] =
    readLines(filePath).map(_.map(parse))

  private[csv] def readLines(filePath: Path): Either[FileReadException, LazyList[Array[String]]] = {
    val lazyLines = Files.lines(filePath).toScala(LazyList)

    val headers = lazyLines.head.split(",").map(_.replace("\"", "")).toList
    val requiredKeys = requiredColumns.keys.toList.sorted
    val optionalKeys = optionalColumns.keys.toList.sorted

    requiredKeys.partition(headers.contains) match {
      case (_, Nil) =>
        val mapTo = mutable.Map[Int, Int]()
        val optionalKeysFound = mutable.Buffer[String]()
        headers.zipWithIndex.collect {
          case (header, index) if optionalColumns.contains(header) =>
            val targetIndex = optionalColumns(header)
            mapTo.put(index, targetIndex)
            optionalKeysFound.addOne(header)
          case (header, index) if requiredColumns.contains(header) =>
            mapTo.put(index, requiredColumns(header))
        }

        optionalKeys.diff(optionalKeysFound).foreach { key =>
          {
            mapTo.put(mapTo.keys.last + 1, optionalColumns(key))
          }
        }

        Right(
          lazyLines.tail
            .map(_.split(","))
            .map(cols => {
              mapTo.values
                .map(toIndex => {
                  if (toIndex >= cols.length) ""
                  else cols(mapTo(toIndex))
                })
                .toArray
            })
        )

      case (_, notFoundKeys) =>
        Left(new MissingFieldsException(notFoundKeys))
    }
  }

  private[csv] def toLong(
      value: String,
      field: String,
      defaultValue: Option[Long] = None
  ): Either[InvalidFieldValueException, Long] =
    try {
      Right(value.toLong)
    } catch {
      case _: Throwable if defaultValue.isDefined => Right(defaultValue.get)
      case _: Throwable                           => Left(new InvalidFieldValueException(field, value))
    }

  private[csv] def toString(value: String, field: String): Either[InvalidFieldValueException, String] = {
    val finalValue = value.replaceAllLiterally("\"", "").trim
    if (finalValue.isEmpty) Left(new InvalidFieldValueException(field, value)) else Right(finalValue)
  }

  private[csv] def toOptionalString(value: String): Option[String] = {
    val finalValue = value.replaceAllLiterally("\"", "").trim
    if (finalValue.isEmpty) None else Some(finalValue)
  }

  private[csv] def toBoolean(value: String, field: String): Either[InvalidFieldValueException, Boolean] =
    try {
      if (value == "0") Right(false)
      else if (value == "1") Right(true)
      else Left(new InvalidFieldValueException(field, value))
    } catch {
      case _: Throwable => Left(new InvalidFieldValueException(field, value))
    }

  private[csv] def toDate(value: String, field: String): Either[InvalidFieldValueException, LocalDate] =
    try {
      Right(LocalDate.parse(value, dateFormatter))
    } catch {
      case _: Throwable => Left(new InvalidFieldValueException(field, value))
    }

}
