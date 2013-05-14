package com.liqi.experiment;

import java.util.Map;

import com.liqi.centrality.alg.DC;
import com.liqi.graph.Graph;
import com.liqi.networkcreator.LOrderDiseaseNetworkCreator;
import com.liqi.proxy.ProxyFactory;
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
	
	public static void main1(String[] args){
		InputArgument inputArg = parseArgument(args);
		//ExperimentDataBuffer buffer = new ExperimentDataBuffer(inputArg);
		ExperimentDataBuffer buffer = (ExperimentDataBuffer)ProxyFactory.getProgramRuntimeProxyInstance(ExperimentDataBuffer.class, 
				new Class[]{InputArgument.class}, new Object[]{inputArg});
		
		Statistic sta = new NeighborStatistic(buffer);
		sta.run();
		WriterUtil.write(inputArg.getOutputDir() + "neighborStatistic.txt", sta.getStatisticResult());
		
		for(int i=0; i<=1;++i){
			LOrderDiseaseNetworkCreator sdn = (LOrderDiseaseNetworkCreator) ProxyFactory.getProgramRuntimeProxyInstance(LOrderDiseaseNetworkCreator.class,
					new Class[]{Graph.class, Map.class, Integer.class}, new Object[]{buffer.getPpi(), buffer.getDiseaseGeneMap(), i});
			sdn.create();
			String outFilename = inputArg.getOutputDir() + "sub_disease_ppi_"+ i + ".txt";
			WriterUtil.write(outFilename, sdn.getResultPPI().toString());
			//sdn.printSubNetwork();
			
			DC dc = new DC();
			//DC dc = (DC)ProxyFactory.getProgramRuntimeProxyInstance(DC.class, null, null);
			dc.run(sdn.getResultPPI());
			String dcFile = inputArg.getOutputDir() + "sub_disease_ppi_" + i +"_dc.txt";
			WriterUtil.write(dcFile, dc.getReuslt());
		}		
	}
	
	
	public static void main(String[] args){
		main1(args);
	}
}
