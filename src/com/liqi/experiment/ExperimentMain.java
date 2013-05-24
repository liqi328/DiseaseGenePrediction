package com.liqi.experiment;

import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Graph;
import com.liqi.networkcreator.LOrderDiseaseNetworkCreator;
import com.liqi.networkcreator.TissueSpecificPPINetworkCreator;
import com.liqi.proxy.ProxyFactory;
import com.liqi.statistic.NeighborStatistic;
import com.liqi.statistic.Statistic;
import com.liqi.util.WriterUtil;


public class ExperimentMain {
	
	private static InputArgument parseArgument(String[] args){
		InputArgument inputArg = new InputArgument();
		inputArg.setPpiFilename("E:/2013疾病研究/实验数据/神经退行性疾病/HPRD_ppi.txt");
		inputArg.setGeneDiseaseAssociationFilename("E:/2013疾病研究/实验数据/神经退行性疾病/gene_disease.txt");
		inputArg.setIdMappingFilename("E:/2013疾病研究/疾病数据/HumanPPI/HPRD_Release9_062910/FLAT_FILES_072010/HPRD_ID_MAPPINGS.txt");
		inputArg.setTissueSpecificGeneExpressionFilename(
				"E:/2013疾病研究/疾病数据/TissueS_geneExpression/GDS596_full.soft");
		String outputDir = inputArg.getPpiFilename().substring(0, inputArg.getPpiFilename().lastIndexOf('/')) + "/output/";
		inputArg.setOutputDir(outputDir);
		
		return inputArg;
	}
	
	public static void createLOrderSubnetwork(InputArgument inputArg, ExperimentDataBuffer buffer){
		for(int i=0; i<=2;++i){
			LOrderDiseaseNetworkCreator ldnCreator = (LOrderDiseaseNetworkCreator) ProxyFactory.getProgramRuntimeProxyInstance(LOrderDiseaseNetworkCreator.class,
					new Class[]{Graph.class, Map.class, Integer.class}, new Object[]{buffer.getPpi(), buffer.getDiseaseGeneMap(), i});
			ldnCreator.create();
			Graph subPPI = ldnCreator.getResultPPI();
			String outFilename = inputArg.getOutputDir()+ "DPIN/" + "sub_disease_ppi_"+ i + ".txt";
			WriterUtil.write(outFilename, subPPI.edgesToString());
			//sdn.printSubNetwork();
			
//			DC dc = new DC();
//			//DC dc = (DC)ProxyFactory.getProgramRuntimeProxyInstance(DC.class, null, null);
//			dc.run(subPPI);
//			String dcFile = inputArg.getOutputDir() + "sub_disease_ppi_" + i +"_dc.txt";
//			WriterUtil.write(dcFile, dc.getReuslt());
		}	
	}
	
	public static Map<String, Graph> createTissueSpecificPPInetwork(InputArgument inputArg, ExperimentDataBuffer buffer){
//		TissueSpecificPPINetworkCreator tsnCreator = new TissueSpecificPPINetworkCreator(buffer.getPpi(),
//				buffer.getTissueMap(), buffer.getTissueSpecificGeneExpressionMap(),
//				buffer.getHprdIdMappingMap());
		TissueSpecificPPINetworkCreator tsnCreator = (TissueSpecificPPINetworkCreator) ProxyFactory.getProgramRuntimeProxyInstance(
				TissueSpecificPPINetworkCreator.class, 
				new Class[]{Graph.class, Map.class, Map.class, Map.class}, 
				new Object[]{buffer.getPpi(), buffer.getTissueMap(), 
					buffer.getTissueSpecificGeneExpressionMap(),
					buffer.getHprdIdMappingMap()});
		tsnCreator.create();
		Map<String, Graph> ppiMap = tsnCreator.getResultPPIMap();
		Iterator<Map.Entry<String, Graph>> itr = ppiMap.entrySet().iterator();
		Map.Entry<String, Graph> entry = null;
		while(itr.hasNext()){
			entry = itr.next();
			String filename = inputArg.getOutputDir() + "TSPPIN/" 
			+ entry.getKey() + ".txt";
			WriterUtil.write(filename, entry.getValue().edgesToString());
		}
		
		return ppiMap;
	}
	
	public static void createDiseaseTSPIN(InputArgument inputArg, ExperimentDataBuffer buffer,
			Map<String, Graph> tsPPIMap, int order){
		int lOrder = order;
		Iterator<Map.Entry<String, Graph>> itrIterator = tsPPIMap.entrySet().iterator();
		Map.Entry<String, Graph> entry = null;
		while(itrIterator.hasNext()){
			entry = itrIterator.next();
			LOrderDiseaseNetworkCreator ldnCreator = new LOrderDiseaseNetworkCreator(
					entry.getValue(), buffer.getDiseaseGeneMap(), lOrder);
			ldnCreator.create();
			Graph diseaseTSPPI = ldnCreator.getResultPPI();
			
			String outFilename = inputArg.getOutputDir() + "DTSPPIN_"+ lOrder 
					+ "/" + entry.getKey() +"_"+ lOrder + ".txt";
			//WriterUtil.write(outFilename, diseaseTSPPI.toString());
			WriterUtil.write(outFilename, diseaseTSPPI.edgesToString());
		}
	}
	
	public static void main1(String[] args){
		InputArgument inputArg = parseArgument(args);
		ExperimentDataBuffer buffer = new ExperimentDataBuffer(inputArg);
//		ExperimentDataBuffer buffer = (ExperimentDataBuffer)ProxyFactory.getProgramRuntimeProxyInstance(ExperimentDataBuffer.class, 
//				new Class[]{InputArgument.class}, new Object[]{inputArg});
		
		Statistic sta = new NeighborStatistic(buffer);
		sta.run();
		WriterUtil.write(inputArg.getOutputDir() + "neighborStatistic.txt", sta.getStatisticResult());
		
		createLOrderSubnetwork(inputArg, buffer);
		
		Map<String, Graph> tsPPIMap = createTissueSpecificPPInetwork(inputArg, buffer);
		
		createDiseaseTSPIN(inputArg, buffer, tsPPIMap, 1);
		createDiseaseTSPIN(inputArg, buffer, tsPPIMap, 2);
	}
	
	
	public static void main(String[] args){
		main1(args);
	}
}
