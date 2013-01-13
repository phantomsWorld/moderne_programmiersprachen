package controllers

import model.Util
import scala.actors.Actor
import scala.actors.remote._
import scala.actors.remote.RemoteActor._
import scala.actors.AbstractActor
import scala.swing.Publisher

class GameController extends Publisher {
  var controller = new Fieldcontroller
  
  def toString(infoType:String) = infoType match {
    case "HTML" => controller.fieldToHtmlString
    case _		=> controller.toString
  }
  def resizeField(h:Int,w:Int) = {
    controller = new Fieldcontroller(h,w)
    publish(Util.FieldUpdate)
  }
  def refreshField = {
    controller.refreshField
    publish(Util.FieldUpdate)
    publish(Util.ClosePopup)
    //updateUIs
  }
  def changeColor(newColor:String) = {
    controller.changeColor(Util.colorsStringToInt(newColor).asInstanceOf[Int])
    publish(Util.FieldUpdate)
    //updateUIs
  }
  def changeColor(newColor:Int) = {
    controller.changeColor(newColor)
    publish(Util.FieldUpdate)
    //updateUIs
  }
  def readBotRecursion = controller.readRecursion
  def resetBotRecursion(value:Int) = {
    controller.setRecursion(value)
    publish(Util.FieldUpdate)
  }
  def saveGame(name:String) = controller.saveXML(name)
  def loadGame(name:String) = {
    controller = Util.loadGame(name)
    publish(Util.FieldUpdate)
  }
  def availableFiles(rootDir:String, listType:String) = Util.listGames(rootDir,listType)
  def readPossessions = controller.calculatedPossession
  def h = controller.field.h
  def w = controller.field.w
  def colorNum = controller.colorNum
  def cells = controller.field.cells
  
  /*def updateUIs = {
    games.foreach(actor => {
      println("Repaint")
      
      actor ! "Repaint"
    })
  }
  
  var games:List[AbstractActor] = Nil
  //println("Before Port")
  val port = Util.openPort()
  //println("Port Controller: "+port)
  
    
    val game =select(Node("localhost",9002),'Game9002)
    val game3 =select(Node("localhost",9003),'Game9003)
    
  def act() {
    alive(port)
    register('ControllerActor, this)
    games = List(game):::List(game3):::games
  
    loop{
      react{
        case _:Int => {
          Console.println("=== Add Actor ===")
          val actor = select(Node("localhost",port),Symbol("Game"+port.toString))
          println(actor)
          //games = List(actor):::games
        }
        case _ => Console.println("=== BLUBB ===")
      }
    }
  }
  
  start*/
}