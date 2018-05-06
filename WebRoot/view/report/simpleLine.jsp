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
       <!--   <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search" onClick="search();">查询</a>-->
        <button type="button" id="btn-search" class="easyui-linkbutton"  onClick="search();">查询</button>
      </form>  
	<br/>
     <!--  Search panel end -->
	
	
    <div id="main" style="width: 800px;height:600px;align:center;"></div>
     
   	       	 
</div>
</body>
   <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
       var chart = echarts.init(document.getElementById('main'), 'shine');

       option = {
	    		title: {
	       	        text: '市场趋势'
	       	    },
	       	    tooltip: {
	       	        trigger: 'axis'
	       	    },
	       	    legend: {
	       	        data:['指数']
	       	    },
	       	    grid: {
	       	        left: '3%',
	       	        right: '4%',
	       	        bottom: '3%',
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
     var _url = urls['msUrl'] + '/Report/getSimpleLineReport.do';
     search();
     /**
        $.get(_url).done(function (data) {
        	
        	var arr=data.value;
        	var myDates=new Array()
        	var myIndexs=new Array()
        	for(var i=0;i<arr.length;i++){
        		myDates[i]=arr[i].releaseDateString;
        		myIndexs[i]=arr[i].indexEnd;
        	}
        	//alert(myDates);
        	//alert(myIndexs);
        	chart.hideLoading();
            // 填入数据
            chart.setOption({
            	 xAxis: {
     		        type: 'category',
     		        boundaryGap: false,
     		        data: myDates
     		    },
     		    yAxis: {
     		        type: 'value'
     		    },
     		    series: [  {
    	            name:'指数',
    	            type:'line',
    	            stack: '点位',
    	            data:myIndexs
    	        }] 
            });
        });
        */
        
        function search(){
        	var indexCode=$("#indexCode option:selected").val();
        	//var va1=document.getElementById('indexCode').value;
        	//alert(va1);
        	alert(indexCode);
        	chart.showLoading();
        	$.ajax({
        		  type: 'POST',
        		  url: _url,
        		  data: {'indexCode':indexCode},
        		  dataType: "json",
                  success: function(data){
                	var arr=data.value;
                  	var myDates=new Array()
                  	var myIndexs=new Array()
                  	for(var i=0;i<arr.length;i++){
                  		myDates[i]=arr[i].releaseDateString;
                  		myIndexs[i]=arr[i].indexEnd;
                  	}
                  	//alert(myDates);
                  	//alert(myIndexs);
                  	chart.hideLoading();
                      // 填入数据
                      chart.setOption({
                      	 xAxis: {
               		        type: 'category',
               		        boundaryGap: false,
               		        data: myDates
               		    },
               		    yAxis: {
               		        type: 'value'
               		    },
               		    series: [  {
              	            name:'指数',
              	            type:'line',
              	            stack: '点位',
              	            data:myIndexs
              	        }] 
                      });
                            
                  }
        	});
        	
        }
     

        
    </script>
</html>
