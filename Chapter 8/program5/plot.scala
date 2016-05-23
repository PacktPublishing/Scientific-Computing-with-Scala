import org.jfree.chart._
import org.jfree.data.statistics._
import scala.collection.JavaConverters._
import scala.util.Random

object ScalaPlotting {
  def getSeries(mean: Double, stddev: Double): List[Double] = {
    List.tabulate(20) {
      (i: Int) => Random.nextGaussian() * stddev + mean
    }
  }

  def main(args: Array[String]) { 
    val dataset = new DefaultBoxAndWhiskerCategoryDataset();
    val series1 = getSeries(20.0, 50.0)
    val series2 = getSeries(-10.0, 20.0)
    val series3 = getSeries(10.0, 40.0)
    val series4 = getSeries(0.0, 30.0)
    dataset.add(series1.asJava, "1", "1")
    dataset.add(series2.asJava, "2", "2")
    dataset.add(series3.asJava, "3", "3")
    dataset.add(series4.asJava, "4", "4")
    val frame = new ChartFrame("Experimental Data",
      ChartFactory.createBoxAndWhiskerChart(
        "Experimental Data",
        "Experiment No.", "Value",
        dataset, false))
    frame.pack()
    frame.setVisible(true)
  }
}
