package views

import controllers._
import model.Util
import swingGuiTest.AreaFrame

object MAIN{
	def main(args:Array[String]) {
	  val controller = new GameController
	  
	  new AreaFrame(controller)
	  new AreaFrame(controller)
	  new AreaFrame(controller)
	  
	  new TUI(controller)
	}
}