function addFreeBoard() {
	$.ajax({
		url: "/health/addFreeBoard",
		method: "post",
		data: {
			"bname": $("#bname").val(),
			"title": $("#title").val(),
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
}