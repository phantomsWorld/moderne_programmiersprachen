package view

import controller._

object TUI {
	def main(args:Array[String]) {
		var input = ""
		var color = ""
		var controller:Fieldcontroller = new Fieldcontroller
	    
	    while (true) {
	      println(controller.field.toString)
	      println("Player on Turn: "+controller.playerOnTurn)
	      println(controller.toString)
	      println("Enter command: q-Quit  sw-Switch Player  'c *'-Change Color(*: new color)  r-Refresh Field \n" +
	      		  "               'rs * *'-resize Field (width, height)  'save *'-Save (Filename)")
	      input = readLine()
	      input match {
	        case "q" => return
	        case "r" => controller.refreshField
	        case "sw" => controller.switchPlayer
	        case _ => {
	          input.toList.filter(c => c != ' ').map(c => c.toString) match{
	            case "c" :: color :: Nil => color match {
	              case "b" => controller.changeColor(3)
	              case "g" => controller.changeColor(1)
	              case "r" => controller.changeColor(0)
	              case "v" => controller.changeColor(4)
	              case "y" => controller.changeColor(2)
	              case _   => println("Invalid color. Please enter a correct one!")
	            }
	            //case "rs" :: width :: height :: Nil => println("test")
	            case _ => {
	              var commandInput = input.toString().split(" ")
	              commandInput(0) match{
	                case "rs" => controller = new Fieldcontroller(commandInput(1).toInt, commandInput(2).toInt)
	                case "save" => controller.saveXML(commandInput(1))
	                case "load" => {
	                  if(commandInput(1).contains(".xml")){
	                    val data = scala.xml.XML.loadFile(commandInput(1))
	                    controller = new Fieldcontroller((data \\ "player").text.toInt, (data \\ "height").text.toInt, (data \\ "width").text.toInt)
	                    controller.parseXML(data)
	                  }
	                  else println("Incorrect Filename")
	                }
	                case _ => println("Invalid command. Please enter a correct one!")
	              }
	            }
	          }
	        }
	      }
	    }
	}
}