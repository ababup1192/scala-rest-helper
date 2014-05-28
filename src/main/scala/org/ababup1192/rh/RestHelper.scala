package org.ababup1192.rh

import org.ababup1192.rh.response._
import play.api.libs.ws.WS
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import play.api.libs.json._
import org.ababup1192.rh.response.Put
import org.ababup1192.rh.response.Post
import org.ababup1192.rh.response.Response
import scala.reflect.ClassTag

/**
 * REST HELPER CLASS.
 * @param pDomain example: "http://google.com/"
 */
case class RestHelper(pDomain: String) {

  private def httpRequest(uri: String, httpMethod: HttpMethod): Response[String] = {
    val result = httpMethod match {
      case Get => Await.result(WS.url(uri).get(), Duration.Inf)
      case Post(body: String) => Await.result(WS.url(uri).post(body), Duration.Inf)
      case Put(body: String) => Await.result(WS.url(uri).put(body), Duration.Inf)
      case Delete => Await.result(WS.url(uri).delete(), Duration.Inf)
    }
    Response(result.status, result.body)
  }

  // TODO: Implicit class
  private def jsonToBoolean(jsonValue: JsValue): Product = {
    jsonValue.validate[Boolean].map { value: Boolean => Right(value)}
      .recoverTotal { jsonError: JsError => Left(jsonError)
    }
  }

  private def jsonToInt(jsonValue: JsValue): Product = {
    jsonValue.validate[Int].map { value: Int => Right(value)}
      .recoverTotal { jsonError: JsError => Left(jsonError)
    }
  }

  private def jsonToLong(jsonValue: JsValue): Product = {
    jsonValue.validate[Long].map { value: Long => Right(value)}
      .recoverTotal { jsonError: JsError => Left(jsonError)
    }
  }

  private def jsonToDouble(jsonValue: JsValue): Product = {
    jsonValue.validate[Double].map { value: Double => Right(value)}
      .recoverTotal { jsonError: JsError => Left(jsonError)
    }
  }

  private def jsonToString(jsonValue: JsValue): Product = {
    jsonValue.validate[String].map { value: String => Right(value)}
      .recoverTotal { jsonError: JsError => Left(jsonError)
    }
  }

  /**
   * Convert jsValue to ScalaValue.
   * @param response Response(HTTP_STATUS: Int, HTTP_BODY: String)
   * @tparam T T is expected scala value type. String, Bool, Long, Int, Array, Object ...
   * @return Response(HTTP_STATUS: Int, Either[JsError, T]
   */
  private def jsonToScalaValue[T: ClassTag](response: Response[String]): Response[Product] = {
    val jsonValue: JsValue = Json.parse(response.body)

    val t = implicitly[ClassTag[T]].runtimeClass
    val B = classOf[Boolean]
    val I = classOf[Int]
    val L = classOf[Long]
    val D = classOf[Double]
    val S = classOf[String]

    val result = t match {
      case B => jsonToBoolean(jsonValue)
      case I => jsonToInt(jsonValue)
      case L => jsonToLong(jsonValue)
      case D => jsonToDouble(jsonValue)
      case S => jsonToString(jsonValue)

      case _ => JsError("Unsupported type Error")
    }

    Response(response.status, result)
  }

  /**
   * Convert jsValue to ScalaValue.
   * @param response Response(HTTP_STATUS: Int, HTTP_BODY: String)
   * @param hasReads ensure returned Json Reads[T] method.
   * @tparam T expected Scala value. Case class or mix-in HasReads Object.
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: T)
   */
  private def jsonToScalaValue[T](response: Response[String], hasReads: HasReads[T]): Response[Product] = {
    val jsonValue: JsValue = Json.parse(response.body)

    implicit val jsonReads = hasReads.reads

    val result = jsonValue.validate[T].map {
      value: T => Right(value)
    }
      .recoverTotal {
      jsonError: JsError => Left(jsonError)
    }

    Response(response.status, result)
  }

  /**
   * HTTP GET Request.
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: String)
   */
  def get(): Response[String] = {
    httpRequest(pDomain, Get)
  }

  /**
   * HTTP GET Request.
   * @param path example: "user/:id"
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: String)
   */
  def get(path: String): Response[String] = {
    httpRequest(pDomain + path, Get)
  }

  /**
   * HTTP GET Request. So, BODY: String parse Json value and generate Scala Value.
   * @tparam T T is expected scala value type. Bool, Int, Long, Double, Array String ...
   * @return Response(HTTP_STATUS: Int, Either[JsError, T]
   */
  def getParseJson[T: ClassTag] = {
    jsonToScalaValue[T](get())
  }

  /**
   * HTTP GET Request. So, BODY: String parse Json value and generate Scala case class or mix-in HasReads Object.
   * @param hasReads ensure returned Json Reads[T] method.
   * @tparam T expected Scala value. Case class or mix-in HasReads Object.
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: T)
   */
  def getParseJson[T](hasReads: HasReads[T]) = {
    jsonToScalaValue[T](get(), hasReads)
  }

  /**
   * HTTP GET Request. So, BODY: String parse Json value and generate Scala case class or mix-in HasReads Object.
   * @param path example: "user/:id"
   * @param hasReads ensure returned Json Reads[T] method.
   * @tparam T expected Scala value. Case class or mix-in HasReads Object.
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: T)
   */
  def getParseJson[T](path: String, hasReads: HasReads[T]) = {
    jsonToScalaValue[T](get(path), hasReads)
  }

  /**
   *
   * @tparam T T is expected scala value type. Bool, Int, Long, Double, Array String ...
   * @return Response(HTTP_STATUS: Int, Either[JsError, T]
   */
  def getParseJson[T: ClassTag](path: String) = {
    jsonToScalaValue[T](get(path))
  }

  /**
   * HTTP POST Request.
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: String)
   */
  def post(): Response[String] = {
    httpRequest(pDomain, Post(new String))
  }

  /**
   * HTTP PUT Request.
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: String)
   */
  def put(): Response[String] = {
    httpRequest(pDomain, Put(new String))
  }

  /**
   * HTTP DELETE Request.
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: String)
   */
  def delete(): Response[String] = {
    httpRequest(pDomain, Delete)
  }

  /**
   * HTTP POST Request.
   * @param path example: "user"
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: String)
   */
  def post(path: String): Response[String] = {
    httpRequest(pDomain + path, Post(new String))
  }

  /**
   * HTTP PUT Request.
   * @param path example: "user/:id"
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: String)
   */
  def put(path: String): Response[String] = {
    httpRequest(pDomain + path, Put(new String))
  }

  /**
   * HTTP DELETE Request.
   * @param path example: "user/:id"
   * @return Response(HTTP_STATUS: Int, HTTP_BODY: String)
   */
  def delete(path: String): Response[String] = {
    httpRequest(pDomain + path, Delete)
  }


  /*
  /**
   *
   * @param path example: "user/id"
   * @param responseList example: List(OkResponse[User](Reads[User]), ...)
   * @return Some(Status, json parse result)
   */
  def getResponseParseJson(path: String, responseList: List[Response]): Option[(Int, _)] = {
    val result = Await.result(WS.url(s"$pDomain$path").get(), Duration.Inf)
    val status = result.status
    val body = result.body

    try {
      status match {
        case OK =>
          val response = filterResponse[OkResponse[_]](responseList)
          getRestResult(body, response.head)
        case CREATED =>
          val response = filterResponse[CreatedResponse[_]](responseList)
          getRestResult(body, response.head)
        case NO_CONTENT =>
          val response = filterResponse[NoContentResponse[_]](responseList)
          getRestResult(body, response.head)
        case BAD_REQUEST =>
          val response = filterResponse[BadRequestResponse[_]](responseList)
          getRestResult(body, response.head)
        case NOT_FOUND =>
          val response = filterResponse[NotFoundResponse[_]](responseList)
          getRestResult(body, response.head)
        case _ => None
      }
    } catch {
      case _: Throwable => None
    }
  }
  */

}
