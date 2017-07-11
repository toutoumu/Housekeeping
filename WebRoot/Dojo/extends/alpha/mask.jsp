<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script language="javascript" type="text/javascript">
	/**
	 * 显示遮罩层 
	 */
	function showMask(message) {
		document.getElementById("bg").style.display = "block";
		document.getElementById("show").style.display = "block";
	}
	/**
	 *隐藏遮罩层
	 */
	function hideMask() {
		document.getElementById("bg").style.display = 'none';
		document.getElementById("show").style.display = 'none';
	}
</script>
<style type="text/css">
.dijitDialogUnderlay {
	background: #666666;
	opacity: 0.5;
}

#bg {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1001;
	-moz-opacity: 0.7;
	opacity: 0.70;
	filter: alpha(opacity = 70);
}

#show {
	display: none;
	position: absolute;
	top: 45%;
	left: 40%;
	width: 20%;
	height: 34px;
	padding: 8px;
	border: 8px solid #E8E9F7;
	background-color: white;
	z-index: 1002;
	overflow: auto;
}
</style>
<div id="bg"></div>
<div id="show">
	<div style="vertical-align: middle; line-height: 25px; margin: 0px; padding: 0px; border: none;">
		<img alt="请稍后..." src="Dojo/extends/alpha/busy.gif"
			style="vertical-align: middle; line-height: 25px; margin: 0px; padding: 0px; border: none;" /><span
			style="vertical-align: middle; line-height: 25px; margin-left: 10px;">正在加载请稍后...</span>
	</div>
</div>
