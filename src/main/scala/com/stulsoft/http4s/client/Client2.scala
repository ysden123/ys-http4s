/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.http4s.client

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.http4s.ember.client.EmberClientBuilder
import com.typesafe.scalalogging.StrictLogging
import org.http4s.Uri
import org.http4s.client.middleware.RetryPolicy

import scala.concurrent.duration._
import scala.concurrent.duration.Duration

/**
 * Simple HTTP client with http4s.
 *
 * @see [[https://blog.shangjiaming.com/scala%20tutorial/http4s-introduction-2/#how-to-create-a-client]]
 */
object Client2 extends StrictLogging:
  def main(args: Array[String]): Unit = {
    logger.info("==>main")

    val resource = for {
      client <- EmberClientBuilder
        .default[IO]
//        .retryPolicy(RetryPolicy.exponentialBackoff(1.minute, 3))
        .retryPolicy(RetryPolicy.exponentialBackoff(Duration(1, "minutes"), 3))
        .build
    } yield client

    try {
      val response = resource.use(client =>
          client
            .expect[String](Uri.unsafeFromString("http://localhost:8080/hello/Client2")))
        .unsafeRunSync()
      logger.info("response: {}", response)
    }catch{
      case exception: Exception=>
        logger.error(exception.getMessage, exception)
    }
  }
