package com.lanqiao.util;

public class TreeNode {

	protected String id;

	protected String parent;

	protected String text;

	protected int print;

	public TreeNode(){
		
	}
	
	public TreeNode(String id, String text, String parent) {
		this.id = id;
		this.text = text;
		this.parent = parent;
		this.print = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPrint() {
		return print;
	}

	public void setPrint(int print) {
		this.print = print;
	}

}
