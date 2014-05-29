package org.ababup1192.rh

import org.specs2.mutable.Specification
import play.api.http.Status._
import play.api.libs.json.{Reads, Json}
import org.ababup1192.rh.json.Json._
import org.ababup1192.rh.response.HttpResponse.Response

object RestHelperSpec extends Specification {

  case class User(id: Int, name: String)

  object User extends HasReads[User] {
    override def reads: Reads[User] = Json.reads[User]
  }

  case class JsonError(code: Int, name: String, description: String)

  object JsonError extends HasReads[JsonError] {
    override def reads: Reads[JsonError] = Json.reads[JsonError]
  }

  "RestHelper" should {

    "String http get" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.get("string")
      val string = response match {
        case Response(OK, string: String) => string
        case _ => "failed"
      }
      string mustEqual "get"
    }

    "String http post" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.post("string")
      val string = response match {
        case Response(OK, string: String) => string
        case _ => "failed"
      }
      string mustEqual "post"
    }

    "String http put" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.put("string")
      val string = response match {
        case Response(OK, string: String) => string
        case _ => "failed"
      }
      string mustEqual "put"
    }

    "String http delete" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.delete("string")
      val string = response match {
        case Response(OK, string: String) => string
        case _ => "failed"
      }
      string mustEqual "delete"
    }

    "JsonBool http get" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.getParseJson[Boolean]("bool.json")
      val string = response match {
        case Response(OK, Right(result: Boolean)) => result
        case Response(_, Left(jsError)) => false
      }
      string mustEqual true
    }

    "JsonInt http get" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.getParseJson[Int]("int.json")
      val string = response match {
        case Response(OK, Right(result: Int)) => result
        case Response(_, Left(jsError)) => -1
      }
      string mustEqual 10
    }
    "JsonLong http get" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.getParseJson[Long]("long.json")
      val string = response match {
        case Response(OK, Right(result: Long)) => 10L
        case Response(_, Left(jsError)) => -1L
      }
      string mustEqual 10L
    }
    "JsonDouble http get" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.getParseJson[Double]("double.json")
      val string = response match {
        case Response(OK, Right(result: Double)) => result
        case Response(_, Left(jsError)) => -1.0d
      }
      string mustEqual 10.0d
    }

    "JsonString http get" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.getParseJson[String]("string.json")
      val string = response match {
        case Response(OK, Right(result: String)) => result
        case Response(_, Left(jsError)) => jsError.toString
      }
      string mustEqual "get jsString"
    }

    "JsonObject http get" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.getParseJson[User]("user.json", User)
      val user = response match {
        case Response(OK, Right(result: User)) => result
        case Response(_, Left(jsError)) => User(-1, "failed")
      }
      user mustEqual User(1, "abab")
    }

    /*

    "Multiple Request http get" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.getParseJson("user.json",
        List(OkRequest[User](User)))
      val user = response match {
        case Response(OK, Right(result: User)) => result
        case Response(_, Left(jsError)) => User(-1, "failed")
      }
      user mustEqual User(1, "abab")
    }

    "Multiple Request http get faled case" in {
      val restHelper = RestHelper("http://localhost:9000/")
      val response = restHelper.getParseJson("user.json/bad",
        List(OkRequest[User](User), BadRequest[JsonError](JsonError)))
      val error = response match {
        case Response(OK, Right(result: User)) => result
        case Response(BAD_REQUEST, Right(result: JsonError)) => result
        case Response(_, Left(jsError)) => User(-1, "failed")
      }
      error mustEqual JsonError(1, "ID NotFound", "User's id not found")
    }
    */
  }
}
