package code.rest

import net.liftweb.http.{GetRequest, Req, LiftRules}
import xml.Text
import net.liftweb.http.rest.RestHelper

object CSV extends RestHelper {

  def init() : Unit = {
    LiftRules.explicitlyParsedSuffixes += "csv"
    LiftRules.statelessDispatch.append(CSV)
  }

  serve {
    case Req("reports" :: name :: Nil, "csv", GetRequest) =>
      Text("Here's your CSV report for "+name)
  }


}
