/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.http4s.client

import cats.effect.{IO, IOApp}
import com.typesafe.scalalogging.StrictLogging
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.client.Client
/**
 * Simple HTTP client
 *
 * @see [[https://http4s.org/v0.23/docs/client.html]]
 */
object Client1  extends IOApp.Simple with StrictLogging{
  private def printHello(client: Client[IO]): IO[Unit] =
    client
      .expect[String]("http://localhost:8080/hello/Ember")
      .flatMap(IO.println)

  val run: IO[Unit] = EmberClientBuilder
    .default[IO]
    .build
    .use(client => printHello(client))
}