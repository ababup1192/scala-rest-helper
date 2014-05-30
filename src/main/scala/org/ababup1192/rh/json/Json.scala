package org.ababup1192.rh.json

import play.api.libs.json._
import play.api.mvc.{Action, Result, Controller}
import play.api.mvc.BodyParsers.parse

object Json {

  /**
   * Please mix-in Companion Object.
   * [PlayJson].reads[T]
   * @tparam T Companion case class name.
   */
  trait HasReads[T] {
    def reads: Reads[T]
  }

  /**
   * Please mix-in Companion Object.
   * [PlayJson].writes[T]
   * @tparam T Companion case class name.
   */
  trait HasWrites[T] {
    def writes: Writes[T]
  }

  /**
   * Please mix-in Companion Object.
   * [PlayJson].format[T]
   * @tparam T Companion case class name.
   */
  trait HasFormat[T] {
    def format: Format[T]
  }

  /**
   * Require toJson method.
   * And implements as follows
   * implicit val jsonWrites = [Companion Object].writes
   * [PlayJson].toJson(this)
   */
  trait JsonWritable {
    /**
     * Require toJson method.
     * And implements as follows
     * implicit val jsonWrites = [Companion Object].writes or format
     * [PlayJson].toJson(this)
     */
    def toJson: JsValue
  }

}
