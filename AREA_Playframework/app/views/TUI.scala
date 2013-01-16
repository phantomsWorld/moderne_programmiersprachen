package views

import controllers._
import model.Util

object TUI {
	def main(args:Array[String]) {
		val controller = new GameController
	
	  /*val gui = new AreaFrame(controller)
	  gui.open()*/
	  
		var input = ""
		var color = ""
	    
	    while (true) {
	      println(controller.toString("Text"))
	      
	      println("Enter command: q-Quit  'c *'-Change Color(*: new color)\n" +
	      		  "               'n'-New game  'rs * *'-resize Field(width, height)\n" +
	      		  "				  'load *'-Load(Filename)  'save *'-Save(Filename)")
	      input = readLine()
	      input match {
	        //case "q" => return
	        case "r" => controller.refreshField
	        case "n" => controller.refreshField
	        //case "sw" => controller.switchPlayer
	        case _ => {
	          input.toList.filter(c => c != ' ').map(c => c.toString) match{
	            case "c" :: color :: Nil => color match {
	              case "b" | "g" | "r" | "v" | "y" => controller.changeColor(Util.color(color).asInstanceOf[Int])
	              case _   => println("Invalid color. Please enter a correct one!")
	            }
	            case _ => {
	              var commandInput = input.toString().split(" ")
	              commandInput(0) match{
	                case "rs" => if(commandInput.size == 3) controller.resizeField(commandInput(1).toInt, commandInput(2).toInt) else println("Incorrect count of parameters!")
	                case "save" => controller.saveGame(commandInput(1))
	                case "load" => {
	                  if(commandInput.size != 2) println("Incorrect count of parameters!")
	                  else if(commandInput(1).contains(".xml")) Util.loadGame(commandInput(1))
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