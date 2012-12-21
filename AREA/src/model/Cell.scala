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
  override def toString = {
  	val colors = Map( 0 -> "r",
  					  1 -> "g",
  					  2 -> "y",
  					  3 -> "b",
  					  4 -> "v" )
	colors(color)
  }
}