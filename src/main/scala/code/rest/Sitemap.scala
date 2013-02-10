package code.rest

import net.liftweb.http._
import net.liftweb.http.rest.RestHelper

object Sitemap extends RestHelper {
  serve {
    case Req("sitemap" :: Nil, _, GetRequest) =>
      XmlResponse(
        S.render(<lift:embed what="sitemap" />, S.request.get.request)
      .head)
  }
}
