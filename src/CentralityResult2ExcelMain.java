import com.liqi.jxl.CentralityResult2Excel;


public class CentralityResult2ExcelMain {
	public static void main(String[] args){
		String[] dirs = {
				//"E:/2013�����о�/ʵ������/�������Լ���/result2/DPIN_result",
				"E:/2013�����о�/ʵ������/�������Լ���/result2/TSPPIN_result",
				//"E:/2013�����о�/ʵ������/topologyAnalysis/result/TSPPIN_result",
				//"E:/2013�����о�/ʵ������/topologyAnalysis/result/PPIN_result",
				};
		try{
			for(String dir : dirs){
				CentralityResult2Excel reader = new CentralityResult2Excel();
				reader.read(dir);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
