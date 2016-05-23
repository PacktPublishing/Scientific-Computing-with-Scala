import scala.collection.mutable.{MutableList, Map}
import java.io.{FileReader, BufferedReader}

object CSVReader {
 def main(args: Array[String]) {
   val file = new FileReader("iris.csv")
   val reader = new BufferedReader(file)
   try {
     val alldata = new MutableList[Array[String]]
     var line:String = null
     while ({line = reader.readLine(); line} != null) {
       if (line.length != 0) {
         val delimiter: String = ","
         var splitline: Array[String] = line.split(delimiter).map(_.trim)
         alldata += splitline
       }
     }
     val labels = MutableList("sepal length", "sepal width",
       "petal length", "petal width", "class")
     val labelled = labels.zipWithIndex.map {
       case (label, index) => label -> alldata.map(x => x(index))
     }
     val csvdata: Map[String, MutableList[String]] = Map()
     for (pair <- labelled) {
       csvdata += pair                                           
     }
   }
   finally {
     reader.close()
   }
 }
} 
