package model

class Neighbors(){
  var list:List[Cell] = Nil
  
  override def toString = {
    var result = ""
    list.foreach(cell => result+=cell.toString)
    result+""
  }
}