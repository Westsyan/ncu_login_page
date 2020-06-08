package controllers

import dao.AdminDao
import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class PwdController@Inject()(cc:ControllerComponents,adminDao:AdminDao)
                            (implicit exec:ExecutionContext) extends AbstractController(cc){


  def pwdPage = Action{implicit request=>
    Ok(views.html.password.pwdPage())
  }

  def pwdAdminPage = Action{implicit request=>
    Ok(views.html.password.pwdAdminPage())
  }

  case class resetPwdData(id:Int,pwd:String)

  val resetPwdForm = Form(
    mapping(
      "id" -> number,
      "pwd" -> text
    )(resetPwdData.apply)(resetPwdData.unapply)
  )

  def resetPwd = Action{implicit request=>
    val data =resetPwdForm.bindFromRequest.get
    val id = request.session.get("id").get.toInt
    if(id == 1){
     val x = Await.result(adminDao.checkPwd(1,data.pwd),Duration.Inf)
      if(x){
          Await.result(adminDao.updatePassword(data.id,"123456"),Duration.Inf)
          Ok(Json.obj("valid" -> "true"))
        }else{
          Ok(Json.obj("valid"->"false"))
        }
    }else{
      Ok(Json.obj("valid"->"false"))
    }
  }

  case class updatePwdData(pwd:String,newPwd:String)

  val updatePwdForm = Form(
    mapping(
      "pwd" -> text,
      "newPwd" -> text
    )(updatePwdData.apply)(updatePwdData.unapply)
  )

  def updatePwd = Action.async{implicit request=>
    val data = updatePwdForm.bindFromRequest.get
    val id = request.session.get("id").get.toInt
    adminDao.checkPwd(id,data.pwd).map{x=>
      if(x){
        Await.result(adminDao.updatePassword(id,data.newPwd),Duration.Inf)
        Ok(Json.obj("valid" -> "true"))
      }else{
        Ok(Json.obj("valid"->"false"))
      }
    }
  }

}
