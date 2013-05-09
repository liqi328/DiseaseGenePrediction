package com.liqi.experiment;

import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Edge;
import com.liqi.graph.Graph;
import com.liqi.graph.Node;
import com.liqi.model.Gene;

/* ͳ��ÿ��������ھӽڵ���Ŀ���ھӽڵ����²��������Ŀ
 * 
 * */
public class NeighborStatistic extends AbstractStatistic{
	
	public NeighborStatistic(Buffer buffer){
		super(buffer);
	}
	
	@Override
	public void run() {
		statistic();
	}

	@Override
	public String getStatisticResult() {
		StringBuffer sb = new StringBuffer();
		sb.append("Gene_HprdId\tOmim_id\tdegree\tneighborDisease\n");
		Iterator<StatisticData> itr = sdList.iterator();
		StatisticData sd = null;
		while(itr.hasNext()){
			sd = itr.next();
			sb.append(sd.getGene().getHprdId()).append("\t");
			sb.append(sd.getGene().getOmimId()).append("\t");
			sb.append(sd.getDegree()).append("\t");
			sb.append(sd.getNeighborDiseaseGeneCount()).append("\n");
		}
		return sb.toString();
	}
	
	
	private void statistic(){		
		Map<String, Gene> hprdGene = buffer.getHprdGene();
		Iterator<Gene> itr = hprdGene.values().iterator();
		
		Gene gene = null;
		while(itr.hasNext()){
			gene = itr.next();
			processOneGene(gene);
		}		
	}
	
	private void processOneGene(Gene gene){
		Graph ppi = buffer.getPpi();
		Node node = new Node(gene.getHprdId());
		StatisticData sd = new StatisticData(gene);
		sd.setDegree(ppi.getNeighborsNumber(node));
		
		Map<String, Gene> hprdGene = buffer.getHprdGene();
		
		Iterator<Edge> itr = ppi.createNeighborsIterator(node);
		if(itr == null){
			System.out.println("[ " + node.getName() + " ] does not appear in this ppi. ");
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
			if(hprdGene.containsKey(e.getToNode().getName())){
				++count;
			}
		}
		sd.setNeighborDiseaseGeneCount(count);
		sdList.add(sd);
	}
	
}
