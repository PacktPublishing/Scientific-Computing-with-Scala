import org.jfree.chart._
import org.jfree.data.xy._
import scala.math._

object ScalaPlotting {
  def main(args: Array[String]) { 
    val x: Array[Double] = Array.tabulate(20) {
      (i: Int) => -Pi + 2.0 * Pi * (i / 20.0)
    }
    val sin_y = x.map(sin(_))
    val cos_y = x.map(cos(_))
    val dataset = new DefaultXYDataset
    dataset.addSeries("sin", Array(x, sin_y))
    dataset.addSeries("cos", Array(x, cos_y))
    val frame = new ChartFrame("Trigonometry",
      ChartFactory.createScatterPlot("Trigonometry", "x", "y",
      dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL,
      true, false, false))
    frame.pack()
    frame.setVisible(true)
  }
}
