import breeze.linalg._
import scala.math.{sin, Pi}
import breeze.signal._
import breeze.plot._

object FFTDemo {
  def calculateSignal(index: Int, size: Int): Double = {
    val fIndex = index.toDouble
    val fSize = size.toDouble
    (0.1 * sin(fIndex / fSize * 5.0 * 2.0 * Pi) +
     0.4 * sin(fIndex / fSize * 10.0 * 2.0 * Pi) +
     0.5 * sin(fIndex / fSize * 50.0 * 2.0 * Pi))
  }

  def main(args: Array[String]) {
    val signal = DenseVector.tabulate(512){i => calculateSignal(i, 512)}
    val f = Figure()
    val p = f.subplot(0)
    val x = linspace(0.0, 512.0, 512)
    p += plot(x, signal)
    p.xlabel = "sample"
    p.ylabel = "value"
    f.saveas("signal.png")
  }
}
