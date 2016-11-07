package com.escribo.common.security.model;

import com.escribo.common.foundation.model.IModel;

public interface IUserAccessData extends IModel {
	public Long getUserId();

	public void setUserId(Long userId);

	public String getIp();

	public void setIp(String ip);

	public Integer getAccessCounter();

	public void setAccessCounter(Integer accessCounter);
}
