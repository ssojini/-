
window.onload = function() {
};

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
				var $tr = $("<tr class='boardTr'><td>"+res[i].fbnum+"</td><td>"+res[i].title+"</td><td>"+res[i].contents+"</td></tr>");
				$("#boardTable").append($tr);
			}
		},
		error: function(xhs, status, err) {
			alert(err);
		}
	});
}