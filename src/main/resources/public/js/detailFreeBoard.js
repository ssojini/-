function deleteFreeBoard(fbnum) {
	$.ajax({
		url:"/freeboard/delete",
		method:"post",
		data:{"fbnum":fbnum},
		dataType:"json",
		cache:false,
		success:function(res) {
			alert(res.result?"삭제 성공":"삭제 실패");
			if (res.result) {
				location.href = "/freeBoard";
			}
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function editFreeBoard(fbnum) {
	location.href = "/freeboard/edit?fbnum="+fbnum;
}