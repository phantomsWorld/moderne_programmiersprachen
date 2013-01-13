package swingGuiTest

import controllers.GameController

import scala.swing._
import javax.swing.UIManager

class LoadPopup(rootDir:String,files:String,controller:GameController) extends Dialog {
  
  title = "Laden eines gespeicherten Spiels"
  modal = true
  preferredSize = new Dimension(350,350)
  resizable = false
  
  try{
	 UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel);
  } catch {
	case _ => UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }
  
  val fileTable = new Table(Array.tabulate((files.split("\n").asInstanceOf[Array[Any]]).length) {i=>Array(files.split("\n").asInstanceOf[Array[Any]](i))} , (Array.tabulate(1) {"Gefundene Spiele"}).toSeq) {
    selection.elementMode = Table.ElementMode.Cell
  }
  
  contents = new BorderPanel {
    add(new MigPanel{add(new Label("Gefundene Spiele:"){border = Swing.EmptyBorder(8,8,0,8)})},BorderPanel.Position.North)
	add(tablePanel,BorderPanel.Position.Center)
	add(buttonPanel,BorderPanel.Position.South)
  }
  centerOnScreen
  
  def tablePanel = new BoxPanel(Orientation.Vertical) {
	  border = Swing.EmptyBorder(8)
	  contents += fileTable
	}
  
  def buttonPanel = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += Button("Ok"){
        if(fileTable.selection.rows.leadIndex >= 0) {
          //println("Selected file: "+(files.split("\n"))(fileTable.selection.rows.leadIndex))
          controller.loadGame(rootDir+(files.split("\n"))(fileTable.selection.rows.leadIndex))
          //publish((new Message((files.split("\n"))(fileTable.selection.rows.leadIndex))).m)
        }
        close
      }
      contents += Button("Abbrechen"){
        close
      }
    }
  }
  
  open
}