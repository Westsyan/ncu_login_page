package controllers

import java.io.File

import config.Number
import dao.AdminDao
import javax.inject.{Inject, Singleton}
import org.apache.commons.io.FileUtils
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.Utils

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AdminController@Inject()(cc:ControllerComponents,adminDao:AdminDao)
                              (implicit exec:ExecutionContext) extends AbstractController(cc){

  def adminPage = Action { implicit request =>
    Ok(views.html.admin.account())
  }


  def getAllInfo = Action.async { implicit request =>
    adminDao.getAllUser.map { x =>
      val json = x.map { y =>
        val user = y.account
        Json.obj("user" -> user)
      }
      Ok(Json.toJson(json))
    }
  }

  def deleteUser(id: Int) = Action.async { implicit request =>
    adminDao.deleteById(id).map { x =>
      val run = Future {
        FileUtils.deleteDirectory(new File(Utils.path + "/otu_platform/data/" + id))
        FileUtils.deleteDirectory(new File(Utils.path + "/parametron_platform/data/" + id))
        FileUtils.deleteDirectory(new File(Utils.path + "/transcriptome_platform/data/" + id))
        FileUtils.deleteDirectory(new File(Utils.path + "/resequencing_platform/data/" + id))
      }
      Ok(Json.toJson("success"))
    }
  }

  def platformHome = Action { implicit request =>
    Ok(views.html.index.home(request.domain))
  }



  def getUserDirSize(id:Int) = {
    val path = "/mnt/sdb/platform/resources/"
    val diversitySize = FileUtils.sizeOfDirectory(new File(path + "diversity_platform/data/" + id))

    val parametronSize = {
      FileUtils.sizeOfDirectory(new File(path + "parametron_platform/data/" + id)) +
        FileUtils.sizeOfDirectory(new File(path + "parametron_platform/species/" + id))
    }
    val transcriptomeSize = FileUtils.sizeOfDirectory(new File(path + "transcriptome_platform/data/" + id))
    val resequencing_platform = {
      FileUtils.sizeOfDirectory(new File(path + "resequencing_platform/data/" + id)) +
        FileUtils.sizeOfDirectory(new File(path + "resequencing_platform/species/" + id))
    }
    val allSize = (diversitySize + parametronSize + transcriptomeSize + resequencing_platform).toDouble/1024/1024
    if(allSize > 1024){
      (allSize/1024).formatted("%.2f") + "GB"
    }else{
      allSize.formatted("%.2f") + "MB"
    }
  }

  def getAllUser = Action.async { implicit request =>
    adminDao.getAllUser.map { x =>
      val json = x.filter(_.id != 1).map { y =>
        val (name, power) = if (y.id == 1) {
          (y.account, "管理员")
        } else {
          (y.account, "普通用户")
        }



        val operate = if (y.id == 1) {
          s"""<button class="update" onclick="updatePassword('${y.id}','${y.account}')" value="${y.account}" id="${y.id}" title="重置密码"><i class="fa fa-repeat"></i></button>
           """.stripMargin
        } else {
          s"""
             |<button class="update" onclick="updatePassword('${y.id}','${y.account}')" value="${y.account}" id="${y.id}" title="重置密码"><i class="fa fa-repeat"></i></button>
         """.
            stripMargin
        }
        Json.obj("user" -> name, "power" -> power, "operation" -> operate)
      }
      Ok(Json.toJson(json))
    }

  }

  case class passwordData(uid: Int, password: String)

  val passwordForm = Form(
    mapping(
      "uid" -> number,
      "password" -> text
    )(passwordData.apply)(passwordData.unapply)
  )

  def updatePassword = Action.async { implicit request =>
    val data = passwordForm.bindFromRequest.get
    adminDao.updatePassword(data.uid, data.password).map { x =>
      Ok(Json.toJson("success"))
    }
  }

  def deleteTmp = Action{implicit request=>

    val run = Future {
      this.synchronized{
        FileUtils.deleteDirectory(new File(Utils.path + "/otu_platform/tmp"))
        FileUtils.deleteDirectory(new File(Utils.path + "/parametron_platform/tmp"))
        FileUtils.deleteDirectory(new File(Utils.path + "/transcriptome_platform/tmp"))
        FileUtils.deleteDirectory(new File(Utils.path + "/resequencing_platform/tmp"))

        new File(Utils.path + "/otu_platform/tmp").mkdir()
        new File(Utils.path + "/parametron_platform/tmp").mkdir()
        new File(Utils.path + "/transcriptome_platform/tmp").mkdir()
        new File(Utils.path + "/resequencing_platform/tmp").mkdir()
      }
    }
    Ok(Json.toJson("success"))
  }

  def getDisk = Action { implicit request =>
    val file = new Number(Utils.path)
    val free = file.getFree
    val total = file.getTotal
    val use = total - free
    val per = use/total *100
    val json = Json.obj("all" -> total, "use" -> use.formatted("%.2f"), "per" -> per.formatted("%.2f"))
    Ok(Json.toJson(json))
  }

}
