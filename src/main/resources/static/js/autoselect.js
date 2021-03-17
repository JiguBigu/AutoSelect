/**
 * 在文本框内显示用户上传文件的路径
 */
var fileName = null;

function onFileName() {
    document.getElementById("fileName").value = document.getElementById("uploadFile").value;
}

function upload() {
    var test;
        var file = new FormData();
        file.append("file", document.getElementById("uploadFile").files[0]);
        $.ajax({
            type : 'POST',	//传输类型
            async : false,	//同步执行
            processData: false,
            contentType: false,
            url : 'http://127.0.0.1:8082/AutoSelect/file/upload',	//web.xml中注册的Servlet的url-pattern
            data : file,
            dataType : 'json', //返回数据形式为json
            success : function(res) {
                if (res.success == false) {
                    alert(res.errMsg);
                }else{
                    alert("文件：" + res.fileName + "上传成功！ ");
                    fileName = res.fileName
                    console.log("first fileName on another js:" + fileName);
                    test = res.fileName;
                }
            },
            error : function(err) {
                alert("上传失败:" + err.errMsg);
            }
        })
}

function runAlgorithm() {
    if(fileName == null){
        alert("请先上传文件再运行算法");
        return;
    }
    for(var i = 0; i < 40; i = i + 0.01){
        document.getElementById("bar").style.width = i + "%";
        if(i == 20 || i == 34){
            for(var j = 0; j < 1000; j++){}
        }
    }
    console.log("fileName III:" + fileName);
    $.ajax({
        type : "GET",
        async : false,	//同步执行
        url : 'http://127.0.0.1:8082/AutoSelect/algorithm/run/ga',
        data:{"fileName":fileName},
        dataType : 'json', //返回数据形式为json
        success : function(res2) {
            //进度条
            for(var i = 41; i <= 100; i++){
                document.getElementById("bar").style.width = i + "%";

            }
            //成功提示
            document.getElementById("fileName").value = "运行算法成功";
            alert("运行算法成功！");
            //显示优化结果
            // var str = "优化效果最佳算法：" + res2.name + "\n最短时间：" + res2.data.time +
            //     "\n工序号: [" + res2.data.p + "]\n机器号: [" + res2.data.m + "]";
            var str = res2.configInfo + "\n加工时间：" + res2.data.time +
                "\n工序号: [" + res2.data.p + "]\n机器号: [" + res2.data.m + "]";
            document.getElementById("result").style.display = "block";
            document.getElementById("result").value = str;
            //重置进度条
            document.getElementById("bar").style.width = 0 + "%";
        },
        error : function(err) {
            alert("运行算法失败失败:" + err.errMsg);
        }
    });
}

