import mpi._

object MPJTest {
  def main(args: Array[String]) {
    MPI.Init(args)
    val me: Int = MPI.COMM_WORLD.Rank()
    val size: Int = MPI.COMM_WORLD.Size()
    System.out.println("Hello, World, I'm <" + me.toString + ">")
    MPI.Finalize()
  }
}
