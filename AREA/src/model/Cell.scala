package model

case class Cell (height: Int, width: Int, color:Int) {
  
  // Methods for usage
  def x:Int = height
  def y:Int = width
  def c:Int = color
  def ==(cellToCompare:Cell) = if(cellToCompare.x == x && cellToCompare.y == y) true else false
  def compareColors(cellToCompare:Cell):Boolean = cellToCompare.c == color
  
  // Methods for Output
  def coordinatesToString = "(" + x + "," + y + ")"
  def toXML = <cell xValue={x.toString} yValue={y.toString}>{c}</cell>
  //<line>{x}<row>{y}<color>{c}</color></row></line>
  override def toString = {color match {
	case 0 => "r"
	case 1 => "g"
	case 2 => "y"
	case 3 => "b"
	case _ => "v"
	}
  }
}