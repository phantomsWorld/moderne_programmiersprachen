import scala.swing._
import javax.swing.ImageIcon
import com.sun.tools.example.debug.gui.Icons
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
import swingGuiTest.Bar

class AreaFrame extends MainFrame{
  
  title = "Scala Area"
  //iconImage = toolkit.getImage("c:\\test\\line.gif") //Not available in OSX
  centerOnScreen
  
  //Look and Feel
  try{
	 UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel);
	  
  } catch {
	  case _ => UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }
	 
  // create top BorderPanel
  contents = new BorderPanel{
    add(cellGrid, BorderPanel.Position.Center)
    add(playerFrame, BorderPanel.Position.East)
    add(migPanel, BorderPanel.Position.West)
    preferredSize = new Dimension(1024, 768)
  }
 
// create menu bar  
  menuBar = new MenuBar {
    contents += new Menu("Area"){
      contents += new MenuItem(Action("New Game"){ })
      contents += new MenuItem(Action("Save"){ })
      contents += new MenuItem(Action("Quit"){System.exit(0)})
    } 
    contents += new Menu("Settings"){
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
    add(new Label("Designs"), "spanx 10")
    add(new Label("Name"), "growx 0, shrink")

  }
  
// define player frame
  
   def playerFrame = new BoxPanel(Orientation.Vertical){
    
     contents += new FlowPanel {
      var b1 = new Button()
      b1.preferredSize_=(new Dimension(20,20))
      b1.background = Color.BLUE
      var b2 = new Button()
      b2.background = Color.RED
      var b3 = new Button()
      b3.background=Color.GREEN
      var b4 = new Button()
      b4.background=Color.YELLOW
      var b5 = new Button()
      b5.background=new Color(204,51,204)
      contents+=b1
      contents+=b2
      contents+=b3
      contents+=b4
      contents+=b5
      
      b2.contentAreaFilled = false
      
    }
    
   //var progBar = new StatusBar("label", SwingConstants.CENTER, Color.RED, 
   //             Color.BLUE)
    
    var progBar = new Bar(30);
    contents += new Label("Player 1")
    contents += Component.wrap(progBar)
    
    var progBar2 = new Bar(80);
    contents += new Label("Player 2")
    contents += Component.wrap(progBar2)
  }
   
// define cell grid
   
   def cellGrid = new GridPanel(30,30){
     val redLabel = new Label
     redLabel.background = Color.RED
     redLabel.opaque = true
     val greenLabel = new Label
     greenLabel.background = Color.GREEN
     greenLabel.opaque= true
     val blueLabel = new Label
     blueLabel.background = Color.BLUE
     blueLabel.opaque = true
     
     val colorList = List(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, new Color(204,51,204))   
//
//     contents += redLabel
//     contents += greenLabel
//     contents += blueLabel
//     contents += greenLabel
     
     for(i <- 0 until (rows*columns)){
       var rand = Random.nextInt(colorList.size)
       println("Random:" + rand)
       val newLabel = new Label() //new Label(i.toString)
       newLabel.background = colorList(rand)
       newLabel.opaque = true
      // var border = new EtchedBorder(EtchedBorder.RAISED)
      // newLabel.border = BorderFactory.createEtchedBorder(1);
      //newLabel.border = BorderFactory.createBevelBorder(BevelBorder.LOWERED)
       //newLabel.border = new SoftBevelBorder(BevelBorder.RAISED)
       
       newLabel.border = 
         BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(-2,0,-2,0),BorderFactory.createBevelBorder(BevelBorder.RAISED))
//       newLabel.border = new LineBorder(Color.black,2)
       contents += newLabel
     }
     
   }
  
}
  

