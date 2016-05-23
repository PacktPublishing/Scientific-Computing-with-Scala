import scala.collection.mutable.{MutableList, Map}
import scala.math._
import org.jfree.chart._
import org.jfree.data.xy._
import org.jfree.data.statistics._
import java.io.{FileReader, BufferedReader}
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JPanel

object ScatterPlotMatrix {
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
    val frame = new JFrame("Scatter Plot Matrix")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setLayout(new GridLayout(4, 4))
    val attributes = List("sepal length", "sepal width", 
      "petal length", "petal width")
    val classes = List("Iris-setosa", "Iris-versicolor", "Iris-virginica")
    for ((a1, i) <- attributes.zipWithIndex) {
      for ((a2, j) <- attributes.zipWithIndex) {
        if (a1 == a2) {
          val dataset = new HistogramDataset();
          dataset.setType(HistogramType.RELATIVE_FREQUENCY);
          val xs = (for (x <- data(a1)) yield { x.toDouble }).toArray
          dataset.addSeries(a1, xs, 11);
          val chart = ChartFactory.createHistogram(null, a1, "frequency",
            dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL,
            false, false, false)
          frame.add(new ChartPanel(chart, 200, 200, 200, 200, 200, 200,
            false, true, true, true, true, true))
        } else {
          val dataset = new DefaultXYDataset
          for (cls <- classes) {
            val xs = (for ((x, index) <- data(a1).zipWithIndex
              if data("class")(index) == cls)
            yield { x.toDouble }).toArray
            val ys = (for ((y, index) <- data(a2).zipWithIndex
              if data("class")(index) == cls)
            yield { y.toDouble }).toArray
            dataset.addSeries(cls, Array(xs, ys))
          }
          val chart = ChartFactory.createScatterPlot(null, 
            a1, a2, dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL, 
            false, false, false)
          frame.add(new ChartPanel(chart, 200, 200, 200, 200, 200, 200,
            false, true, true, true, true, true))
        }
      }
    }
    frame.pack()
    frame.setVisible(true)
  }
}
