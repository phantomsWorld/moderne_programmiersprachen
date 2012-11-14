package controller

import model._
import javax.activation.CommandInfo

class Fieldcontroller (playerCount: Int, height: Int, width: Int){
	def this(){this(2,8,8)}
	def this(height:Int, width:Int){this(2,height,width)}
	
	val field = new Field(playerCount, height, width)
	val ownedCells = Array(new Neighbors,new Neighbors)
	var playerOnTurn = (Math.random*2).toInt
	//val bot = new Bot
	
	def p = playerCount
	
	def updateOwnedCells(player:Int) = {
	  require(List(0,1).contains(player), println("Incorrect player!!!"))
	  
	  ownedCells(player).list = List(field.playerStart(player))
	  ownedCells(player).list.foreach(cell => updateOwnedNeighbors(player,cell))
	}
	
	refreshField
	
	def getNeighborCell(cell:Cell, direction:String):Cell = {
	  var x = cell.x
	  var y = cell.y
	  direction match {
	    case "rechts" => if(y < width-2) y += 1
	    case "links"  => if(y > 0) y -= 1
	    case "oben"   => if(x > 0) x -= 1
	    case "unten"  => if(x < height-2) x += 1
	    case _ => println("Incorrect direction!")
	  }
	  
	  field.cells(x)(y)
	}
	
	def updateOwnedNeighbors(player:Int, cell:Cell):Boolean = {
	  var neighborFound = false
	  var neighborCell:Cell = null
	  
	  for(i<-0 until 4){
	    i match{
	      case 0 => neighborCell = getNeighborCell(cell, "rechts")
	      case 1 => neighborCell = getNeighborCell(cell, "links")
	      case 2 => neighborCell = getNeighborCell(cell, "oben")
	      case 3 => neighborCell = getNeighborCell(cell, "unten")
	    }
	    
	    if(!ownedCells(player).list.contains(neighborCell) && ownedCells((player+1)%2).list.contains(neighborCell) == false && (cell==neighborCell == false) && (cell.compareColors(neighborCell))) {
	      ownedCells(player).list = ownedCells(player).list ::: List(neighborCell)
	      neighborFound = true
	    }
	  }
	  
	  if(neighborFound == true)
	    ownedCells(player).list.foreach(cell => updateOwnedNeighbors(player,cell))
	    
	  true
	}
	
	def refreshField = {
	  field.generateRandom
	  updateOwnedCells(0)
	  updateOwnedCells(1)
	}
	
	def switchPlayer = playerOnTurn = (playerOnTurn+1)%2
	
	def changeColor(newColor:Int) = {
	  if(newColor == field.playerStart(0).c || newColor == field.playerStart(1).c) println("Incorrect color selected!!!") else {
		  ownedCells(playerOnTurn).list.foreach(cell => field.cells(cell.x)(cell.y) = new Cell(cell.x, cell.y, newColor))
		  updateOwnedCells(playerOnTurn)
		  switchPlayer
	  }
	}
	
	def parseXML(data: xml.Elem) = {
	  playerOnTurn = (data \\ "playerOnTurn").text.toInt
	  for(cell<- data \\ "cell") field.cells((cell \\ "@xValue").text.toInt)((cell \\ "@yValue").text.toInt) = new Cell((cell \\ "@xValue").text.toInt, (cell \\ "@yValue").text.toInt, cell.text.toInt)
	  
	  updateOwnedCells(0)
	  updateOwnedCells(1)
	}
	
	def saveXML(filename:String) = {
	   var myXML: scala.xml.Node = <field><gameInfos><player>{playerCount}</player><height>{field.h.toString}</height><width>{field.w.toString}</width><playerOnTurn>{playerOnTurn}</playerOnTurn></gameInfos>{field.toXML}</field>
	   scala.xml.XML.save({if(filename.contains(".xml")) filename else filename+".xml"},myXML, "UTF8",true,null)
	}
	
	override def toString = {
	  var result = "Owned Cells:"
	  ownedCells(playerOnTurn).list.foreach(cell => result += cell.coordinatesToString + "-" + cell.toString + "; ")
	  result+""
	}
}