$(function () {
    $("#submit").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        $.ajax({
            url:"/personInfo/checkPersonInfo?username="+username+"&password="+password,
            type:"POST",
            success:function (data) {
                if(data.code===100){
                    window.location.href = "/"
                }else {
                    $("#modal_hint").text(data.extend.msg);
                    $("#modal").modal({
                        backdrop:'static'
                    });
                    $("#message_dismiss").click(function () {
                        window.location.href = "/userLogin";
                    })
                }
            }
        })
    });
})