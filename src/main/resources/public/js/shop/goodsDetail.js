/**
 * 
 */
 /* 상품 리뷰
 $(function(){          
    var includes2 = $('[data-include2="review"]');
    jQuery.each(includes2, function(){
      $(this).load('review');
    });
  }); */


	function total(){
		var price = parseInt($('#price').val());
		var num = parseInt($('#prod_cnt').val());
		$('#sum').text(price*num);		
	}
	
	function addCart(){
		var price = parseInt($('#price').val());
		var num = parseInt($('#prod_cnt').val());
		var cart = $('#cartForm').serialize();		
		cart +="&sum="+price*num;
		//cart +="&userid="+"asdf";
		//console.log(cart);
		$.ajax({
			url: '/shop/cart',
			method:'post',
			cache:false,
			data: cart,
			dataType:'json',
			success:function(res){
				alert(res.msg);
				if(res.added){
					if(confirm("장바구니 보러가기?")){
						location.href='/shop/cart';
					}else{
						false;
					}					
				}
			},error:function(xhr,status,err){
				alert("에러: "+err);
			}			
		});
	}
	
	function buyNow(){
		//alert("상품번호: "+goodsnum+" 수량: "+prod_cnt);
		var goodsnum = $('#goodsnum').val();		
		var prod_cnt = $('#prod_cnt').val();
				
		var item = new Object();
		item.goodsnum = goodsnum;
		item.prod_cnt = prod_cnt;
				
		$.ajax({
			url:'/shop/buynow',
			method:'post',
			cache:false,
			data: item,
			dataType:'text',
			success:function(res){
				alert(res);
			},error:function(xhr,status,err){
				alert("에러: "+err);
			}			
		}); 
		
	}	
	