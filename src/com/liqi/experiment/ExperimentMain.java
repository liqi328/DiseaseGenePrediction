package com.liqi.experiment;

import com.liqi.centrality.alg.DC;
import com.liqi.proxy.ProxyFactory;
import com.liqi.util.WriterUtil;


public class ExperimentMain {
	
	private static InputArgument parseArgument(String[] args){
		InputArgument inputArg = new InputArgument();
		inputArg.setPpiFilename("E:/2013疾病研究/实验数据/神经退行性疾病/HPRD_ppi.txt");
		inputArg.setGeneDiseaseAssociationFilename("E:/2013疾病研究/实验数据/神经退行性疾病/gene_disease.txt");
		inputArg.setIdMappingFilename("E:/2013疾病研究/实验数据/神经退行性疾病/omim_hprd.txt");
		String outputDir = inputArg.getPpiFilename().substring(0, inputArg.getPpiFilename().lastIndexOf('/')) + "/output/";
		inputArg.setOutputDir(outputDir);
		
		return inputArg;
	}
	
	public static void main(String[] args){
		InputArgument inputArg = parseArgument(args);
		//Buffer buffer = new Buffer(inputArg);
		Buffer buffer = (Buffer)ProxyFactory.getProgramRuntimeProxyInstance(Buffer.class, 
				new Class[]{InputArgument.class}, new Object[]{inputArg});
		buffer.initBuffer();
		
		Statistic sta = new NeighborStatistic(buffer);
		sta.run();
		WriterUtil.write(inputArg.getOutputDir() + "neighborStatistic.txt", sta.getStatisticResult());
		
		for(int i=0; i<=1;++i){
			//SubDiseaseNetwork sdn = new SubDiseaseNetwork(buffer, i);
			LOrderDiseaseNetworkCreator sdn = (LOrderDiseaseNetworkCreator) ProxyFactory.getProgramRuntimeProxyInstance(LOrderDiseaseNetworkCreator.class,
					new Class[]{Buffer.class, Integer.class}, new Object[]{buffer, i});
			sdn.create();
			sdn.printSubNetwork();
			
			DC dc = new DC();
			//DC dc = (DC)ProxyFactory.getProgramRuntimeProxyInstance(DC.class, null, null);
			dc.run(sdn.getResultPPI());
			String dcFile = inputArg.getOutputDir() + "sub_disease_ppi_" + i +"_dc.txt";
			WriterUtil.write(dcFile, dc.getReuslt());
		}		
	}
}
