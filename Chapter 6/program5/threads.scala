import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef
import akka.routing.ActorRefRoutee
import akka.routing.RoundRobinRoutingLogic
import akka.routing.Router
import scala.util.Random

case class Work()
case class Inside()
case class Outside()
case class Request()
case class Result(result: Double)

class PiCollector extends Actor {
  var counter: Double = 0
  var counterTrue: Double = 0
  def receive = {
    case inside: Inside =>
      counter = counter + 1
      counterTrue = counterTrue + 1
    case outside: Outside =>
      counter = counter + 1
    case request: Request =>
      println(4.0 * counterTrue / counter)
  }
}

class PiGenerator(collector: ActorRef) extends Actor {
   def receive = {
     case w: Work => {
       val x = Random.nextDouble
       val y = Random.nextDouble
       if (x * x + y * y < 1.0) {
         collector ! Inside()
       } else {
         collector ! Outside()
       }
     }
   }
}
 
class PiRouter(collector: ActorRef) extends Actor {
   var counter: Double = 0
   var counterTrue: Double = 0

  var router = {
    val routees = Vector.fill(4) {
      val r = context.actorOf(Props(new PiGenerator(collector)))
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }
 
  def receive = {
    case w: Work =>
      router.route(w, sender())
  }
}

object PiAkka {
  def main(args: Array[String]) {
    val system = ActorSystem("PiSystem")
    val piCollector = system.actorOf(Props[PiCollector], name = "picollector")
    val piRouter = system.actorOf(Props(new PiRouter(piCollector)), name = "pirouter")
    for (i <- 0 until 1000000) {
      piRouter ! Work()
    }
    Thread.sleep(10000)
    piCollector ! Request()
  }
}
