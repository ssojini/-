/**
 * 
 */
 // 구매금액 갯수/합계 구하기
window.onload = (function(){
	var total = 0;
	var list = $("input[name='cartnum']");
	var i = 0;
	for(i; i<list.length;i++){
		var sum = list[i].nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.value;	
//		console.log("sum: "+sum);
		total+=parseInt(sum);
	}
	$('#total_cnt').text(i+' 개 품목');
	$('#total_sum').text(total);
});
 
 	function payment(){
		var itemArr = new Array();
		var list = $("input[name='cartnum']");
		for(var i=0; i<list.length;i++){
			var cartnum = list[i].value;
			var goodsnum = list[i].nextElementSibling.value;
			var goodsname = list[i].nextElementSibling.nextElementSibling.value;
			var price = list[i].nextElementSibling.nextElementSibling.nextElementSibling.value;
			var prod_cnt = list[i].nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.value;
			var sum = list[i].nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.value;
			var mainpic_server = list[i].nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.value;	
			
			var obj = {"cartnum":cartnum,"goodsnum":goodsnum,"goodsname":goodsname,"price":price,"prod_cnt":prod_cnt,"sum":sum,"mainpic_server":mainpic_server}
			itemArr.push(obj);
		}
		//alert(JSON.stringify(itemArr));	
		//주소와 세션에서 userid 넣어 보내야함
		
		$('#items').val(JSON.stringify(itemArr));
		$('#userid').val("asdf");
		var address = "("+$('#sample4_postcode').val()+")"+$('#sample4_roadAddress').val() +" "+ $('#sample4_detailAddress').val();
		$('#address').val(address);
		$('#payment').submit();
		
	}
 
 //주소 얻기 
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function findAddress() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("sample4_roadAddress").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                } else {
                    document.getElementById("sample4_extraAddress").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
    
    //페이
    function requestPay() {	
	
	var name = "무적의 공격대";
	var amount = $('#total_sum').text();
	var buyer_email = "siesta_w@naver.com";
	var buyer_name = $('#uname').val();
	var buyer_tel = $('#phone').val();
	var buyer_addr = $('#sample4_roadAddress').val() +" "+ $('#sample4_detailAddress').val();
	var buyer_postcode = $('#sample4_postcode').val();
	$('#address').val("("+buyer_postcode+")"+buyer_addr);
		
	//alert("구매정보:"+buyer_addr);
	
	var IMP = window.IMP; // 생략 가능
	IMP.init("imp78063027"); // 예: imp00000000
    // IMP.request_pay(param, callback) 결제창 호출
	IMP.request_pay({ // param
          pg: "kakaopay",
          pay_method: "card",
          merchant_uid: "merchant_"+new Date().getTime(),
          name: name,
          amount: amount,
          buyer_email: buyer_email,
          buyer_name: buyer_name,
          buyer_tel: buyer_tel,
          buyer_addr: buyer_addr,
          buyer_postcode: buyer_postcode
      }, function (rsp) { // callback
        if (rsp.success) {
            alert("테스트 결제성공");
            payment();
        } else {
            alert("실패");
        }
    });
  }
  
  