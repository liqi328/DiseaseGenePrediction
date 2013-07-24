package com.liqi.statistic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.liqi.experiment.ExperimentDataBuffer;
import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.Gene;

/**
 * 分析PPI网络中，致病基因的邻居
 * 1：邻居中为致病基因
 * */
public class GeneNeighborAnalysis {
	private List<StatisticData> sdList;
	
	private Map<String, Gene> diseaseGeneMap;
	private Graph ppi;
	public GeneNeighborAnalysis(Map<String, Gene> diseaseGeneMap, Graph ppi){
		this.diseaseGeneMap = diseaseGeneMap;
		this.ppi = ppi;
		
		sdList = new ArrayList<StatisticData>();
	}
	
	public void run() {
		statistic();
	}

	public String getStatisticResult() {
		StringBuffer sb = new StringBuffer();
		sb.append("Gene_HprdId\tOmim_id\tSymbols\tdegree\tDiseaseNeighbors\n");
		Iterator<StatisticData> itr = sdList.iterator();
		StatisticData sd = null;
		while(itr.hasNext()){
			sd = itr.next();
			sb.append(sd.getGene().getHprdId()).append("\t");
			sb.append(sd.getGene().getOmimId()).append("\t");
			sb.append(sd.getGene().getSymbols()).append("\t");
			sb.append(sd.getDegree()).append("\t");
			sb.append(sd.getNeighborDiseaseGeneCount()).append("\n");
		}
		return sb.toString();
	}
	
	
	private void statistic(){		
		System.out.println("2 after:Disease Gene Map size=" + diseaseGeneMap.size());
		Iterator<Gene> itr = diseaseGeneMap.values().iterator();
		
		Gene gene = null;
		while(itr.hasNext()){
			gene = itr.next();
			processOneGene(gene);
		}		
	}
	
	private void processOneGene(Gene gene){
		Node node = new Node("" + Integer.parseInt(gene.getHprdId()));
		if(node.getName() == null){
			System.out.println("Error in Neighbor Statistic 63\n" + gene.toString());
		}
		StatisticData sd = new StatisticData(gene);
		sd.setDegree(ppi.getNeighborsNumber(node));
		
		Iterator<Edge> itr = ppi.createNeighborsIterator(node);
		if(itr == null){
			System.out.println("[ hprd_id: " + node.getName()+", omim_id: "+ gene.getOmimId() + " ] does not appear in this ppi. ");
			return;
		}
		Edge e = null;
		int count = 0;
		while(itr.hasNext()){
			e = itr.next();
			if(e.isSelfLoop()){
				sd.setDegree(sd.getDegree() - 1);
				continue;
			}
			if(isDiseaseGene(e.getToNode().getName())){
				++count;
			}
		}
		sd.setNeighborDiseaseGeneCount(count);
		sdList.add(sd);
	}
	
	private boolean isDiseaseGene(String hprdId){
		Iterator<Gene> itr = diseaseGeneMap.values().iterator();
		while(itr.hasNext()){
			Gene gene = itr.next();
			if(Integer.parseInt(gene.getHprdId()) == Integer.parseInt(hprdId)){
				return true;
			}
		}
		return false;
	}
}
