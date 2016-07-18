package ch.taggiasco

import akka.actor.ActorSystem
import akka.util.ByteString
import akka.event.{Logging, LoggingAdapter}
import akka.stream.scaladsl._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import scala.util.Random

 
trait Routes {
  
  /*
  val pathSpecificRoutes = path("some" / "other") { get { complete(...) } }
  val routes = pathPrefix("v1") { pathSpecificRoutes }
  */
  
  // streams are re-usable so we can define it here
  // and use it for every request
  val numbers = Source.fromIterator(() => Iterator.continually(Random.nextInt()))
  
  val routes =
    path("")(getFromResource("public/index.html")) ~
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    } ~
    path("documentation") {
      encodeResponse {
        getFromResourceDirectory("public/docs")
      }
    } ~
    path("random") {
      get {
        complete(
          HttpEntity(
            ContentTypes.`text/plain(UTF-8)`,
            numbers.map(n => ByteString(s"$n\n"))
          )
        )
      }
    }
  
}
