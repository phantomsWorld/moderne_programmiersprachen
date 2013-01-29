package controllers

import play.api._
import play.api.mvc._
import play.api.templates._
import model.Util
import views._

object Application extends Controller {
  var controller = new GameController
  
  def index = Action { Ok(views.html.index("AREA - Scala")) }
  
  def refreshFieldHtml = Action { Ok(controller.toString("HTML")) }
  def changeColorButton(newColor:String) = Action {
    controller.changeColor(newColor)
  	Ok("Result")
  }
  
  def refreshField = Action {
    controller.refreshField
    Ok("refreshField-Result")
  }
  
  def botRecursion = Action { Ok(controller.readBotRecursion+"") }
  
  def resetBotRecursion(value:Int) = Action {
    controller.resetBotRecursion(value)
    Ok(controller.readBotRecursion+"")
  }
  
  def saveGame(fileName:String) = Action {
    controller.saveGame(fileName)
	Ok("true")
  }
  
  def loadGame(fileName:String) = Action {
    controller.loadGame(fileName)
    Ok("true")
  }
  
  def changeFieldSize(h:Int,w:Int) = Action {
    controller.resizeField(h,w)
    Ok("Result")
  }
  
  def availableFiles = Action { Ok(controller.availableFiles("public/games","HTML")) }
  def possessions = Action { Ok(controller.readPossessions(0)+";"+controller.readPossessions(1)) }
  def readInactColors = Action { Ok("True;False")}//controller.inactiveColors) }
  
  def javascriptRoutes = Action { implicit request =>
    import routes.javascript._
	Ok(Routes.javascriptRouter("jsRoutes")
	    (controllers.routes.javascript.Application.changeColorButton,
	     controllers.routes.javascript.Application.refreshFieldHtml,
	     controllers.routes.javascript.Application.refreshField,
	     controllers.routes.javascript.Application.botRecursion,
	     controllers.routes.javascript.Application.resetBotRecursion,
	     controllers.routes.javascript.Application.saveGame,
	     controllers.routes.javascript.Application.loadGame,
	     controllers.routes.javascript.Application.changeFieldSize,
	     controllers.routes.javascript.Application.availableFiles,
	     controllers.routes.javascript.Application.possessions,
	     controllers.routes.javascript.Application.readInactColors)).as("text/javascript")
  }
}