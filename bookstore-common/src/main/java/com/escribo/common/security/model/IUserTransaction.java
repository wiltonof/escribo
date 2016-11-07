package com.escribo.common.security.model;

import com.escribo.common.foundation.model.IModel;

public interface IUserTransaction extends IModel {
	public IUser getUser();

	public void setUser(IUser user);

	public IPermission getPermission();

	public void setPermission(IPermission permission);

	public Long getPrimaryId();

	public void setPrimaryId(Long primaryId);

	public String getPrimaryCod();

	public void setPrimaryCod(String primaryCod);

	public Long getSecondaryId();

	public void setSecondaryId(Long secondaryId);

	public String getSecondaryCod();

	public void setSecondaryCod(String secondaryCod);
}
