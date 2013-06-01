package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.LiftRules
import xml.Text
import net.liftweb.util.Helpers

object Suffix extends RestHelper {

  def init() : Unit = {
    LiftRules.explicitlyParsedSuffixes = Helpers.knownSuffixes &~ Set("com")
    LiftRules.statelessDispatch.append(Suffix)
  }

  serve {

    case "email" :: e :: "send" :: Nil Get req =>
      Text("In middle: "+e)

    case "email" :: e :: Nil Get req =>
      Text("At end: "+e)

  }

}
