function editCalendar()
{
	if(!confirm('정말로 현재 정보를 수정하시겠어요?')) return;
	var obj = {"s_pnum": $("#s_pnum").text(),
	           "content":$("#content").val()};
	console.log(obj)
	$.ajax({
		url:'/calen/updateCon',
		method:'post',
		data:obj,
		cache : false,
		dataType:'json',
		success : function(res){
			alert(res.update? '수정성공' :'수정실패');
			if(res.update){
				location.href = '';
			}
		},
        error : function(xhr, status, err){
           alert(err);
        }
	});	
	return false;
}
function deleteImg(a_num)
{			
	var num = $("#a_num").val();
	$.ajax({
		url : '/calen/delimg',	
		method : 'post',
		data : { "num":num },  											
		cache : false,		
		dataType:'json',                      
		success : function(res){		
			if(res.deleted){							
				location.reload();
			}	
		},
		error : function(xhr,status,err){
			alert(err);	
		}
	});
}	

$(document).ready(function (e){
	$("input[type='file']").change(function(e){
	
	$('#preview').empty();
	
	var files = e.target.files;
	var arr =Array.prototype.slice.call(files);
	      
	for(var i=0;i<files.length;i++){
		if(!checkExtension(files[i].name,files[i].size)){
	    	return false;
		}
	}     
		preview(arr);         
	});
	    
function checkExtension(fileName,fileSize){
	
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 20971520;
	      
	if(fileSize >= maxSize){
		alert('파일 사이즈 초과');
	    $("input[type='file']").val("");
	    return false;
	}
	      
	if(regex.test(fileName)){
	    alert('업로드 불가능한 파일이 있습니다.');
	    $("input[type='file']").val("");
	    return false;
	}
	    return true;
}
	    
function preview(arr){
	arr.forEach(function(f){      
		var fileName = f.name;
		if(fileName.length > 10){
			fileName = fileName.substring(0,7)+"...";
		}        
		var str = '<div style="display: inline-flex; padding: 10px;"><li style="list-style:none;">';
		str += '<span>'+fileName+'</span><br>';
		        
		if(f.type.match('image.*')){
			var reader = new FileReader(); 
		    reader.onload = function (e) {           
		    	str += '<img src="'+e.target.result+'" title="'+f.name+'" width=150 height=150 />';
		        str += '</li></div>';
		        $(str).appendTo('#preview');
		    } 
			reader.readAsDataURL(f);
		}else{
			str += '<img src="/resources/img/fileImg.png" title="'+f.name+'" width=150 height=150 />';
		    $(str).appendTo('#preview');
		 }
		});
	}
})