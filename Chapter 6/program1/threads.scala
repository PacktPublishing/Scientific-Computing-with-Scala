class MessageThread(message: String) extends Runnable {
  var msg: String = message

  def run() {
    while (true) {
      println(msg)
      Thread.sleep(1000)
    }
  }
}

object Threading {
  def main(args: Array[String]) {
    val t1 = new Thread(new MessageThread("hello"))
    val t2 = new Thread(new MessageThread("world"))
    val t3 = new Thread(new MessageThread("!"))
    t1.start
    t2.start
    t3.start
  }
}
