package swingGuiTest

import controllers.GameController
import scala.swing._
import javax.swing.UIManager

class ResizePopup(actualWidth:Int,actualHeight:Int,controller:GameController) extends Dialog {
  
  title = "Anpassung der Spielfelgröße"
  modal = true
  preferredSize = new Dimension(350,350)
  resizable = false
  
  try{
	 UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel);
  } catch {
	case _ => UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }
  
  val sliderWidth = new Slider{
    min = 4
    max = 20
    value = actualWidth
    majorTickSpacing = 4
    minorTickSpacing = 4
    snapToTicks = true
    paintLabels = true
  }
  
  val sliderHeight = new Slider{
    min = 4
    max = 20
    value = actualHeight
    majorTickSpacing = 4
    minorTickSpacing = 4
    snapToTicks = true
    paintLabels = true
  }
  
  contents = new BorderPanel {
    add(new BoxPanel (Orientation.Vertical) {
      contents += new Label("Bitte wählen Sie die neuen Maße für das Spielfeld."){ border = Swing.EmptyBorder(30,8,0,0) }
      contents += new Label("Das Ändern generiert ein neues Spiel."){ border = Swing.EmptyBorder(8,8,0,8) }
      contents += addWidth
      contents += addHeight
    },BorderPanel.Position.Center)
    
	add(buttonPanel,BorderPanel.Position.South)
  }
  centerOnScreen
  
  def addWidth = new BoxPanel (Orientation.Vertical) {
    border = Swing.EmptyBorder(15)
    contents += new MigPanel{add(new Label("Breite") { border = Swing.EmptyBorder(0,0,5,0) })}
    contents += sliderWidth
  }
  
  def addHeight = new BoxPanel (Orientation.Vertical) {
    border = Swing.EmptyBorder(15)
    contents += new MigPanel{add(new Label("Höhe") { border = Swing.EmptyBorder(0,0,5,0) })}
    contents += sliderHeight
  }
  
  def buttonPanel = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += Button("Ok"){
        //publish((new Message(sliderWidth.value.toString+";"+sliderHeight.value.toString)).m)
        controller.resizeField(sliderHeight.value,sliderWidth.value)
        close
      }
      contents += Button("Abbrechen"){
        close
      }
    }
  }
  
  open
}