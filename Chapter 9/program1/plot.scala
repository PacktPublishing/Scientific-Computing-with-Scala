import org.jfree.chart._
import org.jfree.data.xy._
import scala.math._
import scala.collection.mutable.{MutableList, Map}
import java.io.{FileReader, BufferedReader}

object AndrewsCurve {
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

  def andrewsCurve(row: Array[Double]) = (t: Double) => {
    var result: Double = 0.0
    for ((attr, i) <- row.zipWithIndex) {
      if (i == 0) {
        result = result + row(i) / sqrt(2.0)
      } else if (i % 2 != 0) {
        result = result + row(i) * sin(((i + 1) / 2) * t)
      } else {
        result = result + row(i) * cos(((i + 1) / 2) * t)
      }
    }
    result
  }

  def main(args: Array[String]) {
    val data = readCSVFile("iris.csv")
    val x: Array[Double] = Array.tabulate(100) {
      (i: Int) => -Pi + 2.0 * Pi * (i / 100.0)
    }
    val dataset = new DefaultXYDataset
    for (i <- 0 until data("sepal length").size) {
      val x1 = data("sepal length")(i).toDouble
      val x2 = data("sepal width")(i).toDouble
      val x3 = data("petal length")(i).toDouble
      val x4 = data("petal width")(i).toDouble
      val cls = data("class")(i)
      val curve = x.map(andrewsCurve(Array(x1, x2, x3, x4)))
      dataset.addSeries(cls + i, Array(x, curve))
    }
    val frame = new ChartFrame("Andrews Curve",
      ChartFactory.createXYLineChart("Andrews Curve", "x", "y",
      dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL,
      false, false, false))
    frame.pack()
    frame.setVisible(true)
  }
}
