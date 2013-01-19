package model

import org.specs2.mutable._

class FieldSpec2 extends SpecificationWithJUnit{
  
  "A new empty Field" should{
    val field = new Field(2,4,4)
    
    "have height" in{
      field.h must be_==(4)
    }
    
    "have width" in{
      field.w must be_==(4)
    }
    
    "must have cell Array of Dimension 4x4" in{
      field.cells.size must be_==(4)
      field.cells(0).size must be_==(4)
    }
    
  }
 
  "A new filled Field" should{
    val field = new Field(2,2,2)
    field.generateRandom
    
    "have allowed color values between 0-4" in{
      field.cells(0)(0).color must be_>=(0) and be_<=(5)
      field.cells(0)(1).color must be_>=(0) and be_<=(5)
      field.cells(1)(0).color must be_>=(0) and be_<=(5)
      field.cells(1)(1).color must be_>=(0) and be_<=(5)
    }
    
    "have different start colors for the player, " +
    "player 1 start cell(0)(0), player 2 start cell(height)(weight)" in{
      field.cells(0)(0).compareColors(field.cells(1)(1)) must beFalse
    }
    
    "have start cell for player 1 in cell(0)(0)" in{
      field.playerStart(0)==field.cells(0)(0) must beTrue
    }
    
  }
  
   "The prinout of a new filled Field" should{
    val field = new Field(2,1,1)
    val color = 1
    val cell = new Cell(0, 0, color)
    field.cells(0)(0) = cell // color = 1
        
    
    "generate the following String form " in{
      field.toString must be_==("" + Util.colorsIntToString(color) + " \n" + "")
    }
          
    "generate the following XML " in{
      field.toXML must be_==(<cells><cell xValue={ cell.x.toString } yValue={ cell.y.toString }>{ cell.c }</cell></cells>)
      
    }
    
  }
   
}