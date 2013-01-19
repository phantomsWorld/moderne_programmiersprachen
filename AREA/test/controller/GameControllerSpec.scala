package controller

import org.specs2.mutable._
import controllers.GameController

/**
 * Because the class GameController mainly only forwards the 
 * execution to the FieldController, most functions are tested in the
 * FieldControllerSpec 
 * 
 * @author steffenkollosche
 *
 */

class GameControllerSpec extends SpecificationWithJUnit{
  
  "A new Game Controller" should{
    val gameController = new GameController
    "have an Fieldcontroller with default values player = 2 width&height = 8" in{
      gameController.controller.p must be_==(2)
      gameController.w must be_==(8)
      gameController.h must be_==(8)
    }
    
    "resize the field correctly" in{
      gameController.resizeField(4, 4)
      gameController.w must be_==(4)
      gameController.h must be_==(4)
    }
  }

}