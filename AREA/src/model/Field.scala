package model

class Field ( player: Int, height: Int, width: Int ) {
  require(List(1,2).contains(player), println("No correct player-count was set"))
  
  def h = height
  def w = width
  
  val cells = Array.ofDim[Cell](height, width)
  
  def randomColor:Int = (scala.math.random*5).toInt
  
  def generateRandom = for(i<-0 until height; j<-0 until width){
	  cells(i)(j) = new Cell(i,j,randomColor)
	  while(i == height-1 && j == 0 && cells(i)(j).compareColors(cells(0)(width-1)))
	    cells(i)(j) = new Cell(i,j,randomColor)
  	}
  
  def playerStart(player:Int):Cell = if(player == 1) cells(height-1)(0) else cells(0)(width-1)
  
  def toXML:xml.Elem = {
    val temp:Seq[xml.Elem] = for(i<-0 until height; j<-0 until width) yield cells(i)(j).toXML
    <cells>{temp}</cells>
  }
  
  override def toString = {
    var result = ""
    //cells.foreach(line => result += line.foreach(cell => result += cell.toString).toString + "\n")
    for(i <-0 until height;j<-0 until width) result += cells(i)(j).toString + (if(j == width-1) "\n" else " ")
    
    result+""
  }
}