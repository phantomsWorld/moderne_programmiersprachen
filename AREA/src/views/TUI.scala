package views

import controllers._
import model.Util

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
	              case "b" | "g" | "r" | "v" | "y" => controller.changeColor((new Util).color(color).asInstanceOf[Int])
	              case _   => println("Invalid color. Please enter a correct one!")
	            }
	            case _ => {
	              var commandInput = input.toString().split(" ")
	              commandInput(0) match{
	                case "rs" => if(commandInput.size == 3) controller = new Fieldcontroller(commandInput(1).toInt, commandInput(2).toInt) else println("Incorrect count of parameters!")
	                case "save" => controller.saveXML(commandInput(1))
	                case "load" => {
	                  if(commandInput.size != 2) println("Incorrect count of parameters!")
	                  else if(commandInput(1).contains(".xml")) (new Util).loadGame(commandInput(1))
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