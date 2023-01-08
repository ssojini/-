function changeBoard(bname) {
	$.ajax({
		url: "/health/getListMap",
		method: "post",
		data: { "bname": bname },
		dataType: "json",
		cache: false,
		success: function(res) {
			$(".boardTr").remove("");
			$("#boardTable").append($tr);
			for (var i = 0; i < res.length; i++) {
				var $tr = $("<tr class='boardTr'><td>" + res[i].fbnum + "</td><td><a href='/health/detailFreeBoard?fbnum="+ res[i].fbnum +"'>" + res[i].title + "</a></td><td>" + res[i].author + "</td></tr>");
				$("#boardTable").append($tr);
			}
		},
		error: function(xhs, status, err) {
			alert(err);
		}
	});
}

function freeBoard() {
	location.href = "/health/addFreeBoard?bname="+$("#bname").val();
}