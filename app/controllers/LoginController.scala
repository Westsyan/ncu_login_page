package controllers

import java.io.File

import dao.AdminDao
import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import utils.Utils

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

class LoginController@Inject()(cc:ControllerComponents,adminDao:AdminDao)
                              (implicit exec:ExecutionContext) extends AbstractController(cc) {

  def admin = Action {
    Ok(views.html.adminAndLogin.admin())
  }

  case class userData(account: String, password: String)

  val userForm = Form(
    mapping(
      "account" -> text,
      "password" -> text
    )(userData.apply)(userData.unapply)
  )

  def login = Action.async { implicit request =>
    val data = userForm.bindFromRequest.get
    val account = data.account
    val password = data.password
    adminDao.selectByName(account, password).map { x =>
        val (valid, message) = if(x.isDefined){("true","")}else{("false", "用户名或密码错误")}
        val json = Json.obj("valid" -> valid, "message" -> message)
        Ok(Json.toJson(json))
    }
  }

  def toIndex(account:String) : Action[AnyContent]=Action.async{ implicit request=>
    val session = new Session
    adminDao.getIdByAccount(account).map{x=>
      if(x ==1 ){
        Redirect(routes.AdminController.adminPage()).withNewSession.withSession(session + ("admin" -> account)+("id" -> x.toString))
      }else{
        Redirect(routes.AdminController.platformHome()).withNewSession.withSession(session + ("user" -> account)+("id" -> x.toString))
      }
    }
  }

  def logout = Action {implicit request=>
      Redirect(routes.LoginController.admin()).withNewSession
  }

  def sign = Action {
    Ok(views.html.adminAndLogin.login())
  }

  def signsuccess(account: String, password: String): Action[AnyContent] = Action { implicit request =>
    val row =(account,password)
    Await.result(adminDao.addAccount(Seq(row)),Duration.Inf)
    val id = Await.result(adminDao.getIdByAccount(account),Duration.Inf)
   new File(Utils.path + "/otu_platform/data/" + id).mkdirs()
   new File(Utils.path + "/parametron_platform/data/" + id).mkdirs()
   new File(Utils.path + "/resequencing_platform/data/" + id).mkdirs()
   new File(Utils.path + "/transcriptome_platform/data/" + id).mkdirs()
    Ok(views.html.adminAndLogin.signSuccess())
  }

  def toSuccess = Action {
    Ok(views.html.adminAndLogin.signSuccess())
  }

  case class accountData(account: String)

  val accountForm = Form(
    mapping(
      "account" -> text
    )(accountData.apply)(accountData.unapply)
  )

  def checkAccount = Action.async { implicit request =>
    val data = accountForm.bindFromRequest.get
    val account = data.account
    adminDao.selectName(account).map { x =>
      val valid = if (x.size == 0) {
        "true"
      } else {
        "false"
      }
      val host = request.domain
      val message = s"用户名已存在！<a href='http://${host}'>点击去登录</a>"
      val json = Json.obj("valid" -> valid, "message" -> message)
      Ok(Json.toJson(json))
    }
  }





}
