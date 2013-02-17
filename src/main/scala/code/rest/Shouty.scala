package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST._
import net.liftweb.http.{OkResponse, Req, LiftRules}

/**
 * Provides a CAPITALISED greeting to a JSON request containing a "name" value.
 *
 * Example call:
  curl -d '{ "name" : "Richard" }' -X POST -H 'Content-type: application/json' http://127.0.0.1:8080/shout
  {
   "greeting":"HELLO RICHARD"
  }
 */
object Shouty extends RestHelper {

  def greet(name: String) : JValue =
    "greeting" -> ("HELLO "+name.toUpperCase)

  serve {
    case "shout" :: Nil JsonPost json->request =>
      for { JString(name) <- (json \\ "name").toOpt }
      yield greet(name)

  }

  def init() {
    LiftRules.statelessDispatch.append(Shouty)
  }

}
