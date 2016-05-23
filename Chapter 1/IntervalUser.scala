import org.orbitfold.iafs.Interval

object IntervalUser {
  def main(args: Array[String]) = {
    val interval1 = new Interval(-0.5, 0.5)
    val interval2 = new Interval(0.2, 0.8)
    println(interval1 + interval2)
    println(interval1 - interval2)
    println(interval1 * interval2)
    println(interval1 / interval2)
  }
}

