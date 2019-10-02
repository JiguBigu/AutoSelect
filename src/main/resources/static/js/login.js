function login() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    console.log("username:" + username);
    console.log("password:" + password);
    $.ajax({
        type : 'POST',	//传输类型
        async : false,	//同步执行
        url : 'http://127.0.0.1:8082/AutoSelect/user/login',	//web.xml中注册的Servlet的url-pattern
        data : { "username":username, "password":password},
        dataType : 'json', //返回数据形式为json
        success : function(res) {
            if (res.success == false) {
                alert("登录错误:" + res.errMsg);
            }else{
                alert("登陆成功！");
                window.location.href="autoselect.html";
            }
        },
        error : function(err) {
            alert("login erro:" + err.errMsg);
        }
    });
}
