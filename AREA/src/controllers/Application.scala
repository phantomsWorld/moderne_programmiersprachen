package controllers

import play.api._
import play.api.mvc._
import play.api.templates._
import model.Util

object Application extends Controller {
  var controller:Fieldcontroller = new Fieldcontroller
  
  def index = Action { Ok(views.html.index("AREA - Scala")) }
  
  def refreshFieldHtml = Action { Ok(controller.fieldToHtmlString) }
  def changeColorButton(newColor:String) = Action {
  	controller.changeColor((new Util).colorsStringToInt(newColor).asInstanceOf[Int])
  	Ok("Result")
  }
  
  def refreshField = Action {
    controller.refreshField
    Ok("refreshField-Result")
  }
  
  def botRecursion = Action { Ok(controller.readRecursion+"") }
  
  def resetBotRecursion(value:Int) = Action {
    controller.setRecursion(value)
    Ok(controller.readRecursion+"")
  }
  
  def saveGame(fileName:String) = Action {
    controller.saveXML(fileName)
	Ok("true")
  }
  
  def loadGame(fileName:String) = Action {
    controller = (new Util).loadGame(fileName)
    Ok("true")
  }
  
  def changeFieldSize(h:Int,w:Int) = Action {
    controller = new Fieldcontroller(h,w)
    Ok("Result")
  }
  
  def availableFiles = Action { Ok((new Util).listGames("public/games","HTML")) }
  def possessions = Action { Ok(controller.calculatedPossession(0)+";"+controller.calculatedPossession(1)) }
  
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
	     controllers.routes.javascript.Application.possessions)).as("text/javascript")
  }
}