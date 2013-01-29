package model

/**
 * __Represantation of neighbors of a cell__.
 *
 *
 * {{{
 *  A Cell has multiple neighbors (of the same colorexpected) in the Area game
 * }}}
 *
 *
 * @author Christian Gabele
 * @constructor create a new list of type Cell
 */
class Neighbors{
  /**
   * Simple List of type Cell.
   */
  var list:List[Cell] = Nil
  
  /**
   * String representation of all neighbor cells
   */
  override def toString = {
    var result = ""
    list.foreach(cell => result+=cell.toString)
    result + ""
  }
}
