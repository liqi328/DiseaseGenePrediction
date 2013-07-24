import java.util.Map;

import com.liqi.geneutil.GeneIdMappingUtil;
import com.liqi.graph.Graph;
import com.liqi.model.Gene;
import com.liqi.networkcreator.LOrderDiseaseNetworkCreator;
import com.liqi.reader.ReaderFacade;
import com.liqi.util.WriterUtil;



/*
 * ����Ԥ��
 * */
public class DiseaseGenePredication {
	public static void main(String[] args){
		createCandidateGene();
	}
	
	/*
	 * ��ȡ��ѡ����-����1���������� + �ھ�
	 * */
	public static void createCandidateGene(){
		String ppiFilename = "E:/2013�����о�/ʵ������/DiseasePrediction/HPRD_ppi.txt";
		String diseaseGeneFilename = "E:/2013�����о�/��������/OMIM/morbidmap";	
		
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		Map<String, Gene> omimIdIndexedDiseaseGeneMap = ReaderFacade.getInstance().getOmimDiseaseGeneMap(diseaseGeneFilename);
		GeneIdMappingUtil.setOtherGeneId(omimIdIndexedDiseaseGeneMap);
		
		LOrderDiseaseNetworkCreator ldnCreator = new LOrderDiseaseNetworkCreator(ppi, omimIdIndexedDiseaseGeneMap, 1);
		ldnCreator.create();
		Graph subPPI = ldnCreator.getResultPPI();
		
		String edgeFilename = "E:/2013�����о�/ʵ������/DiseasePrediction/all_disease_ppi_edges_1.txt";
		String nodeFilename = "E:/2013�����о�/ʵ������/DiseasePrediction/all_disease_ppi_nodes_1.txt";
		WriterUtil.write(edgeFilename, subPPI.edgesToString());
		WriterUtil.write(nodeFilename, subPPI.nodesToString());
	}
	
	/*
	 * ��ȡ��ѡ����-����2����֯���Ի���������
	 * */
	public static void createCandidateGene_2(){}
}
