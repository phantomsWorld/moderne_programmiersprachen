package model

import org.specs2.mutable._

class CellSpec2 extends SpecificationWithJUnit{
  "A new Cell" should {
    val cell = new Cell(0, 3, 2) // Coordinate (0,3) , color 2
    "have x-coordinate 0" in {
      cell.x must be_==(0)
    }
    "have y-coordinate 3" in {
      cell.y must be_==(3)
    }
    "have color 2" in {
      cell.color must be_==(2)
    }
  }

  "Two indent cells compared" should {
    val cell = new Cell(0, 3, 2)
    val cell2 = new Cell(0, 3, 2)

    "must be ident in coordinates" in {
      cell == cell2 must beTrue
    }

    "must have same color" in {
      cell.compareColors(cell2) must beTrue
    }

  }

  "Two different cells compared" should {
    val cell = new Cell(1, 2, 3)
    val cell2 = new Cell(3, 2, 1)

    "must be different in coordinates" in {
      cell == cell2 must beFalse
    }

    "must be different in color" in {
      cell.compareColors(cell2) must beFalse
    }
  }

  "Output represantation of cell" should {
    val cell = new Cell(1, 1, 2)

    "look for Coordinates like (x,y)" in {
      cell.coordinatesToString must be_==("(1,1)")
    }

    "as String must be the Color" in {
      cell.toString must be_==(Util.colorsIntToString(cell.color))
    }

    "be as XML line <cell yValue={ y.toString } xValue={ x.toString }>{ color }</cell>" in {
      println(cell.toXML)
      cell.toXML must be_==(<cell xValue={ cell.x.toString } yValue={ cell.y.toString }>{ cell.c }</cell>)
    }
  }

}