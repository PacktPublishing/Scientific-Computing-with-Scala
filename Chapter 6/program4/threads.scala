import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef
import scala.util.Random

class PiGenerator(collector: ActorRef) extends Actor {
  def receive = {
    case "request" => {
      val x = Random.nextDouble
      val y = Random.nextDouble
      if (x * x + y * y < 1.0) {
        collector ! "true"
      } else {
        collector ! "false"
      }
    }
  }
}

class PiCollector extends Actor {
  var counter: Double = 0
  var counterTrue: Double = 0
  def receive = {
    case "true" => 
      counter = counter + 1
      counterTrue = counterTrue + 1
    case "false" =>
      counter = counter + 1
    case "result" =>
      println(4.0 * counterTrue / counter)
  }
}

object PiAkka {
  def main(args: Array[String]) {
    val system = ActorSystem("PiSystem")
    val piCollector = system.actorOf(Props[PiCollector], name = "picollector")
    val piGenerators = for (i <- 0 until 4) yield {
      system.actorOf(Props(new PiGenerator(piCollector)), name = "pigenerator" + i)
    }
    for (i <- 0 until 1000000) {
      for (generator <- piGenerators) {
        generator ! "request"
      }
    }
    piCollector ! "result"
  }
}
