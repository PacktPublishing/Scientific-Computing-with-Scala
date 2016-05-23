import scalax.chart.api._
import scalax.chart.XYChart
import scala.util.Random
import org.jfree.data.statistics._

object ScalaPlotting {
  def main(args: Array[String]) { 
    val x: Array[Double] = Array.tabulate(1000) {
      (i: Int) => Random.nextGaussian()
    }
    val dataset = new HistogramDataset();
    dataset.setType(HistogramType.FREQUENCY);
    dataset.addSeries("Histogram", x, 11);
    val chart = XYBarChart(dataset, title="Histogram", legend=false)
    chart.plot.getDomainAxis().setLabel("Value")
    chart.plot.getRangeAxis().setLabel("Frequency")
    chart.show()
  }
}

