import java.util.Map;

import com.liqi.geneutil.GeneIdMappingUtil;
import com.liqi.graph.Graph;
import com.liqi.model.Gene;
import com.liqi.networkcreator.LOrderDiseaseNetworkCreator;
import com.liqi.networkcreator.LOrderDiseaseNetworkCreator2;
import com.liqi.networkcreator.NormalGeneNetworkCreator;
import com.liqi.reader.ReaderFacade;
import com.liqi.util.WriterUtil;


/*
 * ������������
 * */
public class CreateDiseaseSubNetworkMain {
	
	private static Graph create(String ppiFilename, String diseaseGeneFilename){
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		Map<String, Gene> omimIdIndexedDiseaseGeneMap = ReaderFacade.getInstance().getOmimDiseaseGeneMap(diseaseGeneFilename);
		GeneIdMappingUtil.setOtherGeneId(omimIdIndexedDiseaseGeneMap);
		
		LOrderDiseaseNetworkCreator ldnCreator = new LOrderDiseaseNetworkCreator(ppi, omimIdIndexedDiseaseGeneMap, 0);
		ldnCreator.create();
		return ldnCreator.getResultPPI();
	}
	
	private static Graph create_LQ(String ppiFilename, String diseaseGeneFilename){
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		Map<String, Gene> omimIdIndexedDiseaseGeneMap = ReaderFacade.getInstance().getOmimDiseaseGeneMap(diseaseGeneFilename);
		
		LOrderDiseaseNetworkCreator ldnCreator = new LOrderDiseaseNetworkCreator2(ppi, omimIdIndexedDiseaseGeneMap, 0);
		ldnCreator.create();
		return ldnCreator.getResultPPI();
	}
	
	
	
	/* ����ȫ�������� �������� */
	private static Graph createAllDiseaseSubNetwork(){
		System.out.println("����ȫ�������ļ�������...");
		String ppiFilename = "E:/2013�����о�/ʵ������/�������Լ���/HPRD_ppi.txt";
		String diseaseGeneFilename = "E:/2013�����о�/��������/OMIM/morbidmap";	
		
		Graph subPPI = create(ppiFilename, diseaseGeneFilename);
			
		String edgeFilename = "E:/2013�����о�/ʵ������/�������Լ���/output/all_disease_ppi_edges.txt";
		String nodeFilename = "E:/2013�����о�/ʵ������/�������Լ���/output/all_disease_ppi_nodes.txt";
		WriterUtil.write(edgeFilename, subPPI.edgesToString());
		WriterUtil.write(nodeFilename, subPPI.nodesToString());
		return subPPI;
	}
	
	/* �����ض������ļ������� */
	private static Graph createDiseaseSpecificDiseaseSubNetwork(){
		System.out.println("�����ض������ļ�������...");
		String ppiFilename = "E:/2013�����о�/ʵ������/�������Լ���/HPRD_ppi.txt";
		String diseaseGeneFilename = "E:/2013�����о�/ʵ������/�������Լ���/omim_neurodegenerative_disease_gene.txt";
		
		Graph subPPI = create(ppiFilename, diseaseGeneFilename);
		
		String outFilename = "E:/2013�����о�/ʵ������/�������Լ���/output/neurodegenerative_disease_ppi_edges.txt";
		WriterUtil.write(outFilename, subPPI.edgesToString());
		return subPPI;
	}
	
	private static Graph createNormalGeneSubNetwork(Graph sourcePPI, Graph subPPI){
		NormalGeneNetworkCreator creator = new NormalGeneNetworkCreator(sourcePPI, subPPI);
		creator.create();
		Graph resultPPI = creator.getResultPPI();
		
		String edgeFilename = "E:/2013�����о�/ʵ������/�������Լ���/output/normal_gene_ppi_edges.txt";
		String nodeFilename = "E:/2013�����о�/ʵ������/�������Լ���/output/normal_gene_ppi_nodes.txt";
		WriterUtil.write(edgeFilename, resultPPI.edgesToString());
		WriterUtil.write(nodeFilename, resultPPI.nodesToString());

		return resultPPI;
	}
	
	private static Graph createLQAllDiseaseSubNetwork(){
		System.out.println("����ȫ�������ļ�������...");
		String ppiFilename = "E:/2013�����о�/ʵ������/topologyAnalysis/HPIN_NoRepeat_Symbol.txt";
		String diseaseGeneFilename = "E:/2013�����о�/��������/OMIM/morbidmap";	
		
		Graph subPPI = create_LQ(ppiFilename, diseaseGeneFilename);
			
		String edgeFilename = "E:/2013�����о�/ʵ������/topologyAnalysis/output/all_disease_ppi_edges.txt";
		String nodeFilename = "E:/2013�����о�/ʵ������/topologyAnalysis/output/all_disease_ppi_nodes.txt";
		WriterUtil.write(edgeFilename, subPPI.edgesToString());
		WriterUtil.write(nodeFilename, subPPI.nodesToString());
		return subPPI;
	}
	
	private static Graph createLQNormalGeneSubNetwork(Graph sourcePPI, Graph subPPI){
		NormalGeneNetworkCreator creator = new NormalGeneNetworkCreator(sourcePPI, subPPI);
		creator.create();
		Graph resultPPI = creator.getResultPPI();
		
		String edgeFilename = "E:/2013�����о�/ʵ������/topologyAnalysis/output/normal_gene_ppi_edges.txt";
		String nodeFilename = "E:/2013�����о�/ʵ������/topologyAnalysis/output/normal_gene_ppi_nodes.txt";
		WriterUtil.write(edgeFilename, resultPPI.edgesToString());
		WriterUtil.write(nodeFilename, resultPPI.nodesToString());

		return resultPPI;
	}
	
	private static void createHPRDNetwork(){
		Graph allDiseaseSubPPI = createAllDiseaseSubNetwork();
		//createDiseaseSpecificDiseaseSubNetwork();
		
		String ppiFilename = "E:/2013�����о�/ʵ������/�������Լ���/HPRD_ppi.txt";
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		createNormalGeneSubNetwork(ppi, allDiseaseSubPPI);
	}
	
	private static void createLQNetwork(){
		Graph allDiseaseSubPPI = createLQAllDiseaseSubNetwork();
		
		String ppiFilename = "E:/2013�����о�/ʵ������/topologyAnalysis/HPIN_NoRepeat_Symbol.txt";
		Graph ppi = ReaderFacade.getInstance().getPPI(ppiFilename);
		createLQNormalGeneSubNetwork(ppi, allDiseaseSubPPI);
	}
	
	public static void main(String[] args){
		//createHPRDNetwork();
		createLQNetwork();
	}
}
