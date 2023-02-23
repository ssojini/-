$(function(){
	$('#btnUpload').on('click', function(event) {
		event.preventDefault();
		    
		var form = $('#addForm')[0]
	    var data = new FormData(form);
		    
		$('#btnUpload').prop('disabled', true);
		
		console.log(data);
		for (var key of data.keys()) {
			  console.log("키"+key);
			}
			for (var value of data.values()) {
			  console.log("내용"+value);

			}
			
		$.ajax ({
			type : 'post',
		    enctype: 'multipart/form-data',
			url : '/calen/add',
		    data : data,
			dateType : 'json',
			processData: false,  
		    contentType: false,
			cache : false,
			success: function (data) {
		           $('#btnUpload').prop('disabled', false);
		           alert('저장되었습니다')
		           location.href='/calen/getCalendar'
		        },
		        error: function (e) {
		            $('#btnUpload').prop('disabled', false);
		            alert('저장실패');
			}
		});
	})	
})

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
});