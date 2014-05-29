package org.ababup1192.rh.json

import play.api.libs.json.Reads

object Json {

  trait HasReads[T] {
    def reads: Reads[T]
  }

}
