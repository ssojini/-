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
				"contents": $("#contents").html()
			},
			dataType: "json",
			cache: false,
			success: function(res) {
				if (res.result) {
					alert("저장 성공");
					location.href = "/freeboard";
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

function uploadFiles(fbnum) {
	var data = getFormData();
	data.append("fbnum",fbnum);
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
			if (res.result) {
				alert("파일업로드 성공");
			} else {
				alert("파일업로드 실패");
			}
		},
		error: function(xhs, status, err) {
			alert(err);
		}
	});
	}
}

function changeFile(fbnum) {
	$.ajax({
		url:"/file/list",
		method:"post",
		data:{
			"fbnum":fbnum
		},
		cache:false,
		dataType:"json",
		success:function(res) {
			if (res.result) {
				var listAttach = res.listAttach;
				for (var i = 0; i < listAttach.length; i++) {
					var attach = listAttach[i];
					var $attachDiv = $("<div id='attach"+attach.anum+"'></div>");
					var $checkbox = $("<input type='checkbox' value='"+attach.anum+"'>");
					$attachDiv.append($checkbox);
					var $anumSpan = $("<span id='anum"+attach.anum+"'>"+attach.anum+"</span>");
					$attachDiv.append($anumSpan);
					var $anameSpan = $("<span id='aname"+attach.anum+"'>"+attach.aname+"</span>");
					$attachDiv.append($anameSpan);
					var $asizeSpan = $("<span id='asize"+attach.anum+"'>"+attach.asize+"</span>");
					$attachDiv.append($asizeSpan);
					$("#listAttach").append($attachDiv);
				}
			} else {
				alert("파일불러오기 실패");
			}
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
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
	var attachInput = $("#listAttach input[type=checkbox]");
	for (var i = 0; i < attachInput.length; i++) {
		if (attachInput[i].checked == true) {
			$img = $("<img src='/images/"+attachInput[i].value + "_" + $("#aname"+attachInput[i].value).text() + "'>");
			$("#contents").append($img);
		}
	}
}

function deleteFile() {
	var checkbox = $("#listAttach input[type=checkbox]");
	var arrAttach = new Array();
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked) {
			var div = $("#attach"+checkbox[i].value);
			var child = div.children();
			var fbnum = $("#fbnum").val();
			var anum = child.eq(1).text();
			var aname = child.eq(2).text();
			var asize = child.eq(3).text();
			
			var attach = new Object();
			attach.fbnum = fbnum;
			attach.anum = anum;
			attach.aname = aname;
			attach.asize = asize;
			
			arrAttach.push(attach);
			
			removeImg(anum+"_"+aname);
		}
	}
	$.ajax({
		url:"/file/delete",
		method:"post",
		data:{
			"arrAttach":JSON.stringify(arrAttach)
		},
		cache:false,
		dataType:"json",
		success:function(res) {
			if (res.result) {
				changeFile(res.fbnum);
			} else {
				alert("파일삭제 실패");
			}
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function removeImg(filename) {
	var contentsImg = $("#contents img")[0];
	console.log(contentsImg);
}

