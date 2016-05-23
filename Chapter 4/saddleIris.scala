import org.saddle._
import org.saddle.io._
import breeze.plot._

object SaddleIris {
  def main(args: Array[String]) {
    val fs = CsvFile("iris.data")
    val data: Frame[Int, Int, String] = CsvParser.parse(fs)
    val doubleData = data.mapValues { case elt => elt.toDouble }
    val irisData = Frame("SepalLength" -> doubleData.colAt(0),
      "SepalWidth" -> doubleData.colAt(1),
      "PetalLength" -> doubleData.colAt(2),
      "PetalWidth" -> doubleData.colAt(3),
      "Class" -> doubleData.colAt(4))
    val f = Figure()
    f.width = 800
    f.height = 800
    var c = 0
    var i = 0
    var j = 0
    val columns = Vector("Sepal Length", "Sepal Width", 
      "Petal Length", "Petal Width")
    val colors = Vector("red", "green", "blue")
    val subplots = for (i <- 0 to 3; j <- 0 to 3) 
      yield { f.subplot(4, 4, i + 4 * j)  }
    for (c <- 0 to 2; i <- 0 to 3; j <- 0 to 3; if i != j) {
      val data = irisData.rfilter { case r => r.at(4).toInt == c }
      val xs = for (x <- data.colAt(i).toSeq) yield { x._2 }
      val ys = for (y <- data.colAt(j).toSeq) yield { y._2 }
      val p = subplots(i + 4 * j)
      p += plot(xs, ys, '+')
      p.xlabel = columns(i)
      p.ylabel = columns(j)
    }
    for (i <- 0 to 3) {
      val p = f.subplot(4, 4, i + 4 * i)
      val col = for (x <- irisData.colAt(i).toSeq) yield { x._2 }
      p += hist(col, 20)
      p.xlabel = columns(i)
    }
    f.saveas("matrix.png")
  }
}
