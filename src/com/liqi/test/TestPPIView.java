package com.liqi.test;


import junit.framework.TestCase;

import org.junit.Test;

import com.liqi.graph.Graph;
import com.liqi.view.PPIView;

public class TestPPIView extends TestCase {

	@Test
	public void testReadPPI() {
		String filename = "E:/2013�����о�/ʵ������/PPI-6��/HPRD_ppi.txt";
		Graph graph = PPIView.readPPI(filename);
		System.out.println("-------------print----------");
		System.out.println(graph.toString());
		
		assertEquals(true,true);
	}

}
