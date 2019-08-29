package com.bfxy.springboot.entity;

import java.io.Serializable;

public class AccountMessage implements Serializable {

	/**
	 * serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -658862025150425706L;
	private String name;
	private Integer userId;
	private String messageId;
	private Double  account;
	private Integer status;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getAccount() {
		return account;
	}
	public void setAccount(Double account) {
		this.account = account;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
