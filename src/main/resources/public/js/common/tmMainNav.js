/**
 * 
 */
function overTmMainNav() {
	var elements = document.getElementsByClassName("navbar_item_menu");
	for (var i = 0; i < elements.length; i++) {
		elements[i].style.cssText = "display:block";
	}
}

function leaveTmMainNav() {
	var elements = document.getElementsByClassName("navbar_item_menu");
	for (var i = 0; i < elements.length; i++) {
		elements[i].style.cssText = "display:none";
	}
}