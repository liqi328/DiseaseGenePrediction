package com.liqi.graph;

public class Edge implements Cloneable{
	private Node from;
	private Node to;
	
	public Edge(Node from, Node to){
		this.from = from;
		this.to = to;
	}

	public Node getFromNode(){
		return this.from;
	}
	
	public Node getToNode(){
		return this.to;
	}
	
	
	public boolean isSelfLoop(){
		return this.from.equals(this.to);
	}
	
	@Override
	public Object clone(){
		Edge e = null;
		try {
			e = (Edge)super.clone();
			e.from = (Node) this.from.clone();
			e.to = (Node) this.to.clone();
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return e;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append(this.from.toString()).append("\t");
		buf.append(this.to.toString());
		return buf.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge e = (Edge) obj;
		boolean flag = (this.from.equals(e.from) && this.to.equals(e.to))
				|| (this.from.equals(e.to) && this.to.equals(e.from));
		return flag;
	}

	
}
