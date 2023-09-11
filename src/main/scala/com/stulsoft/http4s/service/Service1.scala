/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.http4s.service

import cats.effect.*
import com.comcast.ip4s.*
import com.typesafe.scalalogging.StrictLogging
import org.http4s.HttpRoutes
import org.http4s.dsl.io.*
import org.http4s.implicits.*
import org.http4s.ember.server.*

object Service1 extends IOApp with StrictLogging:
  private val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
    case GET -> Root / "s2" / name =>
      Ok(s"S2, $name.")
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