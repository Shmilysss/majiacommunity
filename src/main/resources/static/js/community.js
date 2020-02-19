/**
 * 发送评论
 */
function postComment() {
    var questionId = $("#question_id").val();
    var content = $("#question_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                // $("#comment_section").hide();
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    //未登录提示
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=241bdde1a21536f5cb90&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        datatype: "json"
    });
}

/**
 * 展示二级评论
 */
function collapseComment(Obj) {
    var id = Obj.getAttribute("data-id");
    $("#comment-"+id).toggleClass("in");
}