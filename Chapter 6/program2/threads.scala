import java.util.concurrent.Callable
import scala.util.Random

object Accumulator {
  var c = 0.0
  def inc() {
    this.synchronized {
      c = c + 1.0
    }
  }
}

class SamplingThread extends Runnable {
  def run() {
    for (i <- 0 until 500000000) {
      val x = Random.nextDouble
      val y = Random.nextDouble
      if (x * x + y * y < 1.0) {
        Accumulator.inc()
      }
    }
  }
}

object PiParallel {
  def main(args: Array[String]) {
    var c = 0.0
    val threads = for (i <- 0 until 2) yield { new Thread(new SamplingThread) }
    threads.foreach { (thread: Thread) => thread.start }
    threads.foreach { (thread: Thread) => thread.join }
    println(Accumulator.c / (500000000 * 2))
  }
}
