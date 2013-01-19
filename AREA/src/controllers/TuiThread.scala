package controllers

import model.Util
import scala.swing.Reactor

class TuiThread(controller: GameController) extends Runnable {
  val reactor = new Object with Reactor
  reactor.listenTo(controller)
  reactor.reactions += {
    case Util.FieldUpdate => listGame
  }

  val newGameInput = "\n  Bitte bestätigen Sie dies mit dem Start eines neuen Spiels mit 'n'\n"
  def listGame = {
    println(controller.toString("Text"))

    println("Enter command: q-Quit  'c *'-Change Color(*: new color)\n" +
      "               'n'-New game  'rs * *'-resize Field stepwidth=4(width=4-20, height=4-20)\n" +
      "				  'load *'-Load(DirPath/Filename.xml)  'save *'-Save(Filename)\n" +
      "               'gameList'-List Games  'rec *'-Change Recursion(1-6)")

    if (controller.readPossessions(0) > 50.0) println("\n  Gratulation! Sie haben gewonnen!" + newGameInput)
    if (controller.readPossessions(1) > 50.0) println("\n  Schade, Sie haben leider verloren." + newGameInput)
    if (controller.readPossessions(0) == 50.0 && controller.readPossessions(1) == 50.0) {
      println("\n  Unentschieden! Versuchen Sie es doch nochmal." + newGameInput)
    }
  }

  def run = { listGame }
}
