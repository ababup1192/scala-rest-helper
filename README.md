scala-rest-helper
=================

ScalaからREST APIを叩くときに、便利にしたい。返ってくるレスポンスをオブジェクトにして扱いたい。Statusをわかりやすく、パターンマッチしたい。などなど。見やすいコード、使いやすいライブラリを目指しているので、アドバイスお待ちしています。

## 単純なStringをやりとりする例。

    val restHelper = RestHelper("http://localhost:9000/") // ドメインまで指定
    val response = restHelper.get("string")               // その先のパスを指定
    val string = response match {
      case Response(OK, string: String) => string         // Statusコードでパターンマッチ。
      case _ => "failed"
    }

