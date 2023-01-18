function deleteFile() {
	var checkbox = $("input[type=checkbox]");
	var arrAttach = new Array();
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked) {
			var div = $("#attach"+checkbox[i].value);
			var child = div.children();
			var fbnum = $("#fbnum").text();
			var anum = child.eq(1).text();
			var aname = child.eq(2).text();
			var asize = child.eq(3).text();
			
			var attach = new Object();
			attach.fbnum = fbnum;
			attach.anum = anum;
			attach.aname = aname;
			attach.asize = asize;
			
			arrAttach.push(attach);
			
			removeImg(aname);
			removeImg2(anum+"_"+aname);
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
				updateContents();
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
	var imgs = document.getElementsByTagName(filename);
	for (var i = 0; i < imgs.length; i++) {
		imgs[i].remove();
	}
}

function removeImg2(filename) {
	let img = document.getElementsByTagName("img");
	console.log(img);
	for (var i = 0; i < img.length; i++) {
		console.log(img[i]);
		console.log(img[i].src);
		if (img[i].src == "http://localhost/images/"+filename) {
			img[i].remove();
		}
	}
}

function updateContents() {
	$.ajax({
		url:"/freeboard/updateContents",
		method:"post",
		data:{
			"fbnum":$("#fbnum").text(),
			"contents":$("#contents").html()
		},
		cache:false,
		dataType:"json",
		success:function(res) {
			alert(res.result?"파일삭제 성공":"파일삭제 실패");
			location.href = "/freeboard/edit?fbnum="+$("#fbnum").text();
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function deleteFreeboard(fbnum) {
	$.ajax({
		url:"/freeboard/delete",
		method:"post",
		data:{"fbnum":fbnum},
		dataType:"json",
		cache:false,
		success:function(res) {
			alert(res.result?"삭제 성공":"삭제 실패");
			if (res.result) {
				location.href = "/freeboard";
			}
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function changeFile() {
	updateContents();
	let data = new FormData();
	let files = $("#files")[0].files;
	for (var i = 0; i < files.length; i++) {
		data.append("files", files[i]);
	}
	data.append("fbnum",$("#fbnum").text());
	$.ajax({
		url:"/file/upload",
		enctype: "multipart/form-data",
		method:"post",
		data:data,
		cache:false,
		processData:false,
		contentType:false,
		timeout: 600000,
		dataType:"json",
		success:function(res) {
			location.href = "/freeboard/edit?fbnum="+$("#fbnum").text();
		},
		error:function(xhs,status,err) {
			alert(err);
		}
	});
}

function insertImg() {
	let checkboxs = $("input[type=checkbox]");
	for (var i = 0; i < checkboxs.length; i++) {
		if (checkboxs[i].checked == true) {
			appendImg(checkboxs[i].value);
		}
	}
}

function appendImg(anum) {
	let div = $("#attach"+anum);
	let children = div.children();
	let aname = children.eq(2).text();
	let $img = $("<img src='/images/"+anum+"_"+aname+"'>");
	$("#contents").append($img);
}