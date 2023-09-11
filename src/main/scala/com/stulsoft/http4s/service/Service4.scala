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
import org.http4s._
import org.http4s.circe._
import org.http4s.circe.CirceEntityDecoder.circeEntityDecoder
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._

/**
 * Decoding JSON to a case class.
 *
 * @see [[https://http4s.org/v0.23/docs/json.html#decoding-json-to-a-case-class]]
 */
object Service4 extends IOApp with StrictLogging:
  private case class SomeRequest(name: String, age: Int)

  private case class SomeResponse(name2: String, age2: Int)

  private val services = HttpRoutes.of[IO] {
    case req @ POST -> Root / "service4" =>
      for {
        // Decode a SomeRequest request
        someRequest <- req.as[SomeRequest]
        // Create SomeResponse
        someResponse = SomeResponse(someRequest.name + " modified", someRequest.age * 21)
        // Encode a SomeResponse
        resp <- Ok(someResponse.asJson)
      } yield resp
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
