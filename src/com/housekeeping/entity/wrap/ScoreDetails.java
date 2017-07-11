package com.housekeeping.entity.wrap;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.housekeeping.entity.ScoreDetail;

/**
 * 文件名: ScoreDetails.java
 * 编写者: toutoumu
 * 编写日期: 2014年10月16日
 * 简要描述: 积分明细包装类
 * 组件列表:
 * ********************  修改日志  **********************************
 * 修改人：           		  修改日期：
 * 修改内容：
 * 
 */
@XmlRootElement(name = "ScoreDetails")
public class ScoreDetails {
	private List<ScoreDetail> scoreDetails;

	public List<ScoreDetail> getScoreDetails() {
		return scoreDetails;
	}

	public void setScoreDetails(List<ScoreDetail> scoreDetails) {
		this.scoreDetails = scoreDetails;
	}
}
