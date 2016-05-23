import org.jfree.chart._
import org.jfree.data.xy._
import scala.math._
import scala.collection.mutable.{MutableList, Map}
import java.io.{FileReader, BufferedReader}

object ParallelCoordinates {
  def readCSVFile(filename: String): Map[String, MutableList[String]] = {
    val file = new FileReader(filename)
    val reader = new BufferedReader(file)
    val csvdata: Map[String, MutableList[String]] = Map()
    try {
      val alldata = new MutableList[Array[String]]
      var line:String = null
      while ({line = reader.readLine(); line} != null) {
        if (line.length != 0) {
          val delimiter: String = ","
          var splitline: Array[String] = line.split(delimiter).map(_.trim)
          alldata += splitline
        }
      }
      val labels = MutableList("sepal length", "sepal width",
        "petal length", "petal width", "class")
      val labelled = labels.zipWithIndex.map {
        case (label, index) => label -> alldata.map(x => x(index))
      }
      for (pair <- labelled) {
        csvdata += pair
      }
    } finally {
      reader.close()
    }
    csvdata
  }

  def main(args: Array[String]) {
    val data = readCSVFile("iris.csv")
    val dataset = new DefaultXYDataset
    for (i <- 0 until data("sepal length").size) {
      val x = Array(0.0, 1.0, 2.0, 3.0)
      val y1 = data("sepal length")(i).toDouble
      val y2 = data("sepal width")(i).toDouble
      val y3 = data("petal length")(i).toDouble
      val y4 = data("petal width")(i).toDouble
      val y = Array(y1, y2, y3, y4)
      val cls = data("class")(i)
      dataset.addSeries(cls + i, Array(x, y))
    }
    val frame = new ChartFrame("Parallel Coordinates",
      ChartFactory.createXYLineChart("Parallel Coordinates", "x", "y",
      dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL,
      false, false, false))
    frame.pack()
    frame.setVisible(true)
  }
}
