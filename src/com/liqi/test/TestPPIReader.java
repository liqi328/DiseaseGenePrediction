package com.liqi.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.liqi.graph.Graph;
import com.liqi.reader.ReaderFacade;
import com.liqi.util.WriterUtil;

public class TestPPIReader extends TestCase{
	@Test
	public void testRead(){
		String filename = "E:/2013�����о�/ʵ������/PPI-6��/HPRD_ppi.txt";
		Graph graph = ReaderFacade.getInstance().getPPI(filename);
		System.out.println("-------------print----------");
		WriterUtil.write("E:/2013�����о�/ʵ������/PPI-6��/ppi_test2.txt", graph.toString());
		assertEquals(true,true);
	}
}
