import java.sql.DriverManager
import java.sql.Connection

object JdbcSqlite {
  def main(args: Array[String]) {
    var c: Connection = null
    try {
      Class.forName("org.sqlite.JDBC")
      c = DriverManager.getConnection("jdbc:sqlite:planets.sqlite")
    } catch {
      case e: Throwable => e.printStackTrace
    }
    c.close()
  }
} 
