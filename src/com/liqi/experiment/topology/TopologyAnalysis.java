package com.liqi.experiment.topology;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.HprdIdMapping;
import com.liqi.model.Tissue;
import com.liqi.model.TissueSpecificGeneExpression;
import com.liqi.networkcreator.TissueSpecificPPINetworkCreator;
import com.liqi.networkcreator.TissueSpecificPPINetworkCreator2;
import com.liqi.reader.ReaderFacade;
import com.liqi.util.WriterUtil;

public class TopologyAnalysis {
	private Graph ppi;
	Map<String, TissueSpecificGeneExpression> tissueSpecificGeneExpression;
	Map<String, Tissue> tissueMap;
	
	public void getPPI(){
		String filename = "E:/2013疾病研究/实验数据/topologyAnalysis/HPIN_NoRepeat_Symbol.txt";
		ppi = ReaderFacade.getInstance().getPPI(filename);
		
		System.out.println("Node size: " + ppi.getNodesNumber());
		System.out.println("Edge size: " + ppi.getEdgesNumber());
	}

	public void ppiConversion(){		
		String hprdFilename = "E:/2013疾病研究/疾病数据/HumanPPI/HPRD_Release9_062910/FLAT_FILES_072010/HPRD_ID_MAPPINGS.txt";
		Map<String, HprdIdMapping> hprdIdMappingMap = ReaderFacade.getInstance().getHprdIdIndexedIdMappingMap(hprdFilename);
		
		Map<String, HprdIdMapping> geneNameMappingMap = new HashMap<String, HprdIdMapping>();
		
		HprdIdMapping hprdIdMapping = null;
		Iterator<HprdIdMapping> itr = hprdIdMappingMap.values().iterator();
		while(itr.hasNext()){
			hprdIdMapping = itr.next();
			geneNameMappingMap.put(hprdIdMapping.getGeneSymbol(), hprdIdMapping);
		}
		
		int count = 0;
		Node node = null;
		Iterator<Node> nodeItr = ppi.createNodesIterator();
		while(nodeItr.hasNext()){
			node = nodeItr.next();
			hprdIdMapping = geneNameMappingMap.get(node.getName());
			if(hprdIdMapping == null){
				System.out.println(node.getName());
				++count;
			}			
		}
		System.out.println("count: " + count);
	}
	
	public void getTissueSpecificGeneExpression(){
		String filename = "E:/2013疾病研究/疾病数据/TissueS_geneExpression/GDS596_full.soft";
		tissueSpecificGeneExpression =
				ReaderFacade.getInstance().getTissueSpecificGeneExpressionMap(filename);
		tissueMap = ReaderFacade.getInstance().getTissueMap(filename);
	}
	
	public void createTissueSpecificNetwork(){
		TissueSpecificPPINetworkCreator tsnCreator = new TissueSpecificPPINetworkCreator2(ppi, tissueMap, tissueSpecificGeneExpression, null);
		tsnCreator.create();
		Map<String, Graph> ppiMap = tsnCreator.getResultPPIMap();
		Iterator<Map.Entry<String, Graph>> itr = ppiMap.entrySet().iterator();
		Map.Entry<String, Graph> entry = null;
		String dir = "E:/2013疾病研究/实验数据/topologyAnalysis/TSPPIN/";
		while(itr.hasNext()){
			entry = itr.next();
			String filename = dir + entry.getKey() + ".txt";
			WriterUtil.write(filename, entry.getValue().edgesToString());
		}
	}
	
	public static void main(String[] args){
		TopologyAnalysis ta = new TopologyAnalysis();
		ta.getPPI();
		ta.getTissueSpecificGeneExpression();
		ta.createTissueSpecificNetwork();
		//ta.ppiConversion();
	}
}
