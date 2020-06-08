/**
 * Created by yz on 2017/6/20.
 */
var html=" "

function cpm(obj,title) {
    var html = "    <div id=\"updateSuccess\" class=\"modal fade modal-margin\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" style=\"margin-top: 200px;\">\n" +
        "        <div class=\"modal-dialog\" style=\"width: 400px;\">\n" +
        "            <div class=\"modal-content\">\n" +
        "                <div class=\"modal-header bg-primary\">\n" +
        "                    <h4 class=\"modal-title\" align=\"center\" id=\"successTitle\">\n" +
        "                        <span id=\"deleteTitle\" style=\"font-weight: bold;\">"+ title +"</span>\n" +
        "                    </h4>\n" +
        "                </div>\n" +
        "                <div class=\"row-fluid\" align=\"center\" >\n" +
        "                    <div id=\"successIcon\">\n" +
        "                        <img src=\"/assets/images/success.png\">\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "                <div class=\"modal-footer bg-info\">\n" +
        "                    <div align=\"center\">\n" +
        "                        <button type=\"button\" class=\"btn green\" onclick=\"sureSuccess(this)\" style=\"width: 100px;\" id=\"successBtn\" value='"+ obj+"'>确定</button>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>";

    $(".page-content").append(html);
    $("#updateSuccess").modal("show");

}

function sureSuccess(obj) {
    var state = obj.value;
    $("#updateSuccess").modal("hide");
    console.log(state)
    if(state == "table"){
        updateTable();
    }else{
        location.reload();
    }
}

function deleteBefore() {
    $("#title1").show();
    $("#title2").hide();
    $("#title3").hide();
    $("#warn1").show();
    $("#warn2").hide();
    $("#warn3").hide();
    $("#btn1").show();
    $("#btn2").show();
    $("#btn3").hide();
}

function deleting() {
    $("#title1").hide();
    $("#title3").hide();
    $("#title2").show();
    $("#warn1").hide();
    $("#warn3").hide();
    $("#warn2").show();
    $("#btn1").hide();
    $("#btn2").hide();
    $("#btn3").hide();
}

function deleteAfter() {
    $("#title1").hide();
    $("#title2").hide();
    $("#title3").show();
    $("#warn1").hide();
    $("#warn2").hide();
    $("#warn3").show();
    $("#btn1").hide();
    $("#btn2").hide();
    $("#btn3").show();
}


function deleteTmpBefore() {
    $(".title1").show();
    $(".title2").hide();
    $(".title3").hide();
    $(".warn1").show();
    $(".warn2").hide();
    $(".warn3").hide();
    $(".btn1").show();
    $(".btn2").show();
    $(".btn3").hide();
}

function deleteTmpAfter() {
    $(".title1").hide();
    $(".title2").hide();
    $(".title3").show();
    $(".warn1").hide();
    $(".warn2").hide();
    $(".warn3").show();
    $(".btn1").hide();
    $(".btn2").hide();
    $(".btn3").show();
}

function deleteFailed(message) {
    $("#deleteShow").modal("hide");
    let html = "\n" +
        "    <div id=\"deleteShow\" class=\"modal fade modal-margin\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\" style=\"margin-top: 200px;\">\n" +
        "        <div class=\"modal-dialog\" style=\"width: 400px;\">\n" +
        "            <div class=\"modal-content\">\n" +
        "                <div class=\"modal-header bg-primary\">\n" +
        "                    <h4 class=\"modal-title\" align=\"center\" id=\"title1\">\n" +
        "                        <span style=\"font-weight: bold\">发生错误</span>\n" +
        "                    </h4>\n" +
        "                </div>\n" +
        "                <div class=\"row-fluid\" align=\"center\" >\n" +
        "                    <h4>\n" + message +
        "                    </h4>\n" +
        "                </div>\n" +
        "                <div class=\"modal-footer bg-info\">\n" +
        "                    <div align=\"center\">\n" +
        "                        <button type=\"button\" class=\"btn green\" onclick=\"updateDelete()\" style=\"width: 100px;\n" +
        "                         \">确定</button>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n";
    $("#model").empty();
    $("#model").html(html);
    $("#deleteShow").modal("show");
}