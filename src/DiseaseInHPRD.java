import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;
import com.liqi.jxl.Centrality2Excel;
import com.liqi.reader.CentralityReader;
import com.liqi.statistic.DiseaseSpecificStatistic;


/*
 * 统计全网HPRD ppi网络中，神经退行性疾病的中心性
 * */
public class DiseaseInHPRD {
	public static void main(String[] args){
		String filename = "E:/2013疾病研究/实验数据/神经退行性疾病/result2/HPRD_centrality.txt";
		CentralityReader reader = new CentralityReader(filename);
		reader.read();
		Map<String, Centrality2> centralityMap = reader.getCentralityMap(); 
		
		Map<String, Map<String, Map<String, Double>>> allDiseaseSpecificAvgMap = 
				new HashMap<String, Map<String, Map<String, Double>>>();
		allDiseaseSpecificAvgMap.put("HPRD", getDiseaseSpecificAvgMap(centralityMap));
		
		File excelFile = new File(filename + ".xls");
		try {
			Centrality2Excel excelWriter = new Centrality2Excel(excelFile);
			excelWriter.writeDiseaseSpecificCentralityAverage(allDiseaseSpecificAvgMap);
			excelWriter.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Map<String, Map<String, Double>> getDiseaseSpecificAvgMap(Map<String, Centrality2> centralityMap){
		 /* 获取统计信息*/       
		DiseaseSpecificStatistic casta = new DiseaseSpecificStatistic(centralityMap);
		casta.run();
		return casta.getStatisticResultMap();
	}
}
