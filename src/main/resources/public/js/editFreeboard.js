function deleteFile() {
	var checkbox = $("input[type=checkbox]");
	var arrAttach = new Array();
	var attach = new Object();
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked) {
			var div = $("#"+checkbox[i].value);
			console.log(div);
			var child = div.children();
			console.log(child.eq(1));
			var fbnum = $("#fbnum").text();
			console.log(fbnum);
			var anum = child.eq(1).text();
			var aname = child.eq(2).text();
			var asize = child.eq(3).text();
			
			attach.fbnum = fbnum;
			attach.anum = anum;
			attach.aname = aname;
			attach.asize = asize;
			
			arrAttach.push(attach);
			
			removeImg(aname);
		}
	}
	console.log(arrAttach);
	console.log(JSON.stringify(arrAttach));
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
	var imgs = document.getElementsByClassName(filename);
	for (var i = 0; i < imgs.length; i++) {
		imgs[i].remove();
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