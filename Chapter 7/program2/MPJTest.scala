import mpi._
import scala.util.Random

object MPJTest {
  def main(args: Array[String]) {
    MPI.Init(args)
    val me: Int = MPI.COMM_WORLD.Rank()
    val size: Int = MPI.COMM_WORLD.Size()
    if (me == 0) {
      for (i <- 1 until size) {
        val buf = Array(Random.nextInt(100))
        MPI.COMM_WORLD.Send(buf, 0, 1, MPI.INT, i, 0)
        println("MASTER: Dear <" + i + "> please do work on " + buf(0))
      }
      for (i <- 1 until size) {
        val buf = Array(0)
        MPI.COMM_WORLD.Recv(buf, 0, 1, MPI.INT, i, 0)
        println("MASTER: Dear <" + i + "> thanks for the reply, which was " + buf(0))
      }
    } else {
      val buf = Array(0)
      MPI.COMM_WORLD.Recv(buf, 0, 1, MPI.INT, 0, 0)
      println("<" + me + ">: " + "Understood, doing work on " + buf(0))
      buf(0) = buf(0) * buf(0)
      MPI.COMM_WORLD.Send(buf, 0, 1, MPI.INT, 0, 0)
      println("<" + me + ">: " + "Reported back")
    }
    MPI.Finalize()
  }
}
