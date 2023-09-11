/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.http4s.service

import cats.effect._
import com.comcast.ip4s.{ipv4, port}
import com.typesafe.scalalogging.StrictLogging
import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s.*
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._

/**
 * Encoding case classes as JSON.
 *
 * @see [[https://http4s.org/v0.23/docs/json.html#encoding-case-classes-as-json]]
 */
object Service3 extends IOApp with StrictLogging:
  private case class Hello(name: String)

  private case class User(name: String)

  private val services = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(Hello(name).asJson)
    case GET -> Root / "user" / name =>
      Ok(User(name).asJson)
  }.orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    logger.info("Go!")
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(services)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)