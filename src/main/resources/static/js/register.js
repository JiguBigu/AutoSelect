var verSuccess = false;
function register() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var agnPassword = document.getElementById("second_password").value;
    console.log("username:" + username);
    console.log("password:" + password);
    console.log("verification2:" + verSuccess);

    if(password != agnPassword){
        alert("两次输入密码不同！");
        return;
    }

    if(verSuccess){
        verRegister();
    } else {
        alert("请拖动滑块证明你不是机器人");
    }
}

function verRegister() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : 'http://127.0.0.1:8082/AutoSelect/user/register',	//web.xml中注册的Servlet的url-pattern
        data : { "username":username, "password":password},
        dataType : 'JSON', //返回数据形式为json
        success : function(registerSuccess) {
            if (registerSuccess.success == false) {
                alert(registerSuccess.errMsg);
            } else {
                alert("注册成功，请前往登录！");
                window.location.href="login.html";
            }
        },
        error : function(errorMsg) {
            console.log("Register Erro !" + errorMsg);
            console.log("data:" + errorMsg.success);
        }
    })
}