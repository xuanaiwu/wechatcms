<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>

<body>
<div class="warp easyui-panel" data-options="border:false">
	<!-- Search panel start -->
 	 
 	 <form id="searchForm">
 	 	<p class="ui-fields">
 	 	    <label class="ui-label">指数名称:</label>
 	 	    <select  id="indexCode" name="indexCode"  style="width:250px"  >
 	 	    	<c:forEach items="${typeList}" var="dataSwIndex">
 	 	    		<option value="${dataSwIndex.indexCode }">${dataSwIndex.indexName }</option>
 	 	    	</c:forEach>
		   </select>
        </p>
        <p class="ui-fields">
 	 	    <label class="ui-label">维度:</label>
 	 	    <select  id="code" name="code"  style="width:250px"  >
 	 	    	<option value="0">指数</option>
				<option value="1">市盈率</option>
				<option value="2">市净率</option>
				<option value="3">换手率</option>
				<option value="4">成交量</option>
				<option value="5">成交额</option>
				<option value="6">均价</option>
				<option value="7">股息率</option>
		   </select>
        </p>
        <p class="ui-fields">
        	<label style="width:80px">开始日期:</label>  
			            <input id="startDate" type="text" name="startDate" class="easyui-datebox"  style="width:139px"></input>
        </p>
        
         <p class="ui-fields">
        	<label style="width:80px">结束日期:</label>  
			            <input id="endDate" type="text" name="endDate" class="easyui-datebox"  style="width:139px"></input>
        </p>
        
       <!--   <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search" onClick="search();">查询</a>-->
        <button type="button" id="btn-search" class="easyui-linkbutton"  onClick="search();">查询</button>
      </form>  
	<br/>
     <!--  Search panel end -->
    <div id="main" style="width: 800px;height:600px;"></div>     	 
</div>
</body>
   <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
       var chart = echarts.init(document.getElementById('main'), 'shine');

        // 指定图表的配置项和数据
        var option = {
        		title: {
        	        text: '市场趋势'
        	    },
        	    tooltip: {
        	        trigger: 'axis'
        	    },
        	    grid: {
        	        left: '7%',
        	        right: '8%',
        	        bottom: '5%',
        	        containLabel: true
        	    },
        	    toolbox: {
        	        feature: {
        	            saveAsImage: {}
        	        }
        	    }
        };

       // 使用刚指定的配置项和数据显示图表。
       chart.setOption(option);
       chart.showLoading();
        
     // 异步加载数据
     var _url = urls['msUrl'] + '/Report/getLineReport.do';
     search();
     
     function search(){
    	 chart.showLoading();
    	 var code=$("#code").val();
    	 var options = {
    	       url: _url,
    	       type: 'POST',
    	       success: function (data) {
    	    	     chart.hideLoading();
	              	 var arr=data.value;
	              	 
	                	 var myDates =new Array();
	                	 var myIndexs=new Array();
	                	 var myPes=new Array();
	                	 var myPbs=new Array();
	                	 var myTrades=new Array();
	                	 var myTurnovers=new Array();
	                	var myVolumes=new Array();
	                	
	                	for(var i=0;i<arr.length;i++){
	                		myDates[i]=arr[i].releaseDateString;
	                	}
	                	
	                	if(code==0){
	                		for(var i=0;i<arr.length;i++){
	                    		
	                    		myIndexs[i]=arr[i].indexEnd;
	                    		
	                    		
	                    	}
	                		//alert(myPes);
	                      // 填入数据
	                      chart.setOption({
	                    	  legend: {
	                  	        data:['指数']
	                  	    },
	                      	xAxis: {
	                  	        type: 'category',
	                  	        boundaryGap: false,
	                  	        data: myDates
	                  	    },
	                  	    yAxis: {
	                  	        type: 'value'
	                  	    },
	                  	    series: [
	                  	        {
	                  	            name:'指数',
	                  	            type:'line',
	                  	            stack: '点位',
	                  	            data:myIndexs
	                  	        }
	                  	    ]
	
	                      });  
	                		
	              		
	              	}else if(code==1){
	              		for(var i=0;i<arr.length;i++){
	                    		
	                    		myPes[i]=arr[i].pe;
	                    		
	                    		
	                    	}
	              		
	              		//alert(myPes);
	                      // 填入数据
	                      chart.setOption({
	                    	  legend: {
	                  	        data:['市盈率']
	                  	    },
	                      	xAxis: {
	                  	        type: 'category',
	                  	        boundaryGap: false,
	                  	        data: myDates
	                  	    },
	                  	    yAxis: {
	                  	        type: 'value'
	                  	    },
	                  	    series: [
	                  	        {
	                  	            name:'市盈率',
	                  	            type:'line',
	                  	            stack: '大小',
	                  	            data:myPes
	                  	        }
	                  	    ]
	
	                      });  
	              		
	              	}else if(code==2){
							for(var i=0;i<arr.length;i++){
	                    		
	                    		myPbs[i]=arr[i].pb;
	                    		
	                    		
	                    	}
							
							
							//alert(myPes);
	                      // 填入数据
	                      chart.setOption({
	                    	  legend: {
	                  	        data:['市净率']
	                  	    },
	                      	xAxis: {
	                  	        type: 'category',
	                  	        boundaryGap: false,
	                  	        data: myDates
	                  	    },
	                  	    yAxis: {
	                  	        type: 'value'
	                  	    },
	                  	    series: [
	                  	        {
	                  	            name:'市净率',
	                  	            type:'line',
	                  	            stack: '大小',
	                  	            data:myPbs
	                  	        }
	                  	    ]
	
	                      });  
	              		
	              	}else if(code==3){
	              		
							for(var i=0;i<arr.length;i++){
	                    		
								myTrades[i]=arr[i].trade;
	                    		
	                    		
	                    	}
							
							
							//alert(myPes);
	                      // 填入数据
	                      chart.setOption({
	                    	  legend: {
	                  	        data:['换手率']
	                  	    },
	                      	xAxis: {
	                  	        type: 'category',
	                  	        boundaryGap: false,
	                  	        data: myDates
	                  	    },
	                  	    yAxis: {
	                  	        type: 'value'
	                  	    },
	                  	    series: [
	                  	        {
	                  	            name:'换手率',
	                  	            type:'line',
	                  	            stack: '大小',
	                  	            data:myTrades
	                  	        }
	                  	    ]
	
	                      });  
	              		
	              	}else if(code==4){
	              		
							for(var i=0;i<arr.length;i++){
	                    		
								myTurnovers[i]=arr[i].turnover;
	                    		
	                    		
	                    	}
							
							
							//alert(myPes);
	                      // 填入数据
	                      chart.setOption({
	                    	  legend: {
	                  	        data:['成交量']
	                  	    },
	                      	xAxis: {
	                  	        type: 'category',
	                  	        boundaryGap: false,
	                  	        data: myDates
	                  	    },
	                  	    yAxis: {
	                  	        type: 'value'
	                  	    },
	                  	    series: [
	                  	        {
	                  	            name:'成交量',
	                  	            type:'line',
	                  	            stack: '亿股',
	                  	            data:myTurnovers
	                  	        }
	                  	    ]
	
	                      });  
	              		
	              		
	              		
	              	}else if(code==5){
							for(var i=0;i<arr.length;i++){
	                    		
								myVolumes[i]=arr[i].volume;
	                    		
	                    		
	                    	}
							
							
							//alert(myPes);
	                      // 填入数据
	                      chart.setOption({
	                    	  legend: {
	                  	        data:['成交额']
	                  	    },
	                      	xAxis: {
	                  	        type: 'category',
	                  	        boundaryGap: false,
	                  	        data: myDates
	                  	    },
	                  	    yAxis: {
	                  	        type: 'value'
	                  	    },
	                  	    series: [
	                  	        {
	                  	            name:'成交额',
	                  	            type:'line',
	                  	            stack: '亿元',
	                  	            data:myVolumes
	                  	        }
	                  	    ]
	
	                      });  
	              		
	              	}else if(code==6){
	              		
	              	}else if(code==7){
	              		
	              	}
    	          
    	       }
    	 };
    	 $("#searchForm").ajaxSubmit(options);
    	 
     }
     
     		//废弃，暂时保留
        function searchBak(){
        	var indexCode=$("#indexCode").val();
        	var code=$("#code").val();
        	var startDate=$("#startDate").val();
        	var endDate=$("#endDate").val();
        	var searchForm=$("#searchForm").val();
        	alert(searchForm);
        	alert("startDate="+startDate);
        	alert("indexCode="+indexCode);
        	alert("code="+code);
        	chart.showLoading();
        	$.ajax({
        		  type: 'POST',
        		  url: _url,
        		  data: {'indexCode':indexCode},
        		  dataType: "json",
                  success: function(data){
                	 chart.hideLoading();
                	 var arr=data.value;
                	 
                  	 var myDates =new Array();
                  	 var myIndexs=new Array();
                  	 var myPes=new Array();
                  	 var myPbs=new Array();
                  	 var myTrades=new Array();
                  	 var myTurnovers=new Array();
                  	var myVolumes=new Array();
                  	
                  	for(var i=0;i<arr.length;i++){
                  		myDates[i]=arr[i].releaseDateString;
                  	}
                  	
                  	if(code==0){
                  		for(var i=0;i<arr.length;i++){
                      		
                      		myIndexs[i]=arr[i].indexEnd;
                      		
                      		
                      	}
                  		//alert(myPes);
                        // 填入数据
                        chart.setOption({
                      	  legend: {
                    	        data:['指数']
                    	    },
                        	xAxis: {
                    	        type: 'category',
                    	        boundaryGap: false,
                    	        data: myDates
                    	    },
                    	    yAxis: {
                    	        type: 'value'
                    	    },
                    	    series: [
                    	        {
                    	            name:'指数',
                    	            type:'line',
                    	            stack: '点位',
                    	            data:myIndexs
                    	        }
                    	    ]

                        });  
                  		
                		
                	}else if(code==1){
                		for(var i=0;i<arr.length;i++){
                      		
                      		myPes[i]=arr[i].pe;
                      		
                      		
                      	}
                		
                		//alert(myPes);
                        // 填入数据
                        chart.setOption({
                      	  legend: {
                    	        data:['市盈率']
                    	    },
                        	xAxis: {
                    	        type: 'category',
                    	        boundaryGap: false,
                    	        data: myDates
                    	    },
                    	    yAxis: {
                    	        type: 'value'
                    	    },
                    	    series: [
                    	        {
                    	            name:'市盈率',
                    	            type:'line',
                    	            stack: '大小',
                    	            data:myPes
                    	        }
                    	    ]

                        });  
                		
                	}else if(code==2){
						for(var i=0;i<arr.length;i++){
                      		
                      		myPbs[i]=arr[i].pb;
                      		
                      		
                      	}
						
						
						//alert(myPes);
                        // 填入数据
                        chart.setOption({
                      	  legend: {
                    	        data:['市净率']
                    	    },
                        	xAxis: {
                    	        type: 'category',
                    	        boundaryGap: false,
                    	        data: myDates
                    	    },
                    	    yAxis: {
                    	        type: 'value'
                    	    },
                    	    series: [
                    	        {
                    	            name:'市净率',
                    	            type:'line',
                    	            stack: '大小',
                    	            data:myPbs
                    	        }
                    	    ]

                        });  
                		
                	}else if(code==3){
                		
						for(var i=0;i<arr.length;i++){
                      		
							myTrades[i]=arr[i].trade;
                      		
                      		
                      	}
						
						
						//alert(myPes);
                        // 填入数据
                        chart.setOption({
                      	  legend: {
                    	        data:['换手率']
                    	    },
                        	xAxis: {
                    	        type: 'category',
                    	        boundaryGap: false,
                    	        data: myDates
                    	    },
                    	    yAxis: {
                    	        type: 'value'
                    	    },
                    	    series: [
                    	        {
                    	            name:'换手率',
                    	            type:'line',
                    	            stack: '大小',
                    	            data:myTrades
                    	        }
                    	    ]

                        });  
                		
                	}else if(code==4){
                		
						for(var i=0;i<arr.length;i++){
                      		
							myTurnovers[i]=arr[i].turnover;
                      		
                      		
                      	}
						
						
						//alert(myPes);
                        // 填入数据
                        chart.setOption({
                      	  legend: {
                    	        data:['成交量']
                    	    },
                        	xAxis: {
                    	        type: 'category',
                    	        boundaryGap: false,
                    	        data: myDates
                    	    },
                    	    yAxis: {
                    	        type: 'value'
                    	    },
                    	    series: [
                    	        {
                    	            name:'成交量',
                    	            type:'line',
                    	            stack: '亿股',
                    	            data:myTurnovers
                    	        }
                    	    ]

                        });  
                		
                		
                		
                	}else if(code==5){
						for(var i=0;i<arr.length;i++){
                      		
							myVolumes[i]=arr[i].volume;
                      		
                      		
                      	}
						
						
						//alert(myPes);
                        // 填入数据
                        chart.setOption({
                      	  legend: {
                    	        data:['成交额']
                    	    },
                        	xAxis: {
                    	        type: 'category',
                    	        boundaryGap: false,
                    	        data: myDates
                    	    },
                    	    yAxis: {
                    	        type: 'value'
                    	    },
                    	    series: [
                    	        {
                    	            name:'成交额',
                    	            type:'line',
                    	            stack: '亿元',
                    	            data:myVolumes
                    	        }
                    	    ]

                        });  
                		
                	}else if(code==6){
                		
                	}else if(code==7){
                		
                	}

                  	    
                            
                  }
        	});
        	
        }
        
        $.fn.datebox.defaults.formatter = function(date){
        	var y = date.getFullYear();
        	var m = date.getMonth()+1;
        	var d = date.getDate();
        	//return m+'/'+d+'/'+y;
        	return y+"-"+m+'-'+d;
        }
        
    </script>
</html>
