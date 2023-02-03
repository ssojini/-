/**
 * 
 */
function freeboard_todayBest_nextBtn() {
	freeboard_currentPage = parseInt($("#freeboard_currentPage").val());
	freeboard_nextPage = freeboard_currentPage + 1;
	if ($(".freeboard_"+freeboard_nextPage).length) {
		$(".freeboard_"+freeboard_currentPage).css("display","none");
		$("#freeboard_currentPage").val(freeboard_nextPage);
	}
}

function freeboard_todayBest_previousBtn() {
	freeboard_previousPage = parseInt($("#freeboard_currentPage").val()) - 1;
	if ($(".freeboard_"+freeboard_previousPage).length) {
		$(".freeboard_"+freeboard_previousPage).css("display","block");
		$("#freeboard_currentPage").val(freeboard_previousPage);
	}
}