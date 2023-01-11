function addFreeBoard() {
	$.ajax({
		url: "/health/addFreeBoard",
		method: "post",
		data: {
			"bname": $("#bname").val(),
			"title": $("#title").val(),
			"author": $("#author").val(),
			"contents": $("#contents").text()
		},
		dataType: "json",
		cache: false,
		success: function(res) {
			if (res.result) {
				uploadFiles(parseInt(res.fbnum));
			} else {
				alert("저장 실패");
			}
		},
		error: function(xhs, status, err) {
			alert(err);
		}
	});
}

function uploadFiles(fbnum) {
	var data = getFormData();
	data.append("fbnum",fbnum);
	if (data != null) {
	$.ajax({
		url: "/health/uploadFiles",
		method: "post",
		enctype: "multipart/form-data",
		data: data,
		cache: false,
		dataType: "json",
		processData: false,
		contentType: false,
		timeout: 600000,
		success: function(res) {
			alert(res.result?"저장 성공":"저장 실패");
		},
		error: function(xhs, status, err) {
			alert(err);
		}
	});
	}
}

function getFormData() {
	const files = $("#files")[0];
	// files라는 객체에 담긴다
	console.log("files: ", files.files);
	console.log("files.length: ", files.files.length);

	if (files.files.length === 0) {
		return null;
	}
	const formData = new FormData();
	for (var i = 0; i < files.files.length; i++) {
		formData.append("files", files.files[i]);
	}
	console.log("formData: ", formData);
	for (var key of formData.keys()) {
		console.log("key: ", key);
	}
	for (var value of formData.values()) {
		console.log("value: ", value);
	}
	return formData;
}

function changeFile() {
	const files = $("#files")[0];
	console.log(files.files);
	$("#fileListDiv *").remove("");
	for (var i = 0; i < files.files.length; i++) {
		console.log(files.files[i]);
		$("#fileListDiv").append($("<span><input type='checkbox' value='"+files.files[i].name+"'>"+files.files[i].name+"</span>"));
	}
}

function isImage(file) {
	if (file.type == "image/png") {
		return true;
	}
	if (file.type == "image/jpeg") {
		return true;
	}
	return false;
}

function insertFile() {
	const files = $("#files")[0];
	for (var i = 0; i < files.files.length; i++) {
		var file = files.files[i];
		console.log(file);
		var $img = $("<img id='img_"+i+"' src=''></img>")
		$("#contents").append($img);
		showImage(file,"img_"+i);
	}
}

function showImage(file, id) {
	var fr = new FileReader();
	fr.onload = function() {
		console.log(id);
		console.log(document.getElementById(id));
		console.log(fr.result);
		document.getElementById(id).src = fr.result;
	}
	fr.readAsDataURL(file);
}