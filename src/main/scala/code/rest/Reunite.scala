package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.LiftRules
import xml.Text

object Reunite extends RestHelper  {

  def init() : Unit = {
    LiftRules.statelessDispatch.append(Reunite)
  }

  private def reunite(name: String, suffix: String) =
    if (suffix.isEmpty) name else name+"."+suffix

  serve {
    case "download" :: file :: Nil Get req if req.path.suffix == "png" =>
      Text("You requested PNG file called "+file)

    case "download" :: file :: Nil Get req =>
      Text("You requested "+reunite(file, req.path.suffix))

    case "negotiate" :: file :: Nil Get req =>

      val toSend =
        if (req.header("Accept").exists(_ == "image/webp")) file+".webp"
        else file+".png"

      Text("You requested "+file+", would send "+toSend)

  }

}
