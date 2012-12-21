
package view

import controller._

object TUI {
	def main(args:Array[String]) {
		var input = ""
		var color = ""
		var controller:Fieldcontroller = new Fieldcontroller
	    
	    while (true) {
	      println("Enter command: q-Quit  sw-Switch Player  'c *'-Change Color(*: new color)  r-Refresh field \n" +
	      		  "               'new *'-New game('bot'/'server + port'/'client + ip + port') \n" +
	      		  "				  'rs * *'-resize Field(width, height)  'load *'-Load(Filename)  'save *'-Save(Filename)")
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
	                case "rs" => if(commandInput.size == 3) controller = new Fieldcontroller(commandInput(1).toInt, commandInput(2).toInt) else println("Incorrect count of parameters!")
	                case "save" => controller.saveXML(commandInput(1))
	                case "load" => {
	                  if(commandInput.size != 2) println("Incorrect count of parameters!")
	                  else if(commandInput(1).contains(".xml")){
	                    val data = scala.xml.XML.loadFile(commandInput(1))
	                    controller = new Fieldcontroller((data \\ "player").text.toInt, (data \\ "height").text.toInt, (data \\ "width").text.toInt,true)
	                    controller.parseXML(data)
	                  }
	                  else println("Incorrect Filename")
	                }
	                case "new" => {
	                  if(commandInput.size != 2) println("Incorrect count of parameters!")
	                  else
		                  commandInput(1) match {
		                    case "bot" => controller = new Fieldcontroller(true)
		                    /*case "server" => 
		                    case "client" => */
		                  }
	                }
	                case _ => println("Invalid command. Please enter a correct one!")
	              }
	            }
	          }
	        }
	      }
	      
	      println(controller.toString)
	    }
	}
}