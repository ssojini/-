function addFreeBoard() {
	uploadFiles();
	
	/*
	$.ajax({
		url: "/health/addFreeBoard",
		method: "post",
		data: {
			"bname": $("#bname").val(),
			"title": $("#title").val(),
			"author": $("#author").val(),
			"contents": $("#contents").val()
		},
		dataType: "json",
		cache: false,
		success: function(res) {
			alert(res.result);
		},
		error: function(xhs, status, err) {
			alert(err);
		}
	});
	*/
}

function uploadFiles() {
	$.ajax({
		url:"/health/uploadFiles",
		method:"post",
		enctype: "multipart/form-data",
		data:new FormData($("#uploadForm")[0]),
		cache:false,
		dataType:"json",
		processData:false,
		contentType:false,
		timeout:600000,
		success:function(res) {
			alert(res.result);
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}