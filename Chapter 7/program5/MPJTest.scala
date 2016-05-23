import mpi._

object MPJTest {
  def main(args: Array[String]) {
    MPI.Init(args)
    val me: Int = MPI.COMM_WORLD.Rank()
    val size: Int = MPI.COMM_WORLD.Size()
    var gdata: Array[Int] = Array.fill(8){0}
    var ldata: Array[Int] = Array(0)
    if (me == 0) {
      for (i <- 0 until 8) {
        gdata(i) = i * i * i
      }
    }
    MPI.COMM_WORLD.Scatter(gdata, 0, 1, MPI.INT, ldata, 0, 1, MPI.INT, 0)
    println("I am <" + me + "> and I have received " + ldata(0))
    ldata(0) = ldata(0) * 2
    MPI.COMM_WORLD.Gather(ldata, 0, 1, MPI.INT, gdata, 0, 1, MPI.INT, 0)
    if (me == 0) {
      println("I am MASTER, I have received " + gdata.mkString(" "))
    }
    MPI.Finalize()
  }
}
