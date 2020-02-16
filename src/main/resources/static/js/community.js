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
                $("#comment_section").hide();
            } else {
                alert("response=="+response);
            }
        },
        datatype: "json"
    });
}