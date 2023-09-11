/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.http4s.service

import cats.effect.*
import com.comcast.ip4s.{ipv4, port}
import com.typesafe.scalalogging.StrictLogging
import io.circe.*
import io.circe.literal.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.io.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits.*

/**
 * Sending Raw JSON.
 *
 * @see [[https://http4s.org/v0.23/docs/json.html#sending-raw-json]]
 */
object Service2 extends IOApp with StrictLogging:
  private def hello(name: String): Json =
    json"""{"hello": $name}"""

  private val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(hello(name))
    case POST -> Root / "api" / "hello" / name =>
      Ok(hello(name))
  }.orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    logger.info("Go!")
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(helloWorldService)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)