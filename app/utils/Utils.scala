package utils

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.commons.io.FileUtils
import org.joda.time.DateTime
import play.api.mvc.{AnyContent, Request}

/**
  * Created by yz on 2017/6/16.
  */
object Utils {

  def random :String = {
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
     (0 to 10).map{i=>
      source.charAt((Math.random()*62).toInt)
    }.mkString
  }

  
  def getTime(startTime: Long) = {
    val endTime = System.currentTimeMillis()
    (endTime - startTime) / 1000.0
  }

  def deleteDirectory(direcotry: File) = {
    try {
      FileUtils.deleteDirectory(direcotry)
    } catch {
      case _ =>
    }
  }

  def deleteDirectory(tmpDir: String):Unit = {
    val direcotry = new File(tmpDir)
    deleteDirectory(direcotry)
  }

  def isDouble(value: String): Boolean = {
    try {
      value.toDouble
    } catch {
      case _: Exception =>
        return false
    }
    true
  }

  def refer(request:Request[AnyContent]):String = {
    val header = request.headers.toMap
    header.filter(_._1 == "Referer").map(_._2).head.head
  }

  def date : DateTime = {
    val now = new Date()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val time = dateFormat.format(now)
    val date = new DateTime(dateFormat.parse(time).getTime)
    return date
  }

  def date2 : String = {
    val now = new Date()
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    val date = dateFormat.format(now)
    date
  }


  val isWindow: Boolean = {
    System.getProperties.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1
  }

  val windowsPath = "I:/ncu_platform"
  val linuxPath = "/mnt/sdb/platform/resources/diversity_platform"
  val path = {
    if (new File(windowsPath).exists()) windowsPath else linuxPath
  }

  val dataPath = path + "/data"

}
