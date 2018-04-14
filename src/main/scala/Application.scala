import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.noukenolife.kanji.auth.controllers.AuthController
import com.noukenolife.kanji.support.db.jdbc.HikariDatabase
import com.noukenolife.kanji.support.{DI, SwaggerDocService}

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Application extends App {

  implicit val system: ActorSystem = ActorSystem("kanji")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
  HikariDatabase.init()

  val injector = DI.createInjector()

  val route = SwaggerDocService.routes ~
    injector.getInstance(classOf[AuthController]).route

  val bindingFuture = Http().bindAndHandle(route, "localhost", 9000)

  println(s"Server online at http://localhost:9000/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete { _ =>
      HikariDatabase.destroy()
      system.terminate()
    } // and shutdown when done
}
