package controllers

import model._

class Fieldcontroller (playerCount: Int, height: Int, width: Int, useBot:Boolean){
	def this(){this(2,8,8,true)}
	def this(height:Int, width:Int){this(2,height,width,true)}
	def this(useBot:Boolean){this(2,4,4,useBot)}
	
	println("==== Define new Fieldcontroller ====")
	
	val colorNum = 5
	val field = new Field(playerCount, height, width)
	val ownedCells = Array(new Neighbors,new Neighbors)
	var playerOnTurn = if(useBot) 0 else (scala.math.random*2).toInt
	val botID = 1
	val bot = new Bot(this,botID)
	
	refreshField
	
	def p = playerCount
	
	def updateOwnedCells(player:Int) = {
	  require(List(0,1).contains(player), println("Incorrect player!!!"))
	  
	  ownedCells(player).list = List(field.playerStart(player))
	  ownedCells(player).list.foreach(cell => updateOwnedNeighbors(player,cell))
	}
	
	def getNeighborCell(cell:Cell, direction:String):Cell = {
	  var x = cell.x
	  var y = cell.y
	  direction match {
	    case "rechts" => if(y < width-1) y += 1
	    case "links"  => if(y > 0) y -= 1
	    case "oben"   => if(x > 0) x -= 1
	    case "unten"  => if(x < height-1) x += 1
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
	    
	    if(!ownedCells(player).list.contains(neighborCell) && !ownedCells((player+1)%2).list.contains(neighborCell) && (cell==neighborCell == false) && (cell.compareColors(neighborCell))) {
	      ownedCells(player).list = ownedCells(player).list ::: List(neighborCell)
	      neighborFound = true
	    }
	  }
	  
	  if(neighborFound == true)
	    ownedCells(player).list.foreach(cell => updateOwnedNeighbors(player,cell))
	    
	  true
	}
	
	def recursiveBotNeighbors(visitedCells:List[Cell], nextCheckingColor:Int):List[Cell] = {
	  var list:List[Cell] = visitedCells
	  var neighborCell:Cell = null
	  var neighborFound = false
	  
	  visitedCells.foreach{cell => {
		  for(i<-0 until 4){
		    i match{
		      case 0 => neighborCell = getNeighborCell(cell, "rechts")
		      case 1 => neighborCell = getNeighborCell(cell, "links")
		      case 2 => neighborCell = getNeighborCell(cell, "oben")
		      case 3 => neighborCell = getNeighborCell(cell, "unten")
		    }
		    
		    if(!(list.contains(neighborCell)) && !(ownedCells((botID+1)%2)).list.contains(neighborCell) && neighborCell.c == nextCheckingColor) {
		      neighborFound = true
		      list = List(neighborCell) ::: list
		    }
		  }
	  	}
	  }
	  
	  if(neighborFound == true) list = recursiveBotNeighbors(list, nextCheckingColor)
	  
	  return list
	  
	}
	
	def refreshField = {
	  field.generateRandom
	  updateOwnedCells(0)
	  updateOwnedCells(1)
	}
	
	def switchPlayer = playerOnTurn = (playerOnTurn+1)%2
	
	def waitForBot:Boolean = {
	  if(useBot){
	    var color = bot.getBotColor
	    println("Selected color: "+color)
	    changeColorWithBotInformation(color,false)}
	  true
	}
	
	def changeColor(newColor:Int) = {
	  val player = playerOnTurn
	  if(useBot) changeColorWithBotInformation(newColor,true) else changeColorWithBotInformation(newColor,false)
	  
	  ownedCells(player).list
	}
	
	def changeColorWithBotInformation(newColor:Int, callBot:Boolean) = {
	  if(newColor == field.playerStart(0).c || newColor == field.playerStart(1).c) println("Incorrect color selected!!!\nColors: "+newColor+"; Player: "+playerOnTurn)
	  else {
		  ownedCells(playerOnTurn).list.foreach(cell => field.cells(cell.x)(cell.y) = new Cell(cell.x, cell.y, newColor))
		  updateOwnedCells(playerOnTurn)
		  switchPlayer
		  
		  if(callBot){waitForBot}
	  }
	}
	
	// Calculate the percent possession (Array of 2 doubles)
	def calculatedPossession = Array(100*ownedCells(0).list.length.toDouble/(field.cells.length*field.cells(0).length),100*ownedCells(1).list.length.toDouble/(field.cells.length*field.cells(0).length))
	
	// methods for bot-recursion
	def readRecursion = bot.recursion
	def setRecursion(value:Int) = bot.recursion(value)
	
	// methods handle XML files
	def parseXML(data: xml.Elem) = {
	  playerOnTurn = (data \\ "playerOnTurn").text.toInt
	  for(cell <- data \\ "cell") field.cells((cell \\ "@xValue").text.toInt)((cell \\ "@yValue").text.toInt) = new Cell((cell \\ "@xValue").text.toInt, (cell \\ "@yValue").text.toInt, cell.text.toInt)
	  
	  updateOwnedCells(0)
	  updateOwnedCells(1)
	}
	
	def saveXML(filename:String) = {
	     var myXML: scala.xml.Node = <field><gameInfos><player>{playerCount}</player><height>{field.h.toString}</height><width>{field.w.toString}</width><playerOnTurn>{playerOnTurn}</playerOnTurn></gameInfos>{field.toXML}</field>
	     scala.xml.XML.save({if(filename.contains(".xml")) filename else filename+".xml"},myXML, "UTF8",true,null)
	}
	
	// methods for returning field-cells
	def fieldToHtmlString = {
	  var result = ""
	  
	  for(i<-0 until height; j<-0 until width) result = result+(if(j==0)"<tr>"else"")+"<td id='"+i+"-"+j+"' class='"+field.cells(i)(j).toString+"'><img src='"+field.cells(i)(j).toString+".png' alt='"+field.cells(i)(j).toString+"' class='"+(if(ownedCells(0).list.contains(field.cells(i)(j))==true) "glow0" else if(ownedCells(1).list.contains(field.cells(i)(j))==true) "glow1" else "")+"' /></td>"+(if(j==width-1)"</tr>"else"")
	  
	  result + ""
	}
	
	override def toString = {
	  var result = field.toString+"\n\nPlayer on Turn: "+playerOnTurn +"\nOwned Cells:"
	  ownedCells(playerOnTurn).list.foreach(cell => result += cell.coordinatesToString + "-" + cell.toString + "; ")
	  result+""
	}
}