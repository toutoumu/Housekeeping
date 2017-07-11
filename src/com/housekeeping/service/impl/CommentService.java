package com.housekeeping.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import com.housekeeping.dao.ICommentDao;
import com.housekeeping.entity.Comment;
import com.housekeeping.entity.wrap.Comments;
import com.housekeeping.service.ICommentService;
import com.housekeeping.utils.ServiceErrorBuilder;

public class CommentService implements ICommentService {

	private ICommentDao<Comment> commentDao;

	public void setCommentDao(ICommentDao<Comment> commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public Response addComment(Comment comment) {
		if (comment != null && comment.getEmployeeId() != 0 && comment.getUserId() != 0) {
			int id = (int) commentDao.save(comment);
			comment.setId(id);
			return Response.ok(comment).build();
		}
		throw ServiceErrorBuilder.badRequestError("添加失败");
	}

	@Override
	public Response deleteComment(int id) {
		if (id != 0) {
			Comment comment = commentDao.get(id);
			if (comment != null) {
				commentDao.remove(comment);
				return Response.ok().build();
			}
			throw ServiceErrorBuilder.badRequestError("评论不存在");
		}
		throw ServiceErrorBuilder.badRequestError("删除评论时主键不能为空");
	}

	@Override
	public Response updateComment(Comment comment) {
		if (comment != null && comment.getEmployeeId() != 0 && comment.getUserId() != 0) {
			if (commentDao.update(comment)) {
				return Response.ok().build();
			}
		}
		throw ServiceErrorBuilder.badRequestError("更新评论时主键不能为空");
	}

	@Override
	public Comment getComment(int id) {
		if (id != 0) {
			Comment comment = commentDao.get(id);
			return comment;
		}
		throw ServiceErrorBuilder.badRequestError("查询评论时主键不能为空");
	}

	@Override
	public Comments getCommentByEmployeeId(int employeeId) {
		Comments comments = new Comments();
		List<Comment> commentList = commentDao.findBy("employeeId", employeeId);
		if (commentList == null) {
			return null;
		}
		comments.setComments(commentList);
		return comments;
	}

}
