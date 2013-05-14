package com.liqi.graph;

public class Node implements Cloneable{
	private String name;
	
	public Node(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public Object clone(){
		Node obj = null;
		try {
			obj = (Node) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
		return obj;
	}
	
	@Override
	public String toString(){
		return name.toString();
	}
	
	@Override
	public boolean equals(Object rhs){
		if(this == rhs){
			return true;
		}
		
		if(!(rhs instanceof Node)){
			return false;
		}
		
		Node node = (Node)rhs;
		return this.name.equals(node.name);
	}
	
	@Override
	public int hashCode(){
		int result = 17;
		final int prime = 31;
		result = prime * result + ((name == null)? 0 : name.hashCode());
		return result;
	}

}
