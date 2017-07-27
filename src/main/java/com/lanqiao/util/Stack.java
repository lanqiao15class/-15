package com.lanqiao.util;

import java.util.LinkedList;

public class Stack<T extends TreeNode> {
	private LinkedList list = new LinkedList();

	public void push(T v) {
		list.addFirst(v);
	}

	public void push(Object v) {
		list.addFirst(v);
	}

	public T top() {
		return (T) list.getFirst();
	}

	public Object topObj() {
		return list.getFirst();
	}

	public T pop() {
		return (T) list.removeFirst();
	}

	public Object popObj() {
		return list.removeFirst();
	}

	public String toString() {
		return list.toString();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
}