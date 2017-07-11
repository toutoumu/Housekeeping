<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 弹出窗体 -->
<div data-dojo-type="dijit/Dialog" data-dojo-id="myDialog" title="添加菜单">
	<div data-dojo-type="dijit/form/Form" id="addForm" data-dojo-id="addForm" encType="multipart/form-data" action="" method="post">
		<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="10">
			<tr>
				<td><label for="userName">会员姓名</label></td>
				<td><input type="text" name="userName" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
				<td><label for="city">城市</label></td>
				<td><input type="text" name="city" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
			</tr>
			<tr>
				<td><label for="phoneNumber">电话</label></td>
				<td><input type="text" name="phoneNumber" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
				<td><label for="password">密码</label></td>
				<td><input type="text" name="password" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
			</tr>
			<tr>
				<td><label for="grade">等级</label></td>
				<td><input type="text" name="grade" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
				<td><label for="consumeTimes">消费次数</label></td>
				<td><input type="text" name="consumeTimes" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" value="0"/></td>
			</tr>
			<tr>
				<td><label for="balance">余额</label></td>
				<td><input type="text" name="balance" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
				<td><label for="score">积分</label></td>
				<td><input type="text" name="score" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" /></td>
			</tr>
		</table>
		<div class="dijitDialogPaneActionBar">
			<!-- 这里的按钮不能是type='submit'  -->
			<button data-dojo-type="dijit/form/Button" type="button" onclick="add()">确定</button>
			<button data-dojo-type="dijit/form/Button" type="button" data-dojo-props="onClick:function(){myDialog.hide();}" id="cancel">取消</button>
		</div>
	</div>
</div>
