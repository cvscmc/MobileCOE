package com.cmc.store.mcoe.pojo;

public class Message {
	
	private int messageId;
	private String dealMessage;
	
	private Store store;
	private Branch branch;

	public String getDealMessage() {
		return dealMessage;
	}

	public void setDealMessage(String dealMessage) {
		this.dealMessage = dealMessage;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

}
