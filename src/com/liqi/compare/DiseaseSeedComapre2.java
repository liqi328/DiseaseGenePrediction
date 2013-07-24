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
import com.liqi.statistic.GeneNeighborAnalysis;
import com.liqi.util.WriterUtil;

public class DiseaseSeedComapre2 {
	public static void main(String[] args){
		for(int i = 0; i < 4; ++i){
			compare(getFileNameA(i), getFileNameB(i));
		}
	}
	
	public static void compare(String filenameA, String filenameB){
		System.out.println("---------------compare----------------");
		System.out.println("A = " + filenameA + "\nB = " + filenameB);
		
		Set<String> geneSetA = readFileA(filenameA);
		Set<String> geneSetB = readFileB(filenameB);
		
		String ppiFilename = "E:/2013疾病研究/实验数据/神经退行性疾病/HPRD_ppi.txt";
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		Map<String, Gene> diseaseGeneMapA = ReaderFacade.getInstance().getOmimDiseaseGeneMap(filenameA);
		GeneIdMappingUtil.setOtherGeneId(diseaseGeneMapA);
		Graph A_SubNet = getSubNetwork(ppi, diseaseGeneMapA, 1);
		
		Map<String, Gene> diseaseGeneMapB = new HashMap<String, Gene>();
		for(String geneName : geneSetB){
			Gene gene = new Gene();
			gene.setSymbols(geneName);
			diseaseGeneMapB.put(geneName, gene);
		}
		GeneIdMappingUtil.setOtherGeneId2(diseaseGeneMapB);
		Graph B_SubNet = getSubNetwork(ppi, diseaseGeneMapB, 1);
		
		compareSeed(geneSetA, geneSetB);
		compareNetwork(A_SubNet, B_SubNet);
		comapreAandB_SubNet("A and B_SubNet = ", diseaseGeneMapA, B_SubNet);
		comapreAandB_SubNet("B and A_SubNet = ", diseaseGeneMapB, A_SubNet);
		neighborAnalysis(diseaseGeneMapA, A_SubNet);
		System.out.println("--------------end----------------");
		
		WriterUtil.write("E:/2013疾病研究/实验数据/DiseasePrediction/"+ filenameA.substring(filenameA.lastIndexOf('/') + 1),
				A_SubNet.edgesToString());
	}
	
	private static void neighborAnalysis(Map<String, Gene> diseaseGeneMap, Graph ppi){
		System.out.println("---------Neighbor analysis-----------");
		GeneNeighborAnalysis analysis = new GeneNeighborAnalysis(diseaseGeneMap, ppi);
		analysis.run();
		System.out.println(analysis.getStatisticResult());
	}
	
	public static void compareSeed(Set<String> geneSetA, Set<String> geneSetB){
		System.out.println("---------------compare seed----------------");
		int count = 0;
		for(String geneName : geneSetB){
			if(geneSetA.contains(geneName)){
				System.out.println(geneName);
				++count;
			}
		}
		System.out.println("A and B = " + count);
		System.out.println("-------------------------------");
	}
	
	public static void compareNetwork(Graph A_SubNet, Graph B_SubNet){
		System.out.println("---------------compare network----------------");
		System.out.println("A_SubNet: nodes[" + A_SubNet.getNodesNumber() + "], edges[" + A_SubNet.getEdgesNumber() + "]");
		System.out.println("B_SubNet: nodes[" + B_SubNet.getNodesNumber() + "], edges[" + B_SubNet.getEdgesNumber() + "]");
		
		Iterator<Node> itr = A_SubNet.createNodesIterator();
		int count = 0;
		while(itr.hasNext()){
			Node n = itr.next();
			if(B_SubNet.containsNode(n)){
				//System.out.println(n);
				++count;
			}
		}
		System.out.println("A_SubNet and B_SubNet = " + count);
		System.out.println("-------------------------------");
	}
	
	private static void comapreAandB_SubNet(String title, Map<String, Gene> diseaseGeneMap, Graph g){
		int count = 0;
		Iterator<Gene> itr = diseaseGeneMap.values().iterator();
		while(itr.hasNext()){
			Node n = new Node(itr.next().getHprdId());
			if(g.containsNode(n)){
				//System.out.println(n);
				++count;
			}
		}
		
		System.out.println(title + count);
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
				"E:/2013疾病研究/疾病数据/OMIM/各种疾病/Lung.txt",
				"E:/2013疾病研究/疾病数据/OMIM/各种疾病/Ovarian.txt",
		};
		return filenameA[i];
	}
	
	private static String getFileNameB(int i){
		String[] filenameB = {"E:/2013疾病研究/Data_LiuQing/Leukemia/PubMeth_Leukemia.txt",
				"E:/2013疾病研究/Data_LiuQing/Colorectal Cancer/PubMeth_ColorectalCancer.txt",
				"E:/2013疾病研究/Data_LiuQing/Lung/PubMeth_Lung.txt",
				"E:/2013疾病研究/Data_LiuQing/Ovarian/PubMeth_Ovarian.txt",
		};
		return filenameB[i];
	}
	
	private static Set<String> readFileA(String filenameA){
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filenameA)));
			Set<String> geneSet = new HashSet<String>();
			String line = null;
			String[] cols = null;
			while((line = in.readLine()) != null){
				cols = line.split("\\|");
				String[] symbols = cols[1].split(",");
				for(String sym : symbols){
					geneSet.add(sym.trim());
				}
				
				geneSet.add(cols[1]);
			}
			
			in.close();
			return geneSet;
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
