import org.jfree.chart._
import org.jfree.data.xy._
import java.io.{FileReader, BufferedReader}
import breeze.linalg._
import breeze.optimize._
import scala.collection.mutable.{MutableList, Map}
import scala.math._

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

  def distance(x1: DenseVector[Double], x2: DenseVector[Double]): Double = {
    sqrt(sum((x1 - x2) :^ 2.0))
  }

  def d2(xs: DenseVector[Double], i: Int, j: Int, n: Int): Double = {
    distance(DenseVector(xs(i), xs(n + i)),
      DenseVector(xs(j), xs(n + j)))
  }

  def d4(xs: DenseVector[Double], i: Int, j: Int, n: Int): Double = {
    distance(DenseVector(xs(i), xs(n + i), xs(n * 2 + i), xs(n * 3 + i)),
      DenseVector(xs(j), xs(n + j), xs(n * 2 + j), xs(n * 3 + j)))
  }

  def createF(xsData: DenseVector[Double]): DenseVector[Double] => Double = {
    val n = xsData.size / 4
    val c = sum(for (i <- 0 until n; j <- 0 until n if i < j) yield {
      d4(xsData, i, j, n)
    })
    xs: DenseVector[Double] => {
      val sequence = for (i <- 0 until n; j <- 0 until n if i < j) yield {
        val d2ij = d2(xs, i, j, n)
        val d4ij = d4(xsData, i, j, n)
        if (d4ij != 0.0) {
          (d4ij - d2ij) * (d4ij - d2ij) / d4ij
        } else {
          1000.0
        }
      }
      (1.0 / c) * sum(sequence)
    }
  }

  def main(args: Array[String]) {
    val data = readCSVFile("iris.csv")
    val xs1 = (for (x <- data("sepal length")) 
      yield { x.toDouble }).toArray
    val xs2 = (for (x <- data("sepal width")) 
      yield { x.toDouble }).toArray
    val xs3 = (for (x <- data("petal length")) 
      yield { x.toDouble }).toArray
    val xs4 = (for (x <- data("petal width")) 
      yield { x.toDouble }).toArray
    val n = xs1.size
    val xsData: DenseVector[Double] = 
      new DenseVector((xs1 ++ xs2 ++ xs3 ++ xs4).toArray)
    val f = createF(xsData)
    val df = new ApproximateGradientFunction(f)
    val lbfgs = new LBFGS[DenseVector[Double]](maxIter=100, m=3)
    val optimum = lbfgs.minimize(df, DenseVector.rand(n * 2))
    val x = optimum.slice(0, 150).toArray
    val y = optimum.slice(150, 300).toArray
    val dataset = new DefaultXYDataset
    dataset.addSeries("Iris Setosa", 
      Array(optimum.slice(0, 50).toArray,
        optimum.slice(150, 200).toArray))
    dataset.addSeries("Iris Versicolor", 
      Array(optimum.slice(50, 100).toArray,
        optimum.slice(200, 250).toArray))
    dataset.addSeries("Iris Virginica", 
      Array(optimum.slice(100, 150).toArray, 
        optimum.slice(250, 300).toArray))
    val frame = new ChartFrame("Sammon Projection of Iris Data",
      ChartFactory.createScatterPlot("Sammon Projection of Iris Data", "x", "y",
      dataset, org.jfree.chart.plot.PlotOrientation.VERTICAL,
      true, true, false))
    frame.pack()
    frame.setVisible(true)
  }
}
