package views

import controllers._
import model.Util
import swingGui.AreaFrame

object MAIN {
  def main(args: Array[String]) {
    val controller = new GameController
    new AreaFrame(controller)
    new AreaFrame(controller)
    new TUI(controller)
  }
}
