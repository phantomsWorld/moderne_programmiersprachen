package swingGuiTest

import scala.swing._
import javax.swing.UIManager
import controllers._

class SavePopup(controller:GameController,fileRootDir:String) extends Dialog {
  title = "Speichern des aktuellen Spiels"
  modal = true
  preferredSize = new Dimension(350,260)
  resizable = false
  
  try{
	 UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel);
  } catch {
	case _ => UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }
  
  val fileName = new TextField {
    border = Swing.EmptyBorder(5)
    columns = 150
  }
  
  contents = new BorderPanel {
    add(new BoxPanel (Orientation.Vertical) {
      contents += new Label("Bitte geben Sie den gewünschten Dateinamen an."){ border = Swing.EmptyBorder(30,8,0,0) }
      contents += new Label("Dabei dürfen Sie keine Ordnerstruktur angeben!"){ border = Swing.EmptyBorder(8,8,0,8) }
      contents += new Label("Dateiname:"){border = Swing.EmptyBorder(30,8,0,0)}
      contents += textField
    },BorderPanel.Position.Center)
    
	add(buttonPanel,BorderPanel.Position.South)
  }
  centerOnScreen
  
  def textField = new MigPanel{
    add(fileName)
  }
  
  def buttonPanel = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += Button("Ok"){
        println("Selected file: "+fileName.text)
        controller.saveGame(fileRootDir+fileName.text)
        close
      }
      contents += Button("Abbrechen"){
        close
      }
    }
  }
  
  open
}