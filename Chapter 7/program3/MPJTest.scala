import mpi._
import scala.pickling.Defaults._
import scala.pickling.json._

case class ArbitraryObject(a: Array[Double], b: Array[Int], c: String)

object MPJTest {
  def main(args: Array[String]) {
    MPI.Init(args)
    val me: Int = MPI.COMM_WORLD.Rank()
    val size: Int = MPI.COMM_WORLD.Size()
    if (me == 0) {
      val obj = ArbitraryObject(Array(1.0, 2.0, 3.0), Array(1, 2, 3), "Hello")
      val pkl = obj.pickle.value.toCharArray
      MPI.COMM_WORLD.Send(pkl, 0, pkl.size, MPI.CHAR, 1, 0)
    } else if (me == 1) {
      val buf = new Array[Char](1000)
      MPI.COMM_WORLD.Recv(buf, 0, 1000, MPI.CHAR, 0, 0)
      val msg = buf.mkString
      val obj = msg.unpickle[ArbitraryObject]
      println(msg)
      println(obj.c)
    }
    MPI.Finalize()
  }
}
