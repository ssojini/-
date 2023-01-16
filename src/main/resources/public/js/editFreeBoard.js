function deleteFile() {
	var checkbox = $("input[type=checkbox]");
	var arr = new Array();
	var obj = new Object();
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked) {
			console.log(checkbox[i]);
		}
	}
	$.ajax({
		url:"/health/deleteFiles",
		method:"post",
		data:data,
		cache:false,
		dataType:"json",
		success:function(res) {
			alert(res.result);
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}