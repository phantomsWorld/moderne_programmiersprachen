package model

/**
 * __Represantation of one cell__.
 *
 *
 * {{{
 *  A Cell is the smallest unit in the Area game
 * }}}
 *
 *
 * @author Christian Gabele
 * @constructor create a new cell with position (x and y) and a color
 * @param height '''x coordinate''' of cell
 * @param width	'''y coordinate''' of cell
 * @param color int representation of cell color
 */

case class Cell(height: Int, width: Int, color: Int) {

  /**
   * Get x coordinate of cell
   */
  def x: Int = height

  /**
   * Get y coordinate of cell
   */
  def y: Int = width

  /**
   * Get color of cell as int value
   */
  def c: Int = color

  /**
   * Compare cell. Cell is equal if x and y coordinate are the same
   *
   * @param cellToCompare '' Other cell object'' for comparison
   * @return Boolean value
   */
  def ==(cellToCompare: Cell) = if (cellToCompare.x == x && cellToCompare.y == y) true else false

  /**
   * Compare if cells have the same color
   */
  def compareColors(cellToCompare: Cell): Boolean = cellToCompare.c == color

  /**
   * String representation of cell coordinates
   */
  def coordinatesToString = "(" + x + "," + y + ")"

  /**
   * XML representation of cell <cell xValue=x yValue=y> color </cell>
   */
  def toXML = <cell xValue={ x.toString } yValue={ y.toString }>{ c }</cell>

  override def toString = Util.colorsIntToString(color)
}
