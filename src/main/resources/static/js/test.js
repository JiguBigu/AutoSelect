$(function () {
    $("submit").on("click", function () {
        var acnt = $("#acnt").val();
        var pswd = $("#pswd").val();
        if (acnt && pswd) {
            var url = "http://127.0.0.1:8080/user/" + acnt + "/" + pswd;
            console.log(url);
            var httpRequest = new XMLHttpRequest();//第一步：建立所需的对象
            httpRequest.open('GET', 'url', true);//第二步：打开连接
            httpRequest.send();//第三步：发送请求  将请求参数写在URL中
            /**
             * 获取数据后的处理程序
             */
            httpRequest.onreadystatechange = function () {
                if (httpRequest.readyState == 4 && httpRequest.status == 200) {
                    var json = httpRequest.responseText;//获取到json字符串，还需解析
                    var res = JSON.parse(json);
                    if (res[0].success = true) {
                        window.location.href = 'admin.html';
                    } else {
                        alert("登录失败，请检查用户名和密码");
                    }
                }
            }
        } else {
            alert("请输入用户名及密码！");
        }
    })
    });