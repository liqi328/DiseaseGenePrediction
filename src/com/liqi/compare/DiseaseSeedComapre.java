package com.liqi.compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.liqi.geneutil.GeneIdMappingUtil;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.Gene;
import com.liqi.networkcreator.LOrderDiseaseNetworkCreator;
import com.liqi.reader.ReaderFacade;

public class DiseaseSeedComapre {
	
	public static void main(String[] args){
		for(int i = 0; i < 2; ++i){
			compareSeed(getFileNameA(i), getFileNameB(i));
			compareNetwork(getFileNameA(i), getFileNameB(i));
		}
	}
	
	public static void compareSeed(String filenameA, String filenameB){
		System.out.println("---------------compare seed----------------");
		Map<String, String> geneMap = readFileA(filenameA);
		
		Set<String> geneSet = readFileB(filenameB);
		
		for(String geneName : geneSet){
			if(geneMap.containsKey(geneName)){
				System.out.println(geneName);
			}
		}
		System.out.println("-------------------------------");
	}
	
	public static void compareNetwork(String filenameA, String filenameB){
		System.out.println("---------------compare network----------------");
		String ppiFilename = "E:/2013疾病研究/实验数据/神经退行性疾病/HPRD_ppi.txt";
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		Map<String, Gene> diseaseGeneMap = ReaderFacade.getInstance().getOmimDiseaseGeneMap(filenameA);
		GeneIdMappingUtil.setOtherGeneId(diseaseGeneMap);
		Graph subPPI1 = getSubNetwork(ppi, diseaseGeneMap, 1);;
		
		Set<String> geneSet = readFileB(filenameB);
		Map<String, Gene> diseaseGeneMap2 = new HashMap<String, Gene>();
		for(String geneName : geneSet){
			Gene gene = new Gene();
			gene.setSymbols(geneName);
			diseaseGeneMap2.put(geneName, gene);
		}
		
		GeneIdMappingUtil.setOtherGeneId2(diseaseGeneMap2);
		//Map<String, Gene> omimIdIdexedGeneMap = GeneIdMappingUtil.transform2HprdIdIndexedGeneMap(diseaseGeneMap2);
		
		Graph subPPI2 = getSubNetwork(ppi, diseaseGeneMap2, 1);
		
		findCommonNode(subPPI1, subPPI2);
		System.out.println("-------------------------------");
	}
	
	private static void findCommonNode(Graph g1, Graph g2){
		System.out.println("G1: nodes[" + g1.getNodesNumber() + "], edges[" + g1.getEdgesNumber() + "]");
		System.out.println("G2: nodes[" + g2.getNodesNumber() + "], edges[" + g2.getEdgesNumber() + "]");
		
		Iterator<Node> itr = g1.createNodesIterator();
		while(itr.hasNext()){
			Node n = itr.next();
			if(g2.containsNode(n)){
				System.out.println(n);
			}
		}
	}
	
	private static Graph getSubNetwork(Graph ppi, Map<String, Gene> diseaseGeneMap, int level){
		LOrderDiseaseNetworkCreator ldnCreator = new LOrderDiseaseNetworkCreator(
				ppi, diseaseGeneMap, 1);
		ldnCreator.create();
		return ldnCreator.getResultPPI();
	}
	
	private static String getFileNameA(int i){
		String[] filenameA = {"E:/2013疾病研究/疾病数据/OMIM/各种疾病/Leukemia.txt",
				"E:/2013疾病研究/疾病数据/OMIM/各种疾病/colorectal.txt",
		};
		return filenameA[i];
	}
	
	private static String getFileNameB(int i){
		String[] filenameB = {"E:/2013疾病研究/Data_LiuQing/Leukemia/PubMeth_Leukemia.txt",
				"E:/2013疾病研究/Data_LiuQing/Colorectal Cancer/PubMeth_ColorectalCancer.txt",
		};
		return filenameB[i];
	}
	
	private static Map<String, String> readFileA(String filenameA){
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filenameA)));
			Map<String, String> geneMap = new HashMap<String, String>();
			String line = null;
			String[] cols = null;
			while((line = in.readLine()) != null){
				cols = line.split("\\|");
				geneMap.put(cols[1], cols[0]);
			}
			
			in.close();
			return geneMap;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Set<String> readFileB(String filenameB){
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filenameB)));
			Set<String> geneSet = new HashSet<String>();
			String line = null;
			String[] cols = null;
			while((line = in.readLine()) != null){
				cols = line.split("\t| ");
				geneSet.add(cols[0]);
			}
			in.close();
			return geneSet;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
