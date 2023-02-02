function addFreeboard() {
	if ($("#title").val() == '') {
		alert("제목을 입력하세요");
		$("#title").focus();
	} else {
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
						uploadFiles(res.freeboard);
					} else {
						alert("저장 성공");
						location.href = "/freeboard?bname="+$("#bname").val();
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
}

function updateContents(listAttach) {
	var img = $("#contents > img");
	for (var i = 0; i < img.length; i++) {
		for (var j = 0; j < listAttach.length; j++) {
			if (img[i].className == listAttach[j].aname) {
				img[i].src = "/images/" + listAttach[j].anum + "_" + listAttach[j].aname;
			}
		}
	}
	$.ajax({
		url:"/freeboard/updateContents",
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
				location.href = "/freeboard";
			}
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function uploadFiles(freeboard) {
	var data = getFormData();
	freeboard.contents = $("#contents").html();
	data.append("fbnum",freeboard.fbnum);
	if (data != null) {
	$.ajax({
		url: "/file/upload",
		method: "post",
		enctype: "multipart/form-data",
		data: data,
		cache: false,
		dataType: "json",
		processData: false,
		contentType: false,
		timeout: 600000,
		success: function(res) {
			updateContents(res.liAttach);
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
	changeFiles();
}

function changeFiles() {
	const files = $("#files")[0].files;
	$("#fileListDiv *").remove("");
	
	// 파일이 있을 때 이미지 넣기 버튼 활성화
	if (files.length > 0) {
		$("#insert_img_btn").attr("hidden",false);
	} else {
		$("#insert_img_btn").attr("hidden",true);
	}
	
	for (var i = 0; i < files.length; i++) {
		const file = files[i];
		if (isImage(file)) {
			$("#fileListDiv").append($("<div><input type='checkbox' value='"+files[i].name+"'>"+files[i].name+"</div>"));
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
			appendImage(input[i].value);
		}
	}
}

function insertImg2() {
	var input = $("input[type=checkbox]");
	for (var i = 0; i < input.length; i++) {
		if (input[i].checked == true) {
			$img = $("<img src='/images/"+input[i].value+"'>");
		}
	}
}

function appendImage(filename) {
	var imgtag = document.createElement("img");
	imgtag.className = filename;
	$("#contents").append(imgtag);
	showImage(getFileByName(filename),imgtag);
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

function showImage(file,img) {
	var fr = new FileReader();
	fr.onload = function() {
		img.src = fr.result;
	}
	fr.readAsDataURL(file);
}