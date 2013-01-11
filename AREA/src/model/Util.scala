package model
import java.io.File
import controllers.Fieldcontroller

class Util {
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
	
	// convert color by pattern matching
	def color(value:Any) = value match {
	  case c:String => colorsStringToInt(c)
	  case c:Int 	=> colorsIntToString(c)
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
}