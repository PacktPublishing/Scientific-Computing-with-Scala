import org.jfree.chart._
import org.jfree.data.statistics._
import scala.util.Random

object ScalaPlotting {
  def main(args: Array[String]) { 
    val x: Array[Double] = Array.tabulate(1000) {
      (i: Int) => Random.nextGaussian()
    }
    val dataset = new HistogramDataset();
    dataset.setType(HistogramType.FREQUENCY);
    dataset.addSeries("Histogram", x, 11);
    val frame = new ChartFrame("Histogram",
      ChartFactory.createHistogram("Histogram", "value", "frequency",
        dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL, 
        true, false, true))
    frame.pack()
    frame.setVisible(true)
  }
}
