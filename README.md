scala-rest-helper
=================

ScalaからREST APIを叩くときに、便利にしたい。返ってくるレスポンスをオブジェクトにして扱いたい。Statusをわかりやすく、パターンマッチしたい。などなど・・・。見やすいコード、使いやすいライブラリを目指しているので、アドバイスお待ちしています。

## 単純なStringをやりとりする例

    val restHelper = RestHelper("http://localhost:9000/")           // ドメインまで指定
    val response = restHelper.get("string")                         // その先のパスを指定 GET,POST,PUT,DELETEをサポート
    val string = response match {
      case Response(OK, string: String) => string                   // Statusコードでパターンマッチ
      case _ => "failed"
    }

## JsonValueをScalaValueに変換して返してくれる例
    
    val restHelper = RestHelper("http://localhost:9000/")           
    val response = restHelper.getParseJson[Boolean]("bool.json")    // ジェネリクスで欲しい戻り値の型を指定
    val string = response match {                                   
        case Response(OK, Right(result: Boolean)) => result         // Eitherで指定した型でパターンマッチ
        case Response(_, Left(jsError)) => false                    // 失敗した場合は、jsErrorが返される
    }
