function deleteCal(s_pnum,s_num)
{
	if(!confirm('정말로 현재 게시글을 삭제하시겠어요?')) return;

	$.ajax ({
		url : '/calen/delete',
		method : 'post',
		data : {
			"num" : s_pnum,
			"anum" : s_num
		},
		cache : false,
		dateType : 'json',
		success : function(res){
			alert(res.deleted ? "삭제성공" : "삭제실패");
			if(res.deleted)	{
				location.href='/calen/getCalendar';
			}
		},
		error:function(xhr,status,err){
		alert(err);
	}
	});
}