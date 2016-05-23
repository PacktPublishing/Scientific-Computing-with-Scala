import breeze.linalg._
import scala.math.{pow}
import breeze.optimize._

object Optimization {
  val fn = new DiffFunction[DenseVector[Double]] {
    def calculate(x: DenseVector[Double]) = {
      (pow(x(0) + 1.0, 2.0) + 
       pow(x(1) - 2.0, 2.0) + 
       pow(x(2) + 4.0, 2.0),
       DenseVector(2.0 * (x(0) + 1),
         2.0 * (x(1) - 2.0),
         2.0 * (x(2) + 4.0)))
    }
  }

  def main(args: Array[String]) {
    val lbfgs = new LBFGS[DenseVector[Double]](maxIter=100, 
      m=10, tolerance=0.001)
    val solution = lbfgs.minimize(fn, DenseVector(0.0, 0.0, 0.0))
    println(solution)
  }
}
 
