function editFreeBoard(fbnum) {
	location.href = "/freeboard/edit?fbnum="+fbnum;
}

function addReply() {
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
			$("#reply_contents").val("");
			//refleshReply();
			location.href = "/freeboard/detail?fbnum="+$("#fbnum");
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
		let $tr = $("<tr></tr>");
		let $author = $("<td>"+listReply[i].author!=null?listReply[i].author:""+"</td>");
		let $contents = $("<td>"+listReply[i].contents+"</td>");
		let $datetime = $("<td>"+listReply[i].datetime+"</td>");
		$tr.append($author);
		$tr.append($contents);
		$tr.append($datetime);
		listReplyDiv.append($tr);
	}
}