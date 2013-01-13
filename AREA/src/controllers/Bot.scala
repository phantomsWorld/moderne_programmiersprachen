package controllers

import model._

class Bot (fieldController:Fieldcontroller,botID:Int) {
	val defaultRecDepth = 4
	var recursionHandle = new RecursionHandler(fieldController,defaultRecDepth,botID)
	val countOfPossibleNeighbors = Array.tabulate(fieldController.colorNum) (i => 0)
	
	def getBotColor():Int = getBotColor(recursionHandle.recursionDepth)
	def getBotColor(recDepth:Int):Int = {
	  recursionHandle.updateRecursionNeighbors
	  
	  var botColor = 0
	  
	  calculateNeighborCounts(recDepth)
	  
	  countOfPossibleNeighbors(fieldController.field.playerStart(0).c) = -1
	  countOfPossibleNeighbors(fieldController.field.playerStart(1).c) = -1
	  
	  //for(i<-0 until fieldController.colorNum) println("Color: "+i+" - Size: "+countOfPossibleNeighbors(i))
	  for(i<-0 until fieldController.colorNum) if(countOfPossibleNeighbors(i) > countOfPossibleNeighbors(botColor)) botColor = i
	  
	  return botColor
	}
	
	def calculateNeighborCounts(recDepth:Int):Boolean = {
	  // initialize all counts for possible neighbor counts
	  for(i<- 0 until fieldController.colorNum) countOfPossibleNeighbors(i) = 0
	  
	  for(i<- 0 until math.pow(fieldController.colorNum, recDepth).toInt) countOfPossibleNeighbors((i/(math.pow(fieldController.colorNum,(recDepth-1)))).toInt) = math.max(countOfPossibleNeighbors((i/(math.pow(fieldController.colorNum,(recDepth-1)))).toInt), recursionHandle.recursionNeighbors(recursionHandle.recStartArray(recDepth-1)+i).list.size)
	  
	  // compare if all elements have the same value
	  if(recDepth > 1 && countOfPossibleNeighbors.sameElements(Array.tabulate(fieldController.colorNum) (i=>countOfPossibleNeighbors(0)))) calculateNeighborCounts(recDepth-1) else true
	}
	
	def recursion = recursionHandle.recursionDepth
	def recursion(newValue:Int) = recursionHandle = new RecursionHandler(fieldController,newValue,botID)
	 
}