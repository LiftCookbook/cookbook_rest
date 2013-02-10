package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.LiftRules
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

object QuotationsAPI extends RestHelper {

  def init() : Unit = {
    LiftRules.statelessDispatch.append(QuotationsAPI)
  }

  serve {
    case "quotation" :: Nil JsonGet _ =>
      ("text" -> "A beach house isn't just real estate. It's a state of mind.") ~
        ("by" -> "Douglas Adams") : JValue
  }

}
