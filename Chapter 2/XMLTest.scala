class Planet {
  val name: String
  val satellites: List[String]
  val mass: Double

  def toXML =
    <planet>
      <name>{name}</name>
      <satellites>{ satellites.map(satellite =>
        <satellite>
          { satellite }
        </satellite>) }
      </satellites>
      <mass>{mass}</mass>
    </planet>
}

class Planets {
  val gasGiants: List[Planet]
  val rockyPlanets: List[Planet]
  
  def toXML =
    <planets>
      <gasGiants>
        { gasGiants.map(planet =>
          planet.toXML) }
      </gasGiants>
      <rockyPlanets>
        { rockyPlanets.map(planet =>
          planet.toXML) }
      </rockyPlanets>
    </planets>
}

object XMLTest {
  def main(args: Array[String]) {
    val jupiter = new Planet {
      val name = "Jupiter"
      val satellites = List("Ganymede", "Callisto",
        "Io", "Europa")
      val mass = 1.8986e+27
    }
    val saturn = new Planet {
      val name = "Saturn"
      val satellites = List("Mimas", "Enceladus",
        "Tethys", "Dione")
      val mass = 5.683e+26
    }
    val earth = new Planet {
      val name = "Earth"
      val satellites = List("Moon")
      val mass = 5.972e+24
    }
    val mars = new Planet {
      val name = "Mars"
      val satellites = List("Phobos", "Deimos")
      val mass = 6.39e+23
    }
    val planets = new Planets {
      val gasGiants = List(jupiter, saturn)
      val rockyPlanets = List(earth, mars)
    }
    println(planets.toXML)
  }
} 
