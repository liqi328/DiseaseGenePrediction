package com.liqi.test.graph;


import junit.framework.TestCase;

import org.junit.Test;

import com.liqi.graph.Node;

public class TestNode extends TestCase {

	@Test
	public void testEquals() {
		Node node1 = new Node("1");
		Node node2 = new Node("1");
		Node node3 = new Node("2");
		
		assertEquals(true, node1.equals(node2));
		assertEquals(false, node1.equals(node3));
		assertEquals(false, node1.equals(null));
		assertEquals(false, node2.equals(new Object()));
	}
	
	@Test
	public void testHashCode(){
		Node node1 = new Node("1");
		Node node2 = new Node("1");
		Node node3 = new Node("2");
		
		assertEquals(true, node1.hashCode() == node2.hashCode());
	}

}
