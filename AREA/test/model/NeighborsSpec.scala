package model

import org.specs2.mutable._

class NeighborsSpec extends SpecificationWithJUnit{
  
  "A new Neigbor list" should{
    val neighbor = new Neighbors
    val color = 2
    neighbor.list = List(Cell(1,2,color))
    
    "have a List with one Neighbor" in{
      neighbor.list.size must be_==(1)
    }
    
    "have to generate the following String" in{
      neighbor.toString must be_==("" + Util.colorsIntToString(color) + "")
    }
    
  }
  
}