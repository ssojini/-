function save(ordernum) {
	var status1 = document.getElementById("status");
	var statusIndex = document.getElementById("status").options.selectedIndex;
	var status = status1.options[statusIndex].value;
	if (status == '선택') {
		alert("배송상태를 변경하세요");
		$("#status").focus();
	} else {
		$.ajax({
			url: "/manager/shop/edit",
			method: "post",
			data: {
				"status": status,
				"ordernum": ordernum
			},
			dataType: "json",
			cache: false,
			success: function(res) {
				if (res.result) {
					alert("'"+status+"' 으로 변경 성공");
					location.href = "/manager/shop/detail?ordernum="+ordernum;
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

