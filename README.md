scala-rest-helper
=================

ScalaからREST APIを叩くときに、便利にしたい。返ってくるレスポンスをオブジェクトにして扱いたい。Statusをわかりやすく、パターンマッチしたい。などなど。見やすいコード、使いやすいライブラリを目指しているので、アドバイスお待ちしています。

    val restHelper = RestHelper("http://localhost:9000/")
    val response = restHelper.get("string")
    val string = response match {
      case Response(OK, string: String) => string
      case _ => "failed"
    }

