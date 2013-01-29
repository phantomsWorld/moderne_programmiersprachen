package swingGui

import controllers.GameController
import scala.swing._
import javax.swing.UIManager

class RecursionPopup(actualRecursion: Int, controller: GameController) extends Dialog {

  title = "Rekursionstiefe für Bot festlegen"
  modal = true
  preferredSize = new Dimension(350, 180)
  resizable = false

  try {
    UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel);
  } catch {
    case _ => UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }

  val slider = new Slider {
    min = 1
    max = 6
    value = actualRecursion
    majorTickSpacing = 1
    minorTickSpacing = 1
    snapToTicks = true
    paintLabels = true
  }

  contents = new BorderPanel {
    add(new BoxPanel(Orientation.Vertical) {
      contents += new Label("Rekursionstiefe:") { border = Swing.EmptyBorder(30, 8, 0, 0) }
      contents += addRecursion
    }, BorderPanel.Position.Center)

    add(buttonPanel, BorderPanel.Position.South)
  }
  centerOnScreen

  def addRecursion = new BoxPanel(Orientation.Vertical) {
    border = Swing.EmptyBorder(15)
    contents += slider
  }

  def buttonPanel = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += Button("Ok") {
        controller.resetBotRecursion(slider.value)
        close
      }
      contents += Button("Abbrechen") {
        close
      }
    }
  }

  open
}
