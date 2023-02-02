/**
 * 
 */
 $(function(){
	var chkObj = document.getElementsByName("rowCheck");
	var rowCnt = chkObj.length;
	
	// 전체선택/해제
	$("input[name='allCheck']").click(function(){
		var chk_listArr = $("input[name='rowCheck']");
		for (var i=0; i<chk_listArr.length; i++){
			chk_listArr[i].checked = this.checked;
		}
	});
	
	// 각 선택창이 모두 선택/해제되면 전체선택란 체크/체크해제
	$("input[name='rowCheck']").click(function(){
		if($("input[name='rowCheck']:checked").length == rowCnt){
			$("input[name='allCheck']")[0].checked = true;
		}else{
			$("input[name='allCheck']")[0].checked = false;
		}
		
		var valueArr = new Array();
	var list = $("input[name='rowCheck']");
	var loop_cnt=0;	
	var total = 0;
	for(var i=0; i<list.length; i++){
		if(list[i].checked){
			var sum = list[i].nextElementSibling.nextElementSibling.value;
			//console.log("sum: "+sum);
			total+=parseInt(sum);
		}
	}
	$('#total_sum').text(total);
		
	});	
});


function cnt_change(cartnum){
	//alert(cartnum);
	//var prod_cnt = document.getElementById(cartnum+"_cnt").value;
	var prod_cnt = $('#'+cartnum+'_cnt').val();
	//console.log(prod_cnt);
	//alert("변경수량: "+prod_cnt);	
	
	 $.ajax({
		url:'/shop/cnt_change',
		method:'post',
		cache:false,
		data: {"cartnum":cartnum,"prod_cnt":prod_cnt},
		dataType:'json',
		success: function(res){
			alert(res.msg);
			location.replace('/shop/cart');
		}, error: function(xhr,status,err){
			alert("에러: "+err);			
		}
	})	 
}

function delAll(){
	alert("전체삭제");
	$.ajax({
		url:'/shop/delAll',
		method:'post',
		cache:false,
		dataType:'json',
		success: function(res){
			alert(res.msg);			
			location.replace('/shop/cart');
		}, error: function(xhr,status,err){
			alert("에러: "+err);
		}		
	})
	
}

function delSel(){
	var valueArr = new Array();
	var list = $("input[name='rowCheck']");
	for(var i=0; i<list.length; i++){
		if(list[i].checked){
		valueArr.push(list[i].value);
		}
	}
	if(valueArr.length == 0){
		alert("선택상품이 없습니다."); 
	}else{
		var chk = confirm("정말 삭제하시겠습니까?");
		$.ajax({
			url:'/shop/delSel',
			type:'post',
			traditional: true,
			data:{valueArr:valueArr},
			//dataType:'json',
			success: function(res){
				if(res = 1){
				alert("삭제성공");
				location.replace("/shop/cart");
				}else{
				alert("삭제실패");				
				}
			}, error: function(xhr,status,err){
				alert('에러: '+err);
			}			
		});
	}
}

function buySel(){
		
	var valueArr = new Array();
	var list = $("input[name='rowCheck']");
	var loop_cnt=0;
	
	for (var i = 0; i < list.length; i++) {
		if(list[i].checked){
			//console.log(list[i]);
			var cartnum = list[i].nextElementSibling.value;
			var userid = list[i].nextElementSibling.nextElementSibling.nextElementSibling.value;
			var obj = {"cartnum":cartnum,"userid":userid};
			valueArr.push(obj);
			loop_cnt++;
		}
	}
	if(loop_cnt==0){
		alert("선택상품이 없습니다.");
		return false;
	}
	//console.log(JSON.stringify(valueArr));
	$('#items').val(JSON.stringify(valueArr));
	$('#buyCart').submit();	
	
}


	function buyAll() {
		var chk_listArr = $("input[name='rowCheck']");
		//console.log(chk_listArr);
		for (var i=0; i<chk_listArr.length; i++){
			chk_listArr[i].checked = true;
		}
		buySel();
	}