import nl.typeset.sonofjson._

object SonOfJson {
  def main(args: Array[String]) {
    val rawText = scala.io.Source.fromFile("planets.json").mkString
    val planets = parse(rawText)
    println(planets)
  }
} 
