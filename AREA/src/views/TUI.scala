package views

import controllers._
import model.Util

// Dialog because of the event-Listener
class TUI(controller:GameController) {
  
	val outputThread = new TuiThread(controller)
	new Thread(outputThread).start
  
	new Thread(new Runnable {
	  var input = ""
	  
	  def run() {
	    while (true) {
	      
	      input = readLine()
	      input match {
	        case "q" => return
	        case "n" => controller.refreshField
	        case _ => {
	          input.toList.filter(c => c != ' ').map(c => c.toString) match{
	            case "c" :: color :: Nil => color match {
	              case "b" | "g" | "r" | "v" | "y" => controller.changeColor(color)
	              case _   => println("Invalid color. Please enter a correct one!")
	            }
	            case _ => {
	              var commandInput = input.toString().split(" ")
	              commandInput(0) match{
	              	case "gameList" => controller.availableFiles("games/","Text").split(";").foreach(fileName => println(fileName+"\n"))
	              	case "rec" => if(commandInput.size > 2 || commandInput(1).toInt > 6 || commandInput(1).toInt < 1) println("Incorrect input. Check structure or recursion depth.") else controller.resetBotRecursion(commandInput(1).toInt)
	                case "rs" => if(commandInput.size == 3) controller.resizeField(commandInput(1).toInt, commandInput(2).toInt) else println("Incorrect count of parameters!")
	                case "save" => controller.saveGame(commandInput(1))
	                case "load" => {
	                  if(commandInput.size != 2) println("Incorrect count of parameters!")
	                  else if(commandInput(1).contains(".xml")) controller.loadGame(commandInput(1))
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
	}).run
}