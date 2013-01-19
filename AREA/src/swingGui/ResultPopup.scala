package swingGui

import controllers.GameController
import model.Util
import scala.swing._
import java.awt.Dimension
import javax.swing.UIManager

class ResultPopup(gameResult: String, controller: GameController) extends Publisher {
  val dialog = new Dialog {
    title = "Spielende"
    modal = false
    preferredSize = new Dimension(300, 150)
    resizable = false

    try {
      UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel);
    } catch {
      case _ => UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    contents = new BorderPanel {
      add(new BoxPanel(Orientation.Vertical) {
        contents += new Label(gameResult) { border = Swing.EmptyBorder(30, 8, 0, 0) }
      }, BorderPanel.Position.Center)

      add(buttonPanel, BorderPanel.Position.South)
    }
    centerOnScreen

    def buttonPanel = new BoxPanel(Orientation.Vertical) {
      contents += new FlowPanel {
        contents += Button("Ok") {
          controller.refreshField
          close
        }
      }
    }
  }

  def open = dialog.open
  def close = dialog.close
}
