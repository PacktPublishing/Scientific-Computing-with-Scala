import scalax.chart.api._
import org.jfree.data.category._

object ScalaPlotting {
  def main(args: Array[String]) { 
    val dataset = new DefaultCategoryDataset();
    dataset.setValue(6, "Q1", "Elbonia");
    dataset.setValue(7, "Q2", "Elbonia");
    dataset.setValue(8, "Q3", "Elbonia");
    dataset.setValue(5, "Q4", "Elbonia");
    dataset.setValue(5, "Q1", "Latveria");
    dataset.setValue(8, "Q2", "Latveria");
    dataset.setValue(7, "Q3", "Latveria");
    dataset.setValue(6, "Q4", "Latveria");
    val chart = BarChart(dataset, title="GDP Growth")
    chart.plot.getDomainAxis().setLabel("Country")
    chart.plot.getRangeAxis().setLabel("GDP Growth (%)")
    chart.show()
  }
}
