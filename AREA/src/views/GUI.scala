package views

import swingGuiTest.AreaFrame
import controllers.GameController

object GUI {
	def main(args:Array[String]) {
	  val controller = new GameController
	  val gui = new AreaFrame(controller)
	  //gui.open()
	}
}