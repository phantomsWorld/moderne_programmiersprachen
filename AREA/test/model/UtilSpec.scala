package model

import org.specs2.mutable._
import java.awt.Color

class UtilSpec extends SpecificationWithJUnit{

  "The Utility functions for colors" should{
    
    "map String rep of yellow to int = 2" in{
      Util.color("y", false) must be_==(2)
    }
    
    "map int Represantation of color yellow to string (2 -> y)" in{
      Util.color(2, false) must be_==("y")
    }
    
    "map int value of color to corresponding Swing Color type" in{
      Util.color(2, true) must be_==(Color.YELLOW)
    }
    
  }
  
  "The Utility functions for file handling and xml" should{
    "get the following xml files" in{
       // from test folder testXMLFiles
       Util.listGames("test/testXMLFiles", "Text")  must be_==("test1.xml" + "\n")
    }
   
    "generate the a String of the filename with filename +\n" in{
      Util.listFilename("test1.xml", "Test") must be_==("test1.xml" + "\n")
    }
    
    "generate HTML represantation of the filename" in{
      Util.listFilename("test1.xml", "HTML") must be_==("<li class='ui-widget-content ui-corner-all'>" + "test1.xml" + "</li>")
    }
    
    "load the examle XML Game as following" in{
      val x = Util.loadGame("test/testXMLFiles/test1.xml")
      x.p must be_==(2) // 2 players
      x.field.h must be_==(2) //Height = 2
      x.field.w must be_==(2) //Width = 2
      x.field.cells(0)(0).color must be_==(3) // Check that color of first cell was correctly parsed
    }
  }
 
  
}