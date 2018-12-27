	 	 //0三级联动
  	$(function(){
		//alert("111");
		var str="";
       	$.post(
 			//url
 			"getArr/getP.do",
 			//不携带参数
 			//回调函数
 			function(data){
 				if(data!=''){
 					//遍历所有的省份   拼接option  使下拉出来的都是省份   传值传的是编号   显示出来的是省名称
 					for(var i = 0;i<data.length;i++){
	 					str += "<option  value = '"+data[i].code+"'>"+data[i].name+"</option>";	
 					}
 					//$("#province").val(data[0].name);
 					$("#province").append(str);
 				}
 			},
 			//数据类型
 			"json"
 		);
		
		
		getCitys("100");	
		
		getArea("1001");
					
		//给province添加一个change事件
	  	$("#province").change(function(){
			var arr="";
	  		//获取province的值
	  		var provinceId = $("#province").val();
	  		alert(provinceId);
			getCitys(provinceId);  	
			arr = $("#province").find("option:selected").text();
			$("#Arr").val(arr);
	  	});	
			
	
	 		//给city添加一个change事件
	  	$("#city").change(function(){
	  		var arr="";
	  		//获取cityId的值
	  		var cityId = $("#city").val();
	  		getArea(cityId);
	  		//把省市的名称放到arr字符串里去 然后在id为address的input框中显示
	  		arr =  $("#province").find("option:selected").text()+$("#city").find("option:selected").text();
			$("#address").val(arr);	
	  	});
	  	
	  	//给area添加一个change事件
	  	$("#area").change(function(){
	  		var arr="";
	  		arr =  $("#province").find("option:selected").text()+$("#city").find("option:selected").text()+$("#area").find("option:selected").text();
			$("#address").val(arr);	
	  	});
	});
  //封装 	
  	 function getCitys(provinceId){
  	 	$.post(
  	  			//跳转servlet
  	  			"/sb/baseInfo/getCity",
  	  			//携带参数
  	  			{provinceId:provinceId},
  	  			//回调函数
  	  			function(data){
  			  		var str = "";
  	  				if(data!=''){
  	  					$("#city").empty();
  	  					for(var i = 0;i<data.length;i++){
  	  						str += "<option value = '"+data[i].code+"'>"+data[i].name+"</option>";
  	  					}
  	  					$("#city").append(str);
  	  				}
  	  			},
  	  			//返回的数据类型
  	  			"json"
  	  		);
  	 }	
  	  		
  	 function getArea(cityId){
  	 	$.post(
  	  			//跳转servlet
  	  			"getArr/getA.do",
  	  			//携带参数
  	  			{cityId:cityId},
  	  			//回调函数
  	  			function(data){
  			  		var str = "";
  	  				if(data!=''){
  	  					$("#area").empty();
  	  					for(var i = 0;i<data.length;i++){
  	  						str += "<option value = '"+data[i].code+"'>"+data[i].name+"</option>";
  	  					}
  	  					$("#area").append(str);
  	  					getArea(data[0].name);
  	  				}
  	  			},
  	  			//返回的数据类型
  	  			"json"
  	  		);
  	 } 	
  	 
  	 
  	 //8888888888888888888888888888888888888888888888888
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  	
  //3三级联动
  	$(function(){
		//alert("111");
		var str="";
       	$.post(
 			//url
 			"getArr/getP1.do",
 			//不携带参数
 			//回调函数
 			function(data){
 				if(data!=''){
 					for(var i = 0;i<data.length;i++){
	 					str += "<option  value = '"+data[i].code+"'>"+data[i].name+"</option>";	
 					}
 					//$("#province").val(data[0].name);
 					$("#province3").append(str);
 				}
 			},
 			//数据类型
 			"json"
 		);
		
		
		getCitys3("100");	
		
		getArea3("1001");
					
		//给province添加一个change事件
	  	$("#province3").change(function(){
			var arr="";
	  		//获取province的值
	  		var provinceId = $("#province3").val();
			getCitys(provinceId);  	
			arr = $("#province3").find("option:selected").text();
			$("#address3").val(arr);	
	  	});	
			
	
	 		//给city添加一个change事件
	  	$("#city3").change(function(){
	  		var arr="";
	  		//获取cityId的值
	  		var cityId = $("#city3").val();
	  		getArea(cityId);
	  		arr =  $("#province3").find("option:selected").text()+$("#city3").find("option:selected").text();
			$("#address3").val(arr);	
	  	});
	  	
	  	//给area添加一个change事件
	  	$("#area3").change(function(){
	  		var arr="";
	  		arr =  $("#province3").find("option:selected").text()+$("#city3").find("option:selected").text()+$("#area3").find("option:selected").text();
			$("#address3").val(arr);	
	  	});
	  	
	});
  //封装 	
 	 function getCitys3(provinceId){
 	 	$.post(
 	  			//跳转servlet
 	  			"getArr/getC1.do",
 	  			//携带参数
 	  			{provinceId:provinceId},
 	  			//回调函数
 	  			function(data){
 			  		var str = "";
 	  				if(data!=''){
 	  					$("#city3").empty();
 	  					for(var i = 0;i<data.length;i++){
 	  						str += "<option value = '"+data[i].code+"'>"+data[i].name+"</option>";
 	  					}
 	  					$("#city3").append(str);
 	  				}
 	  			},
 	  			//返回的数据类型
 	  			"json"
 	  		);
 	 }	
 	  		
 	 function getArea3(cityId){
 	 	$.post(
 	  			//跳转servlet
 	  			"getArr/getA1.do",
 	  			//携带参数
 	  			{cityId:cityId},
 	  			//回调函数
 	  			function(data){
 			  		var str = "";
 	  				if(data!=''){
 	  					$("#area3").empty();
 	  					for(var i = 0;i<data.length;i++){
 	  						str += "<option value = '"+data[i].code+"'>"+data[i].name+"</option>";
 	  					}
 	  					$("#area3").append(str);
 	  					getArea3(data[0].name);
 	  				}
 	  			},
 	  			//返回的数据类型
 	  			"json"
 	  		);
 	 } 	
 	
 	 
 	 
 	 
 	 
 	 //888888888888888888888888888888888888888888888888
  	
  	
  	
  	
  	
  	
  	
	 //2三级联动
  	$(function(){
		//alert("111");
		var str="";
       	$.post(
 			//url
 			"getArr/getP.do",
 			//不携带参数
 			//回调函数
 			function(data){
 				if(data!=''){
 					for(var i = 0;i<data.length;i++){
	 					str += "<option  value = '"+data[i].code+"'>"+data[i].name+"</option>";	
 					}
 					//$("#province").val(data[0].name);
 					$("#province2").append(str);
 				}
 			},
 			//数据类型
 			"json"
 		);
		
		
		getCitys2("100");	
		
		getArea2("1001");
					
		//给province添加一个change事件
	  	$("#province2").change(function(){
			var arr="";
	  		//获取province的值
	  		var provinceId = $("#province2").val();
			getCitys2(provinceId);  	
			arr = $("#province2").find("option:selected").text();
			$("#address2").val(arr);	
	  	});	
			
	
	 		//给city添加一个change事件
	  	$("#city2").change(function(){
	  		var arr="";
	  		//获取cityId的值
	  		var cityId = $("#city2").val();
	  		getArea2(cityId);
	  		arr =  $("#province2").find("option:selected").text()+$("#city2").find("option:selected").text();
			$("#address2").val(arr);	
	  	});
	  	
	  	//给area添加一个change事件
	  	$("#area2").change(function(){
	  		var arr="";
	  		arr =  $("#province2").find("option:selected").text()+$("#city2").find("option:selected").text()+$("#area2").find("option:selected").text();
			$("#address2").val(arr);	
	  	});
	  	
	});
  	
  //封装 	
  	 function getCitys2(provinceId){
  	 	$.post(
  	  			//跳转servlet
  	  			"getArr/getC.do",
  	  			//携带参数
  	  			{provinceId:provinceId},
  	  			//回调函数
  	  			function(data){
  			  		var str = "";
  	  				if(data!=''){
  	  					$("#city2").empty();
  	  					for(var i = 0;i<data.length;i++){
  	  						str += "<option value = '"+data[i].code+"'>"+data[i].name+"</option>";
  	  					}
  	  					$("#city2").append(str);
  	  				}
  	  			},
  	  			//返回的数据类型
  	  			"json"
  	  		);
  	 }	
  	  		
  	 function getArea2(cityId){
  	 	$.post(
  	  			//跳转servlet
  	  			"getArr/getA.do",
  	  			//携带参数
  	  			{cityId:cityId},
  	  			//回调函数
  	  			function(data){
  			  		var str = "";
  	  				if(data!=''){
  	  					$("#area2").empty();
  	  					for(var i = 0;i<data.length;i++){
  	  						str += "<option value = '"+data[i].code+"'>"+data[i].name+"</option>";
  	  					}
  	  					$("#area2").append(str);
  	  					getArea2(data[0].name);
  	  				}
  	  			},
  	  			//返回的数据类型
  	  			"json"
  	  		);
  	 } 	
  	 
  	
  	
  	
  	//88888888888888888888888888888888888888888888888888
	 //1三级联动
   	$(function(){
 		//alert("111");
 		var str="";
        	$.post(
  			//url
  			"getArr/getP.do",
  			//不携带参数
  			//回调函数
  			function(data){
  				if(data!=''){
  					for(var i = 0;i<data.length;i++){
 	 					str += "<option  value = '"+data[i].code+"'>"+data[i].name+"</option>";	
  					}
  					//$("#province").val(data[0].name);
  					$("#province1").append(str);
  				}
  			},
  			//数据类型
  			"json"
  		);
 		
 		
 		getCitys1("100");	
 		
 		getArea1("1001");
 					
 		//给province添加一个change事件
 	  	$("#province1").change(function(){
 			var arr="";
 	  		//获取province的值
 	  		var provinceId = $("#province1").val();
 			getCitys1(provinceId);  	
 			arr = $("#province1").find("option:selected").text();
 			$("#address1").val(arr);	
 	  	});	
 			
 	
 	 		//给city添加一个change事件
 	  	$("#city1").change(function(){
 	  		var arr="";
 	  		//获取cityId的值
 	  		var cityId = $("#city1").val();
 	  		getArea1(cityId);
 	  		arr =  $("#province1").find("option:selected").text()+$("#city1").find("option:selected").text();
 			$("#address1").val(arr);	
 	  	});
 	  	
 	  	//给area添加一个change事件
 	  	$("#area1").change(function(){
 	  		var arr="";
 	  		arr =  $("#province1").find("option:selected").text()+$("#city1").find("option:selected").text()+$("#area1").find("option:selected").text();
 			$("#address1").val(arr);	
 	  	});
 	  	
 	});
   	
   //封装 	
   	 function getCitys1(provinceId){
   	 	$.post(
   	  			//跳转servlet
   	  			"getArr/getC.do",
   	  			//携带参数
   	  			{provinceId:provinceId},
   	  			//回调函数
   	  			function(data){
   			  		var str = "";
   	  				if(data!=''){
   	  					$("#city1").empty();
   	  					for(var i = 0;i<data.length;i++){
   	  						str += "<option value = '"+data[i].code+"'>"+data[i].name+"</option>";
   	  					}
   	  					$("#city1").append(str);
   	  				}
   	  			},
   	  			//返回的数据类型
   	  			"json"
   	  		);
   	 }	
   	  		
   	 function getArea1(cityId){
   	 	$.post(
   	  			//跳转servlet
   	  			"getArr/getA.do",
   	  			//携带参数
   	  			{cityId:cityId},
   	  			//回调函数
   	  			function(data){
   			  		var str = "";
   	  				if(data!=''){
   	  					$("#area1").empty();
   	  					for(var i = 0;i<data.length;i++){
   	  						str += "<option value = '"+data[i].code+"'>"+data[i].name+"</option>";
   	  					}
   	  					$("#area1").append(str);
   	  					getArea1(data[0].name);
   	  				}
   	  			},
   	  			//返回的数据类型
   	  			"json"
   	  		);
   	 } 
 
	
 
 
 
 
 
 
 
 
 
 
 