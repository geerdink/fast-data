package nl.ing.api

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._

class APIService extends Actor with MyService {

    // the HttpService trait defines only one abstract member, which
    // connects the services environment to the enclosing actor or test
    def actorRefFactory = context

    // this actor only runs our route, but you could add
    // other things here, like request stream processing
    // or timeout handling
    def receive = runRoute(myRoute)
  }

// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {

  val myRoute =
    path("") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      }
    } ~ //This is the PATH separator for adding more paths
    path("hello" / Segment) { pathVariable => //The pathvariable will be /hello/{pathVariable}
      get{
        respondWithMediaType(`text/plain`) { //Just return plain text
          complete{
            s"You requested: $pathVariable" //Will respond with what you send within the URL
          }
        }
      }
    }
}
