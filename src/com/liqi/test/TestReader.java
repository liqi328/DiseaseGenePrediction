package com.liqi.test;

import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.liqi.model.DiseaseTissueAssociation;
import com.liqi.model.Tissue;
import com.liqi.model.TissueSpecificGeneExpression;
import com.liqi.reader.DiseaseTissueAssociationReader;
import com.liqi.reader.TissuSpecificGeneExpressionReader;
import com.liqi.util.MapToStringUtil;
import com.liqi.util.WriterUtil;

public class TestReader extends TestCase{
	
	@Test
	public void testRead(){
		String filename = "E:/2013疾病研究/疾病数据/TissueS_geneExpression/GDS596_full.soft";
		
		TissuSpecificGeneExpressionReader reader = new TissuSpecificGeneExpressionReader(filename);
		reader.read();
		
		Map<String, TissueSpecificGeneExpression> tsGeneExpMap = reader.getTissueSpecificGeneExpressionMap();
		Map<String, Tissue> tissueMap = reader.getTissueMap();
		
		String tissueFilename = "E:/2013疾病研究/疾病数据/TissueS_geneExpression/tissue.txt";
		String tsGeneExpFilename = "E:/2013疾病研究/疾病数据/TissueS_geneExpression/tsGeneExp.txt";
		
		WriterUtil.write(tissueFilename, MapToStringUtil.mapToString(tissueMap));
		WriterUtil.write(tsGeneExpFilename, MapToStringUtil.mapToString(tsGeneExpMap));
		
		assertEquals(true, true);
	}
	
	@Test
	public void testDiseaseTissueReader(){
		String filename = "E:/2013疾病研究/实验数据/神经退行性疾病/DiseaseTissueAssociationMatrixLageEtAl2008PNAS.tbl";
		
		DiseaseTissueAssociationReader reader = new DiseaseTissueAssociationReader(filename);
		reader.read();
		
		Map<String, DiseaseTissueAssociation> dtaMap = reader.getDiseaseTissueAssociationMap();
		
		String dtaFilename = "E:/2013疾病研究/实验数据/神经退行性疾病/dta.txt";
		
		WriterUtil.write(dtaFilename, MapToStringUtil.mapToString(dtaMap));
		
		assertEquals(true, true);
	}
}
