package controller

import model._

class RecursionHandler(fieldController:Fieldcontroller, recDepth:Int, botID:Int) {
	var sizeRecArray = 0
	val recStartArray = Array.tabulate(recDepth)(x => 0)
	for(i<- 0 until recDepth) {
	  recStartArray(i) = sizeRecArray
	  sizeRecArray = sizeRecArray + (math.pow(fieldController.colorNum,i+1)).toInt
	}
	val recursionNeighbors = Array.tabulate(sizeRecArray)(x => new Neighbors)
	
	def recursionDepth = recDepth
	
	def updateRecursionNeighbors {
	  for(recNum<-0 until recDepth; actualColor<- 0 until (math.pow(fieldController.colorNum,(recNum+1))).toInt) {
	    if(recNum == 0) recursionNeighbors(actualColor).list = fieldController.recursiveBotNeighbors(fieldController.ownedCells(botID).list, actualColor)
	    else recursionNeighbors(recStartArray(recNum)+actualColor).list = fieldController.recursiveBotNeighbors(recursionNeighbors(recStartArray(recNum-1)+actualColor/fieldController.colorNum).list, actualColor % fieldController.colorNum)
	  }
	}
}