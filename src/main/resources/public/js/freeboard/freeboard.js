function changeBoard(bname) {
	location.href = "/freeboard?bname="+bname;
}

function addFreeboard() {
	location.href = "/freeboard/add?bname="+$("#bname").val();
}

function search_freeboard_onkeypress(event) {
	if (event.keyCode == 13) {
		location.href = "/freeboard?bname="+$("#bname").val()+"&title="+event.target.value;
	}
}
function search_freeboard_onclick() {
	$("#search_freeboard_input").val("");
	location.href = "/freeboard?bname="+$("bname").val()+"&title="+$("#search_freeboard_input").val();
}

function goPage(page) {
	location.href = "/freeboard?bname="+$("#bname").val()+"&title="+$("#title").val()+"&page="+page;
}