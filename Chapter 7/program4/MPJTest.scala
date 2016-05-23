import mpi._

object MPJTest {
  def main(args: Array[String]) {
    MPI.Init(args)
    val me: Int = MPI.COMM_WORLD.Rank()
    val size: Int = MPI.COMM_WORLD.Size()
    if (me == 0) {
      val requests = for (i <- 0 until 10) yield {
        val buf = Array(i * i)
        MPI.COMM_WORLD.Isend(buf, 0, 1, MPI.INT, 1, 0)
      }
    } else if (me == 1) {
      for (i <- 0 until 10) {
        Thread.sleep(1000)
        val buf = Array[Int](0)
        val request = MPI.COMM_WORLD.Irecv(buf, 0, 1, MPI.INT, 0, 0)
        request.Wait()
        println("RECEIVED: " + buf(0))
      }
    }
    MPI.Finalize()
  }
}
