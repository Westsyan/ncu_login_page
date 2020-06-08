package test

import java.io.File

import config.Number
import org.apache.commons.io.FileUtils
import play.api.libs.json.Json

object test1 {


  def main(args: Array[String]): Unit = {
    val file = new Number("D:\\甘肃分析云平台资料\\BSA数据")
    val free = file.getFree
    val total = file.getTotal
    val use = total - free
    val per = use/total *100
    val json = Json.obj("all" -> total, "use" -> use.formatted("%.2f"), "per" -> per.formatted("%.2f"))

    println(FileUtils.sizeOfDirectory(new File("D:\\甘肃分析云平台资料\\BSA数据")).toDouble/1024/1024/1024)
    println(total)
    println( use.formatted("%.2f"))
    println( per.formatted("%.2f"))
  }
}
