package model

/**
 * __Represantation of field__.
 *
 *
 * {{{
 *  The field contains all cells and represents the Area game.
 * }}}
 *
 *
 * @author Christian Gabele
 * @constructor Create a new field with correct player-count, a bot-instance, a given width and height.
 * @param player '''playercount''' of game
 * @param height '''height''' of game
 * @param width '''width''' of game
 */
class Field ( player: Int, height: Int, width: Int ) {
  /**
   * Get height of field.
   */
  def h = height
  /**
   * Get width of field.
   */
  def w = width
  
  /**
   * Multidimensional Array containing all cells of a game.
   */
  val cells = Array.ofDim[Cell](height, width)
  
  /**
   * Generate a random color.
   */
  def randomColor:Int = (scala.math.random*5).toInt
  
  /**
   * Set all cell-color of the field to randomized colors. Colors of player-cells has to be different.
   */
  def generateRandom = for(i<-0 until height; j<-0 until width){
	  cells(i)(j) = new Cell(i,j,randomColor)
	  while(i == height-1 && j == width-1 && cells(i)(j).compareColors(cells(0)(0)))
	    cells(i)(j) = new Cell(i,j,randomColor)
  	}
  
  /**
   * Get the starting-cell of a player.
   * 
   * @param player ''Playercount'' for getting the start-cell
   * @return Cell of the player
   */
  def playerStart(player:Int):Cell = if(player == 0) cells(0)(0) else cells(height-1)(width-1)
  
  /**
   * XML representation of field-cells ''<cells> XML representation of cells </cells>''
   * 
   * @return xml.Elem Cells of the field
   */
  def toXML:xml.Elem = {
    val temp:Seq[xml.Elem] = for(i<-0 until height; j<-0 until width) yield cells(i)(j).toXML
    <cells>{temp}</cells>
  }
  
  /**
   * String representation of field
   */
  override def toString = {
    var result = ""
    cells.foreach(row => {
      row.foreach(cell => result += cell.toString+" ")
      result += "\n"
    })
    
    result+""
  }
}