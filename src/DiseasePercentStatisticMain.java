import java.io.IOException;

import jxl.JXLException;

import com.liqi.jxl.DiseasePercent2Excel;


public class DiseasePercentStatisticMain {

	public static void main(String[] args){
		String dir = "E:/2013�����о�/ʵ������/�������Լ���/result2/TSPPIN_result_txt";
		DiseasePercent2Excel reader = new DiseasePercent2Excel();
		try {
			reader.read(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JXLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
