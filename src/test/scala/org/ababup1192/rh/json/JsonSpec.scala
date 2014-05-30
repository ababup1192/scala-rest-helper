package org.ababup1192.rh.json

import org.specs2.mutable.Specification
import play.api.libs.json.{Json => PlayJson, Format, JsValue}
import org.ababup1192.rh.json.Json.{HasFormat, JsonWritable}

object JsonSpec extends Specification {

  case class User(id: Long, name: String) extends JsonWritable {
    /**
     * Require toJson method.
     * And implements as follows
     * implicit val jsonWrites = [Companion Object].writes or format
     * [PlayJson].toJson(this)
     */
    override def toJson: JsValue = {
      implicit val jsonWrites = User.format
      PlayJson.toJson(this)
    }
  }

  object User extends HasFormat[User] {
    override def format: Format[User] = PlayJson.format[User]
  }

  "Json" should {

    "JsonWritable converts case class to Json" in {

      val userJson = User(1, "abab").toJson
      val expected = PlayJson.obj("id" -> 1, "name" -> "abab")
      userJson mustEqual expected
    }
  }
}
    /*
    "JsonReadable converts Json to case class " in {
      case class Error(desc: String)
      val user = JsonReader.toObject[User](User, PlayJson.obj("id" -> 1, "name" -> "abab"))

      val expected = User(1, "abab")
      user mustEqual expected
    }

  }
  }*/