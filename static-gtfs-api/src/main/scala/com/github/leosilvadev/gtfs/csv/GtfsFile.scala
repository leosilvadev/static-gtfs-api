package com.github.leosilvadev.gtfs.csv

import java.nio.file.{Files, Path}
import java.time.{LocalDate, LocalTime}
import java.time.format.{DateTimeFormatter, DateTimeParseException}

import com.github.leosilvadev.gtfs.csv.exceptions.{
  FileReadException,
  InvalidFieldValueException,
  MissingFieldsException
}

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

  private val columnsSeparationRegex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"

  private[csv] def readLines(filePath: Path): Either[FileReadException, LazyList[Array[String]]] = {
    val lazyLines = Files.lines(filePath).toScala(LazyList)

    val headers = lazyLines.head.split(columnsSeparationRegex, -1).map(_.replace("\"", "")).toList
    val allColumns = requiredColumns ++ optionalColumns
    val requiredKeys = requiredColumns.keys.toList.sorted

    if (requiredKeys.intersect(headers) != requiredKeys) {
      return Left(new MissingFieldsException(requiredKeys.diff(headers)))
    }

    val mappedIndexes = FieldMapper.mapIndexes(allColumns, headers)

    Right(
      lazyLines.tail
        .map(_.split(columnsSeparationRegex, -1))
        .map(cols => FieldMapper.mapColumns(cols, mappedIndexes))
    )
  }

  private[csv] def toString(
      value: String,
      field: String,
      defaultValue: Option[String] = None
  ): Either[InvalidFieldValueException, String] = {
    val finalValue =
      Option(value).map(_.replaceAllLiterally("\"", "")).map(_.trim).filterNot(_.isEmpty).orElse(defaultValue)
    if (finalValue.isEmpty) Left(new InvalidFieldValueException(field, value)) else Right(finalValue.get)
  }

  private[csv] def toLong(
      value: String,
      field: String,
      defaultValue: Option[Long] = None
  ): Either[InvalidFieldValueException, Long] =
    try {
      val finalValue = value.replaceAllLiterally("\"", "").trim
      Right(finalValue.toLong)
    } catch {
      case _: Throwable if defaultValue.isDefined => Right(defaultValue.get)
      case _: Throwable                           => Left(new InvalidFieldValueException(field, value))
    }

  private[csv] def toInt(
      value: String,
      field: String
  ): Either[InvalidFieldValueException, Int] =
    try {
      val finalValue = value.replaceAllLiterally("\"", "").trim
      Right(finalValue.toInt)
    } catch {
      case _: Throwable => Left(new InvalidFieldValueException(field, value))
    }

  private[csv] def toDouble(
      value: String,
      field: String
  ): Either[InvalidFieldValueException, Double] =
    try {
      val finalValue = value.replaceAllLiterally("\"", "").trim
      Right(finalValue.toDouble)
    } catch {
      case _: Throwable => Left(new InvalidFieldValueException(field, value))
    }

  private[csv] def toOptionalDouble(
      value: String
  ): Option[Double] =
    try {
      val finalValue = value.replaceAllLiterally("\"", "").trim
      Some(finalValue.toDouble)
    } catch {
      case _: Throwable => None
    }

  private[csv] def toOptionalInt(
      value: String
  ): Option[Int] =
    try {
      val finalValue = value.replaceAllLiterally("\"", "").trim
      Some(finalValue.toInt)
    } catch {
      case _: Throwable => None
    }

  private[csv] def toOptionalLong(
      value: String
  ): Option[Long] =
    try {
      val finalValue = value.replaceAllLiterally("\"", "").trim
      Some(finalValue.toLong)
    } catch {
      case _: Throwable => None
    }

  private[csv] def toOptionalString(value: String): Option[String] = {
    val finalValue = value.replaceAllLiterally("\"", "").trim
    if (finalValue.isEmpty) None else Some(finalValue)
  }

  private[csv] def toBoolean(value: String, field: String): Either[InvalidFieldValueException, Boolean] =
    try {
      val finalValue = value.replaceAllLiterally("\"", "").trim
      if (finalValue == "0") Right(false)
      else if (finalValue == "1") Right(true)
      else Left(new InvalidFieldValueException(field, value))
    } catch {
      case _: Throwable => Left(new InvalidFieldValueException(field, value))
    }

  private[csv] def toDate(value: String, field: String): Either[InvalidFieldValueException, LocalDate] =
    try {
      val finalValue = value.replaceAllLiterally("\"", "").trim
      Right(LocalDate.parse(finalValue, dateFormatter))
    } catch {
      case _: Throwable => Left(new InvalidFieldValueException(field, value))
    }

  private[csv] def toOptionalLocalTime(value: String): Option[LocalTime] = {
    val finalValue = value.replaceAllLiterally("\"", "").trim
    if (finalValue.isEmpty) None
    else
      try {
        Some(LocalTime.parse(fixTimeStr(finalValue), DateTimeFormatter.ISO_LOCAL_TIME))
      } catch {
        case _: DateTimeParseException => None
      }
  }

  private[csv] def fixTimeStr(value: String): String = if (value.length == 7) s"0$value" else value
}
