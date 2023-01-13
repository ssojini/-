function deleteFile() {
	var checkbox = $("input[type=checkbox]");
	for (var i = 0; i < checkbox.length; i++) {
		if (checkbox[i].checked) {
			console.log(checkbox[i]);
		}
	}
	$.ajax({
		
	});
}