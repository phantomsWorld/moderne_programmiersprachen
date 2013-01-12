package swingGuiTest;

import controllers._
import model.Util

import scala.swing._
import javax.swing.ImageIcon
import java.net.URL
import java.awt.Color
import scala.util.Random
import javax.swing.border.Border
import javax.swing.border.LineBorder
import java.awt.Dimension
import javax.swing.UIManager
import javax.swing.BorderFactory
import javax.swing.border.EtchedBorder
import javax.swing.border.BevelBorder
import javax.swing.border.SoftBevelBorder
import javax.swing.JProgressBar
import javax.swing.SwingConstants

class AreaFrame(fileRootDir:String = "games/") extends MainFrame{
  
  var controller = new Fieldcontroller
  
  title = "Scala Area"
  iconImage = toolkit.getImage("images/AREA_favicon.png") //Not available in OSX
  resizable = false
  
  //Look and Feel
  try{
	 UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel);
  } catch {
	  case _ => UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }
	 
  // create top BorderPanel
  drawField
  centerOnScreen
 
  // define actions
   val quitAction = Action("Beenden") { System.exit(0) }
   val saveAction = Action("Spiel speichern") { (new SavePopup(controller, fileRootDir)).open }
   val newGameAction = Action("Neues Spiel") {
     controller.refreshField
     drawField
   }
   val loadAction = Action("Spiel laden") {
     (new LoadPopup((new Util).listGames(fileRootDir))).open
     /*(new Util).loadGame("test.xml")
     drawField*/
   }
   val resizeAction = Action("Spielfeldgröße") { (new ResizePopup(controller.field.w,controller.field.h)).open }
   val recursionAction = Action("Rekursion") { (new RecursionPopup(controller.readRecursion)).open }
  
// create menu bar  
  menuBar = new MenuBar {
    contents += new Menu("Area"){
      contents += new MenuItem(newGameAction)
      contents += new MenuItem(saveAction)
      contents += new MenuItem(loadAction)
      contents += new MenuItem(quitAction)
    } 
    contents += new Menu("Einstellungen"){
      contents += new MenuItem(resizeAction)
      contents += new MenuItem(recursionAction)
    }
    contents += new Menu("Help"){
      contents += new MenuItem("Help Contents"){
        iconTextGap = 5
        icon = new ImageIcon("./images/help.png")
      }
      contents += new MenuItem("About"){
        tooltip="Tooltip"
        iconTextGap = 5
        icon = new ImageIcon("./images/info.png")
      }
    }
  }

// define MigPanel
	def migPanel = new MigPanel{
	  add(new FlowPanel {
       for(i<- 0 until controller.colorNum){
         val button = new Button()
         button.preferredSize = new Dimension(45,45)
         button.background = (new Util).color(i,true).asInstanceOf[Color]
         button.action = Action("") {
           controller.changeColor(i)
           drawField
         }
         contents+=button
       }
      })
	}
	def migPanelWest = new BoxPanel(Orientation.Vertical){
	  border = Swing.EmptyBorder(5)
	  
	  contents += new Label("Ihr Fortschritt")
	  contents += Component.wrap(new Bar(if((2*controller.calculatedPossession(0)).toInt>100) 100 else (2*controller.calculatedPossession(0).toInt)))
	  
	  contents += new Label("Computer")
	  contents += Component.wrap(new Bar(if((2*controller.calculatedPossession(1)).toInt>100) 100 else (2*controller.calculatedPossession(1).toInt)))
	}
  
// define player frame
   def playerFrame = new BoxPanel(Orientation.Vertical){
     contents += new FlowPanel {
       for(i<- 0 until controller.colorNum){
         val button = new Button()
         button.preferredSize = new Dimension(45,45)
         button.background = (new Util).color(i,true).asInstanceOf[Color]
         button.action = Action("") {
           controller.changeColor(i)
           drawField
         }
         contents+=button
       }
    }
    
    contents += new Label("Ihr Fortschritt")
    contents += Component.wrap(new Bar((2*controller.calculatedPossession(0)).toInt))
    
    contents += new Label("Computer")
    contents += Component.wrap(new Bar((2*controller.calculatedPossession(1)).toInt))
  }
   
   def drawField:Boolean = {
     contents = new BorderPanel{
	    add(cellGrid, BorderPanel.Position.Center)
	    //add(playerFrame, BorderPanel.Position.East)
	    add(migPanel, BorderPanel.Position.North)
	    add(migPanelWest,BorderPanel.Position.West)
	    //preferredSize = new Dimension(1024, 768)
	  }
   	 true
   }
   
   def cellGrid = new GridPanel(controller.field.h,controller.field.w) {
     border = Swing.EmptyBorder(5,0,5,5)
     controller.field.cells.foreach(row => row.foreach(cell=> {
       val newLabel = new Label()
       newLabel.background = (new Util).color(cell.c,true).asInstanceOf[Color]
       newLabel.opaque = true
       newLabel.preferredSize = new Dimension(25,25)
       newLabel.maximumSize = new Dimension(25,25)
       
       newLabel.border = 
         BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(-2,0,-2,0),BorderFactory.createBevelBorder(BevelBorder.RAISED))
       
       contents += newLabel
     }))
   }
}
  

