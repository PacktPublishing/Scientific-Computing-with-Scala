import spray.json._
import DefaultJsonProtocol._
import java.io._

case class Person(name: String, age: Int, occupation: String)

object PersonJsonProtocol extends DefaultJsonProtocol {
  implicit val personFormat = jsonFormat(Person, "name", "age", "occupation")
}

import PersonJsonProtocol._

object SprayWriter {
  def main(args: Array[String]) {
    val bob = Person("Bob", 30, "Baker")
    val pw = new PrintWriter(new File("person.json"))
    pw.write(bob.toJson.prettyPrint)
    pw.close()
  }
} 
