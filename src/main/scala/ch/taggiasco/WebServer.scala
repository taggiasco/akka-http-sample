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

 
object WebServer extends Config with Routes {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("webserver")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher
    
    val log: LoggingAdapter = Logging(system, getClass)
    
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
 
    val bindingFuture = Http().bindAndHandle(routes, httpInterface, httpPort)
    bindingFuture.onFailure {
      case ex: Exception => log.error(ex, "Failed to bind to {}:{}!", httpInterface, httpPort)
    }
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
