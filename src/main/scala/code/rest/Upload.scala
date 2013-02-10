package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{Req, LiftRules}

object Upload extends RestHelper {

  def init() : Unit = {
    LiftRules.statelessDispatch.append(Upload)
  }

  serve {
    case "upload" :: Nil Post req =>
      for {
        bodyBytes <- req.body
      } yield <info>Received {bodyBytes.length} bytes</info>

    case "jpg" :: Nil Post JPeg(req) =>
      for {
        bodyBytes <- req.body
      } yield <info>Jpeg Received {bodyBytes.length} bytes</info>

  }

  object JPeg {
    def unapply(req: Req): Option[Req] =
      req.contentType.filter(_ == "image/jpg").map(_ => req)
  }



}
