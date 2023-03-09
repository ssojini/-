$(function(){
	
	//변수 선언하기
	var userid = document.querySelector('#userid');

	var pwd1 = document.querySelector('#pwd1');
	var pwd = document.querySelector('#pwd');

	var error = document.querySelectorAll('.error_next_box');

	var nickname = document.querySelector('#nickname');

	var yy = document.querySelector('#yy');
	var mm = document.querySelector('#mm');
	var dd = document.querySelector('#dd');

	var email1 = document.querySelector('#email1');
	//var email2 = document.querySelector('#email2');

	var phone = document.querySelector('#phone');


	//이벤트 핸들러 연결
	userid.addEventListener("focusout", checkId);

	pwd1.addEventListener("focusout", checkPw);
	pwd.addEventListener("focusout", checkPw2);

	nickname.addEventListener("focusout", checkNick);

	yy.addEventListener("focusout", isBirthCompleted);
	mm.addEventListener("focusout", isBirthCompleted);
	dd.addEventListener("focusout", isBirthCompleted);

	phone.addEventListener("focusout", checkPhoneNum);
	email1.addEventListener("focusout", isEmailCorrect);

});
	
	//아이디 체크
	function checkId() {
		var userid = document.querySelector('#userid');
		var error = document.querySelectorAll('.error_next_box');
	    var idPattern = /[a-zA-Z0-9_-]{5,20}/;
	    if(userid.value === "") {
	        error[0].innerHTML = "필수 정보입니다.";
	        error[0].style.display = "block";
	        error[0].style.color = "red";
	    } else if(!idPattern.test(userid.value)) {
	        error[0].innerHTML = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";
	        error[0].style.display = "block";
	        error[0].style.color = "red";
	    }else{
           	error[0].innerHTML = "멋진 아이디네요!";
     	    error[0].style.color = "#08A600";
     	    error[0].style.display = "block"; 
         }
	     $.ajax({
            url: "/team/idcheck",
            type:'post',
            dataType:'json',
            data:{"userid" : userid.value},
           	success:function(res){
           		if(res.idcheck){
           			error[0].innerHTML="사용중인 아이디입니다"
           			error[0].style.display = "block";
        	        error[0].style.color = "red"; 
           		}
           	}
        });
	}

	//비밀번호
	function checkPw(){
		var error = document.querySelectorAll('.error_next_box');
		var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
		if(pwd1.value === ""){
			error[1].innerHTML ="필수 정보입니다.";
	        error[1].style.display = "block";
	        error[1].style.color = "red";
		}else if(!pwPattern.test(pwd1.value)){
	        error[1].innerHTML = "8~16자의 영문 대 소문자, 숫자,특수문자를 사용하세요."; 
	        error[1].style.display = "block";
	        error[1].style.color = "red";
		}else{
			error[1].innerHTML = "안전";
	        error[1].style.display = "none";
	    	
		}
	}

	//비밀번호 재확인
	function checkPw2(){
		var error = document.querySelectorAll('.error_next_box');
		if(pwd.value===pwd1.value && pwd.value!=""){
			error[2].style.display = "none";
		}else if(pwd.value!==pwd1.value){
			error[2].innerHTML = "비밀번호가 일치하지 않습니다";
			error[2].style.display = "block";
			error[2].style.color = "red";
		}if(pwd.value===""){
			error[2].innerHTML = "필수 정보입니다.";
			error[2].style.display="block";
			error[2].style.color = "red";
		}
	}

	//닉네임 확인
	function checkNick(){
		var error = document.querySelectorAll('.error_next_box');
		var namePattern = /[a-zA-Z가-힣]/;
		if(nickname.value===""){
			error[3].innerHTML = "필수 정보입니다.";
			error[3].style.display="block";
			error[3].style.color = "red";
		}else if(!namePattern.test(nickname.value) || nickname.value.indexOf(" ") > -1) {
			error[3].innerHTML = "한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용불가)";
			error[3].style.display="blokc";
			error[3].style.color = "red";
		}else{
			error[3].style.display="none";
		}
		$.ajax({
            url: "/team/nickcheck",
            type:'post',
            dataType:'json',
            data:{"nickname" : nickname.value},
           	success:function(res){
           		if(res.nickcheck){
           			error[3].innerHTML="사용중인 닉네입니다"
           			error[3].style.display = "block";
        	        error[3].style.color = "red"; 
           		}
           	}
        });
	}

	//핸드폰번호
	function checkPhoneNum() {
		var error = document.querySelectorAll('.error_next_box');
	    var isPhoneNum = /([01]{2})([01679]{1})([0-9]{3,4})([0-9]{4})/;

	    if(phone.value === "") {
	    	error[4].innerHTML = "필수 정보입니다.";
	    	error[4].style.display = "block";
	    	error[4].style.color="red";
	    } else if(!isPhoneNum.test(phone.value)) {
	    	error[4].innerHTML = "형식에 맞지 않는 번호입니다.";
	    	error[4].style.display = "block";
	    	error[4].style.color="red";
	    } else {
	        error[4].style.display = "none";
	    }
	}
	
	//생년월일
	function isBirthCompleted() {
		var error = document.querySelectorAll('.error_next_box');
	    var yearPattern = /[0-9]{4}/;

	    if(!yearPattern.test(yy.value)) {
	        error[5].innerHTML = "태어난 년도 4자리를 정확하게 입력하세요.";
	        error[5].style.display = "block";
	        error[5].style.color="red";
	    } else {
	        isMonthSelected();
	    }


	    function isMonthSelected() {
		var error = document.querySelectorAll('.error_next_box');
	        if(mm.value === "월") {
	            error[5].innerHTML = "태어난 월을 선택하세요.";
	            error[5].style.color="red";
	        } else {
	            isDateCompleted();
	        }
	    }

	    function isDateCompleted() {
		var error = document.querySelectorAll('.error_next_box');
	        if(dd.value === "") {
	            error[5].innerHTML = "태어난 일(날짜) 2자리를 정확하게 입력하세요.";
	            error[5].style.color="red";
	        } else {
	            isBirthRight();
	        }
	    }
	}

	function isBirthRight() {
		var error = document.querySelectorAll('.error_next_box');
	    var datePattern = /\d{1,2}/;
	    if(!datePattern.test(dd.value) || Number(dd.value)<1 || Number(dd.value)>31) {
	        error[5].innerHTML = "생년월일을 다시 확인해주세요.";
	        error[5].style.color="red";
	    } else {
	        checkAge();
	    }
	}

	function checkAge() {
		var error = document.querySelectorAll('.error_next_box');
	    if(Number(yy.value) < 1920) {
	        error[5].innerHTML = "정말이세요?";
	        error[5].style.color="red";
	    } else if(Number(yy.value) > 2019) {
	        error[5].innerHTML = "미래에서 오셨군요. ^^";
	        error[5].style.color="red";
	    } else {
	        error[5].style.display = "none";
	    }
	}
	//이메일
	function isEmailCorrect() {
	    var email1 = document.querySelector('#email1');
		var error = document.querySelectorAll('.error_next_box');
	    var emailPattern = /[a-z0-9]{2,}/;

	    if(email1.value === ""){ 
	    	error[6].innerHTML = "필수 정보입니다.";
	        error[6].style.display = "none"; 
	        error[6].style.color="red";
	    } else if(!emailPattern.test(email1.value)) {
	    	error[6].innerHTML = "형식에 맞지 않는 메일입니다.";
	        error[6].style.display = "block";
	        error[6].style.color="red";
	    } else {
	    	error[6].style.display = "none"; 
	    }
	}
	//이메일 input선택

function selectEmail(ele){
    var $ele = $(ele);
    var $email2 = $('input[name=email2]');

    // '1'인 경우 직접입력
    if($ele.val() == "1"){
        $email2.attr('readonly', false);
        $email2.val('');
    } else {
        $email2.attr('readonly', true);
        $email2.val($ele.val());
    }
}
//이메일 인증	
function sendEmail()
{
	var email1 = $('#email1').val();
	var email2 = $('#email2').val();
	var obj1 = {};
	obj1.email = email1 + '@' + email2;
	
	$.ajax({
		url : "/team/sendemail",
		method : 'post',
		data : obj1,
		cache : false,
		dataType:'json',
		success : function(res) {
			alert("이메일이 전송되었습니다.")
		},
		error : function(e) {
			alert("에러: "+err);
		}
	});
}
//인증 후 비활성화
function btnDisabled()
{
 	$.ajax({
		url : "/team/authEmail",
		method : 'post',
		cache : false,
		dataType:'json',
		success : function(res) {
			if(res){
				alert("인증 되었습니다");
 				joinUser();

			}else{
				alert("이메일 인증하세요");
			}
		},
		error : function(e) {
			alert("에러: "+err);
		}
	}); 
}

//가입
function joinUser()
{
	var email1 = $('#email1').val();
	var email2 = $('#email2').val();
	var obj = {};
	obj.email = email1 + '@' + email2;
	
	var gender = $('.gender').val();
	var yy = $('#yy').val();
	var mm = $('#mm').val();
	var dd = $('#dd').val();
	obj.birth = yy+"-"+mm+"-"+dd;
	
	obj.userid = $("#userid").val();
	obj.pwd = $("#pwd").val();
	obj.phone = $("#phone").val();
	obj.gender = $(".gender").val();
	obj.nickname = $("#nickname").val();
	obj.profile = $('#profile').val();
	
	console.log(obj);
	
	$.ajax({
		url:'/team/join',
		method:'post',
		data:obj,
		cache : false,
		dataType:'json',
		success : function(res){
			alert(res.join ? '회원가입 성공' :'회원가입 실패');
			location.href='/team/login';
		},
        error : function(xhr, status, err)
        {
           alert(err);
        }

	});	
	return false;
}
