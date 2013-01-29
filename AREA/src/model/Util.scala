package model
import java.io.File
import controllers.Fieldcontroller
import java.awt.Color
import java.net.Socket
import scala.swing.event.Event

/**
 * __Object Util for helping methods__.
 *
 *
 * {{{
 *  Factory for proper mapping-functionality
 * }}}
 *
 *
 * @author Christian Gabele
 */
object Util {
	/**
	 * Simple event-object
	 */
	case object FieldUpdate extends Event
	/**
	 * Simple event-object
	 */
	case object ClosePopup extends Event
	
	/**
	 * Mapper for color from String to Int
	 */
	val colorsStringToInt = Map(  "r" -> 0,
			  					  "g" -> 1,
			  					  "y" -> 2,
			  					  "b" -> 3,
			  					  "v" -> 4 )
	/**
	 * Mapper for color from Int to String
	 */
	val colorsIntToString = Map(  0 -> "r",
			  					  1 -> "g",
			  					  2 -> "y",
			  					  3 -> "b",
			  					  4 -> "v" )
	/**
	 * Mapper for color from Int to Swing
	 */
	val colorIntToSwingColor = Map(	0 -> Color.RED,
									1 -> Color.GREEN,
									2 -> Color.YELLOW,
									3 -> Color.BLUE,
									4 -> Color.MAGENTA )
	
	/**
	 * Change a color into the needed type using pattern-matching of parameters and the mapping-variables of the object.
	 * 
	 * @param value ''Color'' of the calling instance
	 * @param swing Flag if the color should be transformed into swing-code or not. Default is ''false''.
	 * @return Any Converted color of type Int or String depending on the needed form.
	 */
	def color(value:Any,swing:Boolean=false) = value match {
	  case c:String => colorsStringToInt(c)
	  case c:Int 	=> swing match{
	    case false 	=> colorsIntToString(c)
	    case _		=> colorIntToSwingColor(c)
	  }
	  case _ => 0
	}
	
	/**
	 * Read all existing game-files contained in a root-directory.
	 * 
	 * @param root ''Root-Folder'' to find contained XML-files
	 * @param output Defines the text format of result. Default is ''Text''
	 * @return String All found files in specific text format
	 */
	def listGames(root:String,output:String="Text") = {
	  var result = ""
	  tree(new File(root)).filter(f => f.isFile && f.getName.endsWith(".xml")).foreach(file => result += listFilename(file.getName(),output))
	  result
	}
	
	/**
	 * Convert a found file into specific text format
	 * 
	 * @param filename ''Filename'' to be converted into the specific format
	 * @param output Defines the text format of result
	 * @return String Filename in HTML-code or as a normal String of the filename
	 */
	def listFilename(filename:String,output:String) = output match {
		case "HTML" => "<li class='ui-widget-content ui-corner-all'>"+filename+"</li>"
		case _ 		=> filename+"\n"
	}
	
	/**
	 * List all XML-files in an root-folder
	 * 
	 * @param root ''Root-Folder'' to find contained XML-files
	 * @param skipHidden Flag if hidden files should also be found or not. Default value is ''false''.
	 * @return Stream[File] Files found in root
	 */
	def tree(root: File, skipHidden: Boolean = false): Stream[File] = 
	  if (!root.exists || (skipHidden && root.isHidden)) Stream.empty 
	  else root #:: (
	    root.listFiles match {
	      case null => Stream.empty
	      case files => files.toStream.flatMap(tree(_, skipHidden))
	  })
	  
	/**
	 * Load an existing file.
	 * 
	 * @param  fileName ''Filename'' of an existing file
	 * @return Fieldcontroller newFieldcontroller
	 */
	def loadGame(fileName:String) = {
	    val data = scala.xml.XML.loadFile(fileName)
	    println("Filename: "+fileName)
	    val result = new Fieldcontroller((data \\ "player").text.toInt, (data \\ "height").text.toInt, (data \\ "width").text.toInt,true)
	    result.parseXML(data)
	    result
	}
}