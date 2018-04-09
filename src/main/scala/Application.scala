import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.noukenolife.kanji.auth.controllers.AuthController
import com.noukenolife.kanji.support.{DI, SwaggerDocService}
import skinny.DBSettings

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Application extends App {

  implicit val system: ActorSystem = ActorSystem("kanji")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
  DBSettings.initialize()

  val injector = DI.createInjector()

  val route = SwaggerDocService.routes ~
    injector.getInstance(classOf[AuthController]).route

  val bindingFuture = Http().bindAndHandle(route, "localhost", 9000)

  println(s"Server online at http://localhost:9000/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete { _ =>
      DBSettings.destroy()
      system.terminate()
    } // and shutdown when done
}
