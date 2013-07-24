import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.liqi.centrality.model.Centrality2;
import com.liqi.jxl.Centrality2Excel;
import com.liqi.reader.CentralityReader;
import com.liqi.statistic.DiseaseSpecificStatistic;


/*
 * ͳ��ȫ��HPRD ppi�����У��������Լ�����������
 * */
public class DiseaseInHPRD {
	public static void main(String[] args){
		String filename = "E:/2013�����о�/ʵ������/�������Լ���/result2/HPRD_centrality.txt";
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
		 /* ��ȡͳ����Ϣ*/       
		DiseaseSpecificStatistic casta = new DiseaseSpecificStatistic(centralityMap);
		casta.run();
		return casta.getStatisticResultMap();
	}
}
