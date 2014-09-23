package code.rest

import java.io.InputStream
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.StreamingResponse

/**

An example of streaming video.  There are two MP4 videos provided
by this class, "wagging" and "running", but you can add
more into the "catalogue" list.

Calling this server will produce the following (using HTTPie):

{{{
    $ http :8080/video/wagging
    HTTP/1.1 206 Partial Content
    Cache-Control: no-cache, private, no-store
    Connection: close
    Content-Length: 296261
    Content-Range: bytes 0-296260/296261
    Content-Type: video/mp4
    Date: Tue, 23 Sep 2014 11:27:27 GMT
    Date: Tue, 23 Sep 2014 11:27:27 GMT
    Expires: Tue, 23 Sep 2014 11:27:27 GMT
    Pragma: no-cache
    Server: Jetty(9.2.1.v20140609)
    X-Frame-Options: SAMEORIGIN
    X-Lift-Version: 2.6-RC1

    +-----------------------------------------+
    | NOTE: binary data not shown in terminal |
    +-----------------------------------------+
}}}
*/

object VideoServer extends RestHelper {

  // Container for information about each video we're going to offer
  // plus the ability to open a video from the classpath.
  case class Video(name: String, title: String, numBytes: Int) {
    lazy val path = s"/video/$name.mp4"
    def open(): Option[InputStream] =
      Option(getClass getResourceAsStream path)
  }

  val catalogue =
    Video("wagging", "Dog wagging tail in a forest", 296261) ::
    Video("running", "Dog running for frisbee",      360520) :: Nil

  serve {
    case "video" :: name :: Nil Get request =>
      for {
        video  <- catalogue.find(_.name == name)
        stream <- video.open()
      } yield {

        val start = 0L
        val end   = video.numBytes - 1

        val headers = List(
          "Content-Type"      -> "video/mp4",
          "Connection"        -> "close",
          "Transfer-Encoding" -> "chunked",
          "Content-Range"     -> s"bytes ${start}-${end}/${video.numBytes}"
        )

        StreamingResponse(
          data  = stream,
          onEnd = stream.close,
          video.numBytes,
          headers,
          cookies = Nil,
          code = 206)
      }
  }

  def init() {
    import net.liftweb.http.LiftRules
    LiftRules.statelessDispatch.append(VideoServer)
  }
}