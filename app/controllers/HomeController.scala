package controllers

import dao.AdminDao
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class HomeController@Inject()(cc:ControllerComponents,adminDao:AdminDao)
                              (implicit exec:ExecutionContext) extends AbstractController(cc){


  def platform(plat:String) = Action{implicit request=>
    Ok(views.html.iframe.platformIframe())
  }

  def adminSpecies(plat:String) = Action{implicit request=>
    Ok(views.html.iframe.speciesIframe())
  }

}
