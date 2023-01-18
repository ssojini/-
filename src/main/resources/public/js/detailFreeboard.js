function editFreeBoard(fbnum) {
	location.href = "/freeboard/edit?fbnum="+fbnum;
}

function addReply() {
	$("#reply_contents *").remove();
	$.ajax({
		url:"/freeboard/addReply",
		method:"post",
		data:{
			"pnum":$("#fbnum").text(),
			"author":$("#reply_author").text(),
			"contents":$("#reply_contents").val()
			},
		cache:false,
		dataType:"json",
		success:function(res) {
			refleshReply();
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function refleshReply() {
	$.ajax({
		url:"/freeboard/getReply",
		method:"post",
		data:{
			"pnum":$("#fbnum").text()
		},
		cache:false,
		dataType:"json",
		success:function(res) {
			appendReply(res.listReply);
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function appendReply(listReply) {
	let listReplyDiv = $("#listReply");
	$("#listReply *").remove();
	for (var i = 0; i < listReply.length; i++) {
		let $div = $("<div></div>");
		let $author = $("<span>"+listReply[i].author!=null?listReply[i].author:""+"</span>");
		let $contents = $("<span>"+listReply[i].contents+"</span>");
		let $datetime = $("<span>"+listReply[i].datetime+"</span>");
		$div.append($author + $contents + $datetime);
		listReplyDiv.append($div);
	}
}