package controller

import model._

class Bot (fieldController:Fieldcontroller,botID:Int) {
	val maxRecDepth = 1
	var sizeRecArray = 0
	val recStartArray = Array.tabulate(maxRecDepth)(x => 0)
	for(i<- 0 until maxRecDepth) {
	  recStartArray(i) = sizeRecArray
	  sizeRecArray = sizeRecArray + (math.pow(fieldController.colorNum,i+1)).toInt
	}
	val recursionNeighbors = Array.tabulate(sizeRecArray)(x => new Neighbors)

	def getBotColor():Int = {
	  updateRecursionNeighbors
	  
	  var botColor = 0
	  
	  val countOfPossibleNeighbors = Array.tabulate(fieldController.colorNum) (i => 0)
	  
	  for(i<-recStartArray(maxRecDepth-1) until recStartArray(maxRecDepth-1)+(if(maxRecDepth==1) fieldController.colorNum else (math.pow(fieldController.colorNum, maxRecDepth).toInt)-1)) {
	    val possibleNeighbor = if(maxRecDepth == 1) i else (i/(math.pow(fieldController.colorNum,(maxRecDepth-1)))).toInt-1
	    countOfPossibleNeighbors(possibleNeighbor) = math.max(countOfPossibleNeighbors(possibleNeighbor), recursionNeighbors(i).list.size)
	  }
	  
	  countOfPossibleNeighbors(fieldController.field.playerStart(0).c) = -1
	  countOfPossibleNeighbors(fieldController.field.playerStart(1).c) = -1
	  
	  for(i<-0 until fieldController.colorNum) println("Color: "+i+" - Size: "+countOfPossibleNeighbors(i))
	  for(i<-0 until fieldController.colorNum) if(countOfPossibleNeighbors(i) > countOfPossibleNeighbors(botColor)) botColor = i
	  
	  return botColor
	}
	
	def updateRecursionNeighbors {
	  for(recNum<-0 until maxRecDepth; actualColor<- 0 until (math.pow(fieldController.colorNum,(recNum+1))).toInt) {
	    if(recNum == 0) recursionNeighbors(actualColor).list = fieldController.recursiveBotNeighbors(fieldController.ownedCells(botID).list, actualColor)
	    else recursionNeighbors(recStartArray(recNum)+actualColor).list = fieldController.recursiveBotNeighbors(recursionNeighbors(recStartArray(recNum-1)+actualColor/fieldController.colorNum).list, actualColor % fieldController.colorNum)
	  }
	}
	 
}