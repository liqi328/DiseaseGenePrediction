package com.liqi.test.graph;


import junit.framework.TestCase;

import org.junit.Test;

import com.liqi.graph.Edge;
import com.liqi.graph.Node;

public class TestEdge extends TestCase {

	@Test
	public void testEquals() {
		Node node1 = new Node("1");
		Node node2 = new Node("1");
		Node node3 = new Node("2");
		Node node4 = new Node("4");
		
		Edge edge1 = new Edge(node1, node3);
		Edge edge2 = new Edge(node2, node3);
		Edge edge3 = new Edge(node1, node4);
		Edge edge4 = new Edge(node3, node1);
		
		assertEquals(true, edge1.equals(edge2));
		assertEquals(false, edge1.equals(edge3));
		assertEquals(true, edge1.equals(edge4));
	}

}
