package org.ababup1192.rh.response

import org.ababup1192.rh.json.Json.HasReads
import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

object HttpResponse {

  trait HttpMethod

  case object Get extends HttpMethod

  case class Post(body: String) extends HttpMethod

  case class Put(body: String) extends HttpMethod

  case object Delete extends HttpMethod

  case class Response[T](status: Int, body: T)

}

object HttpRequest {

  trait Request {
    type U

    def getClassTag: Class[_]
  }

  /*
    trait SimpleRequest[T] extends Request {
      type U = T

      override def getClassTag[T: ClassTag] = super.getClassTag[T]
    }

    case class OkSimpleRequest[T]() extends SimpleRequest[T]

    case class CreatedSimpleRequest[T]() extends SimpleRequest[T]

    case class NoContentSimpleRequest[T]() extends SimpleRequest[T]

    case class BadSimpleRequest[T]() extends SimpleRequest[T]

    case class NoFoundSimpleRequest[T]() extends SimpleRequest[T]
  */
  /*
  trait JsonRequest[T] extends Request{
    val hasReads: HasReads[T]

    def getClassTag: Class[_]

  }

  case class OkRequest[T: ClassTag](hasReads: HasReads[T]) extends JsonRequest[T] {
    override def getClassTag = implicitly[ClassTag[T]].runtimeClass
  }

  case class CreatedRequest[T: ClassTag](hasReads: HasReads[T]) extends JsonRequest[T] {
    override def getClassTag = implicitly[ClassTag[T]].runtimeClass
  }

  case class NoContentRequest[T: ClassTag](hasReads: HasReads[T]) extends JsonRequest[T] {
    override def getClassTag = implicitly[ClassTag[T]].runtimeClass
  }

  case class BadRequest[T: ClassTag](hasReads: HasReads[T]) extends JsonRequest[T] {
    override def getClassTag = implicitly[ClassTag[T]].runtimeClass
  }

  case class NotFoundRequest[T: ClassTag](hasReads: HasReads[T]) extends JsonRequest[T] {
    override def getClassTag = implicitly[ClassTag[T]].runtimeClass
  }
  */

}

