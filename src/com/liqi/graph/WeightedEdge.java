package com.liqi.graph;

public class WeightedEdge extends Edge implements Cloneable {
	private double weight;
	
	public WeightedEdge(Node from, Node to) {
		this(from, to, 1);
	}
	
	public WeightedEdge(Node from, Node to, double weight){
		super(from, to);
		this.setWeight(weight);
	}
	
	public double getWeight(){
		return this.weight;
	}
	
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	@Override
	public Object clone(){
		WeightedEdge we = null;
		we = (WeightedEdge)super.clone();
		return we;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer(super.toString());
		buf.append("\t").append(this.weight);
		return buf.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		if (!super.equals(obj))
			return false;
		
		WeightedEdge other = (WeightedEdge) obj;
		return Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
	}
}
