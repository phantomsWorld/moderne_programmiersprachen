package controllers

import model.Util
import scala.actors.Actor
import scala.actors.remote._
import scala.actors.remote.RemoteActor._
import scala.actors.AbstractActor
import scala.swing.Publisher

class GameController extends Publisher {
  var controller = new Fieldcontroller

  def toString(infoType: String) = infoType match {
    case "HTML" => controller.fieldToHtmlString
    case _ => controller.toString
  }
  def resizeField(h: Int, w: Int) = {
    controller = new Fieldcontroller(h, w)
    publish(Util.FieldUpdate)
  }
  def refreshField = {
    controller.refreshField
    publish(Util.FieldUpdate)
    publish(Util.ClosePopup)
  }
  def changeColor(newColor: String) = {
    controller.changeColor(Util.colorsStringToInt(newColor).asInstanceOf[Int])
    publish(Util.FieldUpdate)
  }
  def changeColor(newColor: Int) = {
    controller.changeColor(newColor)
    publish(Util.FieldUpdate)
  }
  def readBotRecursion = controller.readRecursion
  def resetBotRecursion(value: Int) = controller.setRecursion(value)
  def saveGame(name: String) = controller.saveXML(name)
  def loadGame(name: String) = {
    controller = Util.loadGame(name)
    publish(Util.FieldUpdate)
  }
  def availableFiles(rootDir: String, listType: String) = Util.listGames(rootDir, listType)
  def readPossessions = controller.calculatedPossession
  def h = controller.field.h
  def w = controller.field.w
  def colorNum = controller.colorNum
  def cells = controller.field.cells
}
