package com.github.leosilvadev.gtfs.csv.exceptions

class FileReadException(val cause: Throwable) extends RuntimeException(cause)

class MissingFieldsException(val fields: List[String]) extends FileReadException(new IllegalArgumentException(s"Missing required fields: $fields"))