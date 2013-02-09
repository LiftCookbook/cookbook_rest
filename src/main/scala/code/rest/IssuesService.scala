package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.LiftRules

object IssuesService extends RestHelper {

  def init() : Unit = {
    LiftRules.statelessDispatch.append(IssuesService)
  }

  serve("issues" / "by-state" prefix {
    case "open" :: Nil XmlGet _ => <p>None open</p>
    case "closed" :: Nil XmlGet _ => <p>None closed</p>
    case state :: Nil XmlDelete _ => <p>All deleted</p>
  })

}
