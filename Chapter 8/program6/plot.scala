import scala.math._
import scalax.chart.api._
import scalax.chart.XYChart

object ScalaPlotting {
  def trig_data(fn: Double => Double) = {
    for (i <- 0 until 20) yield {
      val x = -Pi + 2.0 * Pi * (i / 20.0)
      val y = fn(x)
      (x, y)
    }
  }

  def main(args: Array[String]) { 
    val sin_data = trig_data(sin)
    val cos_data = trig_data(cos)
    val data = List("sin" -> sin_data, "cos" -> cos_data)
    val chart = XYLineChart(data, title="Trigonometry")
    chart.plot.getDomainAxis().setLabel("x")
    chart.plot.getRangeAxis().setLabel("y")
    chart.show()
  }
}

