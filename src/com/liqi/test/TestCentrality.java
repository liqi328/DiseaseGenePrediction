package com.liqi.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.liqi.centrality.model.Centrality;

public class TestCentrality {

	@Test
	public void test() {
		Centrality cty = new Centrality();
		cty.setHprdId("0001");
		cty.setNumberOfDisease(2);
		cty.setDC(6);
		
		Centrality cty2 = new Centrality();
		cty2.setHprdId("0002");
		cty2.setNumberOfDisease(1);
		cty2.setDC(2);
		
		int i = cty2.compareTo(cty);
		
		Centrality.CentralityByIdComparator idComparator = cty.new CentralityByIdComparator();
		int j = idComparator.compare(cty, cty2);
		
		Centrality.CentralityComparator cComparator = cty.new CentralityComparator("DC");
		int r = cComparator.compare(cty, cty2);
		
		assertEquals(1, i);
		assertEquals(-1, j);
		assertEquals(-1, r);
	}

}
