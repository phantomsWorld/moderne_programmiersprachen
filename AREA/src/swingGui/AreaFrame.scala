package swingGui;

import controllers._
import model.Util
import scala.swing._
import javax.swing.ImageIcon
import java.net.URL
import java.awt.Color
import scala.util.Random
import java.awt.Dimension
import javax.swing.UIManager
import javax.swing.BorderFactory
import javax.swing.border._
import javax.swing.JProgressBar
import javax.swing.SwingConstants

class AreaFrame(controller: GameController, fileRootDir: String = "games/") extends MainFrame {

  listenTo(controller)

  title = "Scala Area"
  //iconImage = toolkit.getImage("images/AREA_favicon.png") //Not available in OSX
  resizable = false

  val winPopup = new ResultPopup("Gratulation! Sie haben gewonnen!", controller)
  val loosePopup = new ResultPopup("Schade, Sie haben leider verloren.", controller)
  val drawPopup = new ResultPopup("Unentschieden! Versuchen Sie es doch nochmal.", controller)

  //Look and Feel
  try {
    UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel);
  } catch {
    case _ => UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }

  // create top BorderPanel
  drawField
  centerOnScreen

  // define actions
  val quitAction = Action("Beenden") { System.exit(0) }
  val saveAction = Action("Spiel speichern") { new SavePopup(controller, fileRootDir) }
  val newGameAction = Action("Neues Spiel") {
    controller.refreshField
    drawField
  }
  val loadAction = Action("Spiel laden") { new LoadPopup(fileRootDir, Util.listGames(fileRootDir), controller) }
  val resizeAction = Action("Spielfeldgröße") { new ResizePopup(controller.w, controller.h, controller) }
  val recursionAction = Action("Rekursion") { new RecursionPopup(controller.readBotRecursion, controller) }

  // create menu bar  
  menuBar = new MenuBar {
    contents += new Menu("Area") {
      contents += new MenuItem(newGameAction)
      contents += new MenuItem(saveAction)
      contents += new MenuItem(loadAction)
      contents += new MenuItem(quitAction)
    }
    contents += new Menu("Einstellungen") {
      contents += new MenuItem(resizeAction)
      contents += new MenuItem(recursionAction)
    }
    contents += new Menu("Help") {
      contents += new MenuItem("Help Contents") {
        iconTextGap = 5
        icon = new ImageIcon("./images/help.png")
      }
      contents += new MenuItem("About") {
        tooltip = "Tooltip"
        iconTextGap = 5
        icon = new ImageIcon("./images/info.png")
      }
    }
  }

  // define MigPanel
  def migPanel = new MigPanel {
    add(new FlowPanel {
      for (i <- 0 until controller.colorNum) {
        val button = new Button()
        button.preferredSize = new Dimension(45, 45)
        button.background = Util.color(i, true).asInstanceOf[Color]
        if (i == controller.cells(0)(0).c || i == controller.cells(controller.cells.length - 1)(controller.cells(0).length - 1).c) button.visible = false
        button.action = Action("") {
          controller.changeColor(i)
          drawField
        }
        contents += button
      }
    })
  }

  def possessionPanel = new BoxPanel(Orientation.Vertical) {
    border = Swing.EmptyBorder(5)

    contents += new Label("Ihr Fortschritt")
    contents += Component.wrap(new Bar(if ((2 * controller.readPossessions(0)).toInt > 100) 100 else (2 * controller.readPossessions(0).toInt)))

    contents += new Label("Computer")
    contents += Component.wrap(new Bar(if ((2 * controller.readPossessions(1)).toInt > 100) 100 else (2 * controller.readPossessions(1).toInt)))
  }

  def drawField: Boolean = {
    contents = new BorderPanel {
      add(cellGrid, BorderPanel.Position.Center)
      add(migPanel, BorderPanel.Position.North)
      add(possessionPanel, BorderPanel.Position.West)
    }

    if (controller.readPossessions(0) > 50.0) winPopup.open
    if (controller.readPossessions(1) > 50.0) loosePopup.open
    if (controller.readPossessions(0) == 50.0 && controller.readPossessions(1) == 50.0) drawPopup.open

    true
  }

  def cellGrid = new GridPanel(controller.h, controller.w) {
    border = Swing.EmptyBorder(5, 0, 5, 5)
    controller.cells.foreach(row => row.foreach(cell => {
      val newLabel = new Label()
      newLabel.background = Util.color(cell.c, true).asInstanceOf[Color]
      newLabel.opaque = true
      newLabel.preferredSize = new Dimension(25, 25)
      newLabel.maximumSize = new Dimension(25, 25)

      newLabel.border =
        BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(-2, 0, -2, 0), BorderFactory.createBevelBorder(BevelBorder.RAISED))

      contents += newLabel
    }))
  }

  reactions += {
    case Util.FieldUpdate => drawField
    case Util.ClosePopup => {
      winPopup.close
      loosePopup.close
      drawPopup.close
    }
  }

  open
}
