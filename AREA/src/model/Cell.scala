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
  
  override def toString = (new Util).colorsIntToString(color)
}