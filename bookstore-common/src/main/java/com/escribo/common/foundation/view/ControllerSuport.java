package com.escribo.common.foundation.view;
public interface ControllerSuport<T> {

	public void saveOrUpdateObject(T object);
	
	public void deleteObject(T Object);

}
