function editFreeBoard(fbnum) {
	location.href = "/freeboard/edit?fbnum="+fbnum;
}

function addReply() {
	$.ajax({
		url:"/freeboard/addReply",
		method:"post",
		data:{
			"pnum":$("#fbnum").text(),
			"contents":$("#reply_contents").val()
			},
		cache:false,
		dataType:"json",
		success:function(res) {
			$("#reply_contents").val("");
			location.href = "/freeboard/detail?fbnum="+$("#fbnum").text();
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function deleteReply(num) {
	$.ajax({
		url:"/freeboard/deleteReply",
		method:"post",
		data:{
			"num":num
		},
		cache:false,
		dataType:"json",
		success:function(res) {
			location.href = "/freeboard/detail?fbnum="+$("#fbnum").text();
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

function clickLikeCount() {
	$.ajax({
		url:"/freeboard/likeCount",
		method:"post",
		data:{
			"fbnum":$("#fbnum").text()
		},
		cache:false,
		dataType:"json",
		success:function(res) {
			if(res.result) {
				$("#likeCountImg").attr('src','/images/likeCount2.png');
				$("#likecount").text(parseInt($("#likecount").text())+1);
			} else {
				$("#likeCountImg").attr('src','/images/likeCount.png');
				$("#likecount").text(parseInt($("#likecount").text())-1);
			}
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}