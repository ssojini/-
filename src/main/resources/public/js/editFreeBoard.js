function deleteFile() {
	var checkbox = $("input[type=checkbox]");
	var aname = new Array();
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked) {
			aname.push(checkbox[i].className);
		}
	}
	console.log(aname);
	$.ajax({
		url:"/file/deleteFiles",
		method:"post",
		data:{},
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