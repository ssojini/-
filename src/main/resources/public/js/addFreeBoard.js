function addFreeBoard() {
	$.ajax({
		url: "/freeboard/add",
		method: "post",
		data: {
			"bname": $("#bname").val(),
			"title": $("#title").val(),
			"author": $("#author").val(),
			"contents": $("#contents").html()
		},
		dataType: "json",
		cache: false,
		success: function(res) {
			if (res.result) {
				if ($("#files")[0].files.length != 0) {
					uploadFiles(res.freeBoard);
				} else {
					alert("저장 성공");
					location.href = "/freeboard";
				}
			} else {
				alert("저장 실패");
			}
		},
		error: function(xhs, status, err) {
			alert(err);
		}
	});
}

function changeSrc(listAttach) {
	var img = $("#contents > img");
	for (var i = 0; i < img.length; i++) {
		for (var i = 0; i < listAttach.length; i++) {
			if (img[i].className == listAttach[i].aname) {
				img[i].src = "/images/" + listAttach[i].anum + "_" + img[i].className;
			}
		}
	}
	console.log("listAttach:"+listAttach);
	$.ajax({
		url:"/health/changeSrc",
		method:"post",
		data:{
			"fbnum":listAttach[0].fbnum,
			"contents":$("#contents").html()
			},
		cache:false,
		dataType:"json",
		success:function(res) {
			alert(res.result?"저장 성공":"저장 실패");
			if (res.result) {
				location.href = "/freeBoard";
			}
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function uploadFiles(freeBoard) {
	var data = getFormData();
	freeBoard.contents = $("#contents").html();
	data.append("fbnum",freeBoard.fbnum);
	if (data != null) {
	$.ajax({
		url: "/file/uploadFiles",
		method: "post",
		enctype: "multipart/form-data",
		data: data,
		cache: false,
		dataType: "json",
		processData: false,
		contentType: false,
		timeout: 600000,
		success: function(res) {
			console.log("listAttach:"+res.listAttach);
			changeSrc(res.listAttach);
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

	if (files.files.length === 0) {
		return null;
	}
	const formData = new FormData();
	for (var i = 0; i < files.files.length; i++) {
		formData.append("files", files.files[i]);
	}
	for (var key of formData.keys()) {
	}
	for (var value of formData.values()) {
	}
	return formData;
}

function changeFile() {
	const files = $("#files")[0];
	$("#fileListDiv *").remove("");
	for (var i = 0; i < files.files.length; i++) {
		const file = files.files[i];
		if (isImage(file)) {
			$("#fileListDiv").append($("<span><input type='checkbox' value='"+files.files[i].name+"'>"+files.files[i].name+"</span>"));
		}
	}
	$("#contents > img").remove();
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

function insertImg() {
	var input = $("input[type=checkbox]");
	for (var i = 0; i < input.length; i++) {
		if (input[i].checked == true) {
			appendImage(getFileByName(input[i].value));
		}
	}
}

function getFileByName(filename) {
	var files = $("#files")[0].files;
	for (var i = 0; i < files.length; i++) {
		if (files[i].name == filename) {
			return files[i];
		}
	}
	return null;
}

function appendImage(file) {
	if (document.getElementById(file.name)) {
		var i = 0;
		while (true) {
			var filename = file.name + "_" + i;
			if (document.getElementById(filename)) {
				++i;
			} else {
				var $img = $("<img id='" + filename + "' class='"+file.name+"'>");
				$("#contents").append($img);
				showImage(file,filename);
				break;
			}
		}
	} else {
		var $img = $("<img id='" + file.name + "' class='"+file.name+"'>");
		$("#contents").append($img);
		showImage(file,file.name);
	}
}

function showImage(file,id) {
	var fr = new FileReader();
	fr.onload = function() {
		document.getElementById(id).src = fr.result;
	}
	fr.readAsDataURL(file);
}