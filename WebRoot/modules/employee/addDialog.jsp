<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 弹出窗体 -->
<div data-dojo-type="dijit/Dialog" data-dojo-id="myDialog" title="添加菜单">
	<div data-dojo-type="dijit/form/Form" id="addForm" data-dojo-id="addForm" encType="multipart/form-data" action=""
		method="post">
		<table style="border: 1px solid #9f9f9f; width: 100%;" cellspacing="10">
			<tr>
				<td>
					<label for="name">雇员姓名</label>
				</td>
				<td>
					<input type="text" name="name" required="true" maxlength="30" data-dojo-type="dijit/form/ValidationTextBox"
						trim="true" />
				</td>
				<td>
					<label for="name">雇员照片</label>
				</td>
				<td>
					<input type="text" name="photo" data-dojo-id="photoSrc" readonly="readonly" required="true"
						data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
				</td>
				<td rowspan="10" style="text-align: center; width: 200px;">
					<div style="text-align: center; width: 200px;">
						<img id="imgPhoto" alt="照片" width="200px" style="text-align: center;" />
					</div>
					<button data-dojo-type="dijit/form/Button" style="text-align: center;" button" onclick="showUpload()">选择头像</button>
				</td>

			</tr>
			<tr>
				<td>
					<label for="name">身份证号码</label>
				</td>
				<td>
					<input type="text" name="idCard" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
				</td>
				<td>
					<label for="name">手机号码</label>
				</td>
				<td>
					<input type="text" name="phoneNumber" required="true" data-dojo-type="dijit/form/ValidationTextBox"
						trim="true" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="name">雇员坐标</label>
				</td>
				<td>
					<input type="text" name="coordinat" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
				</td>
				<td>
					<label for="name">工作经验</label>
				</td>
				<td>
					<input type="text" name="workExperience" required="true" data-dojo-type="dijit/form/NumberTextBox"
						trim="true" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="name">雇员年龄</label>
				</td>
				<td>
					<input type="text" name="age" required="true" data-dojo-type="dijit/form/NumberTextBox" trim="true" />
				</td>
				<td>
					<label for="name">雇员特长</label>
				</td>
				<td>
					<input type="text" name="speciality" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="name">推荐值</label>
				</td>
				<td>
					<input type="text" name="rank" required="true" data-dojo-type="dijit/form/NumberTextBox" trim="true" />
				</td>
				<td>
					<label for="name">备注</label>
				</td>
				<td>
					<input type="text" name="remark" required="true" data-dojo-type="dijit/form/ValidationTextBox" trim="true" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="name">亲友姓名</label>
				</td>
				<td>
					<input type="text" name="relationName" required="true" data-dojo-type="dijit/form/ValidationTextBox"
						trim="true" />
				</td>
				<td>
					<label for="name">亲友电话</label>
				</td>
				<td>
					<input type="text" name="relationPhoneNumber" required="true" data-dojo-type="dijit/form/ValidationTextBox"
						trim="true" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="name">证件种类</label>
				</td>
				<td>
					<input type="text" name="credentialsCategory" required="true" data-dojo-type="dijit/form/ValidationTextBox"
						trim="true" />
				</td>
				<td>
					<label for="name">证件号码</label>
				</td>
				<td>
					<input type="text" name="credentialsNumber" required="true" data-dojo-type="dijit/form/ValidationTextBox"
						trim="true" />
				</td>
			</tr>
			<tr>
				<td>
					<label for="name">服务区域</label>
				</td>
				<td>
					<input type="text" name="serviceArea" required="true" data-dojo-type="dijit/form/ValidationTextBox"
						trim="true" />
				</td>
				<td>
					<label for="name">籍贯</label>
				</td>
				<td>
					<input type="text" name="nativeSlace" required="true" data-dojo-type="dijit/form/ValidationTextBox"
						trim="true" />
				</td>
			</tr>

			<tr>
				<td>备注</td>
				<td colspan="4">
					<input type="text" name="content" data-dojo-type="dijit/form/Textarea" required="true" trim="true" />
				</td>
			</tr>

		</table>
		<div class="dijitDialogPaneActionBar">
			<!-- 这里的按钮不能是type='submit'  -->
			<button data-dojo-type="dijit/form/Button" type="button" onclick="add()">确定</button>
			<button data-dojo-type="dijit/form/Button" type="button" data-dojo-props="onClick:function(){myDialog.hide();}"
				id="cancel">取消</button>
		</div>
	</div>
</div>
<div data-dojo-type="dijit/Dialog" data-dojo-id="uploadDialog" title="上传头像">
	<div data-dojo-type="dijit/form/Form" id="uploadForm" data-dojo-id="uploadForm" encType="multipart/form-data"
		action="" method="post">
		<div>
			选择图片
			<input type="file" name="file1" id="file" required="true" />
		</div>

		<div class="dijitDialogPaneActionBar">
			<!-- 这里的按钮不能是type='submit'  -->
			<button data-dojo-type="dijit/form/Button" type="button" onclick="upload()">上传</button>
			<button data-dojo-type="dijit/form/Button" type="button"
				data-dojo-props="onClick:function(){uploadDialog.hide();}">取消</button>
		</div>
	</div>
</div>