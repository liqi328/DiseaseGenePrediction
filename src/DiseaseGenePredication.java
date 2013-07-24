import java.util.Map;

import com.liqi.geneutil.GeneIdMappingUtil;
import com.liqi.graph.Graph;
import com.liqi.model.Gene;
import com.liqi.networkcreator.LOrderDiseaseNetworkCreator;
import com.liqi.reader.ReaderFacade;
import com.liqi.util.WriterUtil;



/*
 * 疾病预测
 * */
public class DiseaseGenePredication {
	public static void main(String[] args){
		createCandidateGene();
	}
	
	/*
	 * 抽取候选基因-方法1：疾病基因 + 邻居
	 * */
	public static void createCandidateGene(){
		String ppiFilename = "E:/2013疾病研究/实验数据/DiseasePrediction/HPRD_ppi.txt";
		String diseaseGeneFilename = "E:/2013疾病研究/疾病数据/OMIM/morbidmap";	
		
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		Map<String, Gene> omimIdIndexedDiseaseGeneMap = ReaderFacade.getInstance().getOmimDiseaseGeneMap(diseaseGeneFilename);
		GeneIdMappingUtil.setOtherGeneId(omimIdIndexedDiseaseGeneMap);
		
		LOrderDiseaseNetworkCreator ldnCreator = new LOrderDiseaseNetworkCreator(ppi, omimIdIndexedDiseaseGeneMap, 1);
		ldnCreator.create();
		Graph subPPI = ldnCreator.getResultPPI();
		
		String edgeFilename = "E:/2013疾病研究/实验数据/DiseasePrediction/all_disease_ppi_edges_1.txt";
		String nodeFilename = "E:/2013疾病研究/实验数据/DiseasePrediction/all_disease_ppi_nodes_1.txt";
		WriterUtil.write(edgeFilename, subPPI.edgesToString());
		WriterUtil.write(nodeFilename, subPPI.nodesToString());
	}
	
	/*
	 * 抽取候选基因-方法2：组织特性基因表达数据
	 * */
	public static void createCandidateGene_2(){}
}
