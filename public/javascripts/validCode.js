var code ; //在全局定义验证码

function createCode() {
    code = "";
    let codeLength = 4;//验证码的长度
    let checkCode = document.getElementById("code");
    let random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);//随机数
    for (let i = 0; i < codeLength; i++) {//循环操作
        let index = Math.floor(Math.random() * 10);//取得随机数的索引（0~35）
        code += random[index];//根据索引取得随机数加到code上
    }
    checkCode.value = code;//把code值赋给验证码
}