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
    val bool = response match {                                   
        case Response(OK, Right(result: Boolean)) => result         // Eitherで指定した型でパターンマッチ
        case Response(_, Left(jsError)) => false                    // 失敗した場合は、jsErrorが返される
    }

## JsonValueをcase classに変換して返してくれる例

    case class User(id: Int, name: String)                          // コンパニオンオブジェクトを用意
    object User extends HasReads[User] {
        override def reads: Reads[User] = Json.reads[User]          // HasReads[ケースクラス] をミックスイン
    }                                                               // そして、readsメソッドをオーバーライド
    val restHelper = RestHelper("http://localhost:9000/")
    val response = restHelper.getParseJson[User]("user.json", User) // 上で用意したケースクラスをジェネリクスで指定
    val user = response match {
        case Response(OK, Right(result: User)) => result            // パターンマッチで取得
        case Response(_, Left(jsError)) => User(-1, "failed")
    }
    
