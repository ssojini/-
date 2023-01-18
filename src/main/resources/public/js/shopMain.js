var slides = document.querySelectorAll("#slides>img");
var prev = document.getElementById("prev");
var next = dpcument.getElementById("next");

var current = 0;
showSlides(current);
prev.onclick = prevSlide;
next.onclick = nextSlide;

function shouSlides(n){
	for(var i=0;i<slides.length; i++)
	{
		slides[i].style.display="none";
	}
	slides[n].style.display="block";
}

function prevSlide()
{
	if(current>0)curren -=1;
	else current =slids.length -1;
	showSlides(current);
}

function nextSlide()
{
	if(current<slides.length-1)current +=1;
	
	else current =0;
	showSlides(current);
}