package model
import java.io.File
import controllers.Fieldcontroller
import java.awt.Color
import java.net.Socket

object Util {
	// define mapper for color from String to Int
	val colorsStringToInt = Map(  "r" -> 0,
			  					  "g" -> 1,
			  					  "y" -> 2,
			  					  "b" -> 3,
			  					  "v" -> 4 )
	// define mapper for color from Int to String
	val colorsIntToString = Map(  0 -> "r",
			  					  1 -> "g",
			  					  2 -> "y",
			  					  3 -> "b",
			  					  4 -> "v" )
	val colorIntToSwingColor = Map(	0 -> Color.RED,
									1 -> Color.GREEN,
									2 -> Color.YELLOW,
									3 -> Color.BLUE,
									4 -> Color.MAGENTA )
	
	// convert color by pattern matching
	def color(value:Any,swing:Boolean=false) = value match {
	  case c:String => colorsStringToInt(c)
	  case c:Int 	=> swing match{
	    case false 	=> colorsIntToString(c)
	    case _		=> colorIntToSwingColor(c)
	  }
	  case _ => 0
	}
	
	// read existing game-files
	def listGames(root:String,output:String="Text") = {
	  var result = ""
	  tree(new File(root)).filter(f => f.isFile && f.getName.endsWith(".xml")).foreach(file => result += listFilename(file.getName(),output))
	  result
	}
	
	def listFilename(filename:String,output:String) = output match {
		case "HTML" => "<li class='ui-widget-content ui-corner-all'>"+filename+"</li>"
		case _ 		=> filename+"\n"
	}
	
	def tree(root: File, skipHidden: Boolean = false): Stream[File] = 
	  if (!root.exists || (skipHidden && root.isHidden)) Stream.empty 
	  else root #:: (
	    root.listFiles match {
	      case null => Stream.empty
	      case files => files.toStream.flatMap(tree(_, skipHidden))
	  })
	  
	// load file of given name
	def loadGame(fileName:String) = {
	    val data = scala.xml.XML.loadFile(fileName)
	    println("Filename: "+fileName)
	    val result = new Fieldcontroller((data \\ "player").text.toInt, (data \\ "height").text.toInt, (data \\ "width").text.toInt,true)
	    result.parseXML(data)
	    result
	}
	
	def openPort(port:Int=9001):Int = {
	  try {
	    val socket = new Socket("localhost",port)
	    socket.close()
	    openPort(port+1)
	  } catch {
	    case e:Exception => port
	  }
	}
}