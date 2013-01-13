package views

import controllers.GameController
import model.Util
import scala.swing.SimpleSwingApplication
import scala.swing._

// Dialog because of the event-Listener
class TUI(controller:GameController) extends Dialog {
	listenTo(controller)
  
	val ioThread = new Thread(new Runnable {
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
	})
	
	val newGameInput = "Bitte bestätigen Sie dies mit dem Start eines neuen Spiels mit 'n'"
	def listGame = {
	  println(controller.toString("Text"))
	      
	  println("Enter command: q-Quit  'c *'-Change Color(*: new color)\n" +
	   		  "               'n'-New game  'rs * *'-resize Field(width, height)\n" +
	   		  "				  'load *'-Load(Filename)  'save *'-Save(Filename)\n" + 
	   		  "               'files'-List Games")
	  
	  if(controller.readPossessions(0)>50.0) println("Gratulation! Sie haben gewonnen!\n\n"+newGameInput)
	  if(controller.readPossessions(1)>50.0) println("Schade, Sie haben leider verloren."+newGameInput)
	  if(controller.readPossessions(0) == 50.0 && controller.readPossessions(1) == 50.0) println("Unentschieden! Versuchen Sie es doch nochmal."+newGameInput)
	}
	
	listGame
	ioThread.run
	
	reactions += {
	  case Util.FieldUpdate => {
	    println("Event erhalten")
	    listGame
	  }
    }
}