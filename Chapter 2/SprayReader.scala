import spray.json._
import DefaultJsonProtocol._

case class Planet(name: String, satellites: List[String], mass: Float)
case class Planets(gasGiants: List[Planet], rockyPlanets: List[Planet])

object PlanetsJsonProtocol extends DefaultJsonProtocol {
  implicit val planetFormat = jsonFormat(Planet, "name", "satellites", "mass")
  implicit val planetsFormat = jsonFormat(Planets, "gasGiants", "rockyPlanets")
}

import PlanetsJsonProtocol.{planetFormat, planetsFormat}

object SprayReader {
   def main(args: Array[String]) {
     val rawText = scala.io.Source.fromFile("planets.json").mkString
     val jsonAst = rawText.parseJson
     val planets = jsonAst.convertTo[Planets]
     println(planets.gasGiants(0).name)
     println(planets.rockyPlanets(1).satellites)
   }
} 
