package org.ababup1192.rh.response

import play.api.libs.json._
import play.api.http.Status._

trait HttpMethod

case object Get extends HttpMethod

case class Post(body: String) extends HttpMethod

case class Put(body: String) extends HttpMethod

case object Delete extends HttpMethod

case class Response[T](status: Int, body: T)


trait HasUnapply[T] {
  def unapply(t: T): Option[T]
}

/*
trait Response[T] {
  val status: Int
}

case class OkResponse[T]() extends Response[T] {
  val status = OK
}

case class CreatedResponse[T](jsonReads: Reads[T]) extends Response[T] {
  val status = CREATED
}

case class NoContentResponse[T](jsonReads: Reads[T]) extends Response[T] {
  val status = NO_CONTENT
}

case class BadRequestResponse[T](jsonReads: Reads[T]) extends Response[T] {
  val status = BAD_REQUEST
}

case class NotFoundResponse[T](jsonReads: Reads[T]) extends Response[T] {
  val status = NOT_FOUND
}
*/