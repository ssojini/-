function addAdminBoard() {
	
	
	$.ajax({
		url: "/health/writeAdmin",
		method: "post",
		data: {
			"name": $("#name").val(),
			"title": $("#title").val(),
			"author": $("#author").val(),
			"content": $("#content").val()
		},
		dataType: "json",
		cache: false,
		success: function(res) {
		if(res.added){
			alert("저장 되었습니다.");
		}
		},
		error: function(xhs, status, err) {
			alert(err);
		}
	});
}