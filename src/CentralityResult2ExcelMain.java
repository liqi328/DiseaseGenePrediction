import com.liqi.jxl.CentralityResult2Excel;


public class CentralityResult2ExcelMain {
	public static void main(String[] args){
		String[] dirs = {
				//"E:/2013疾病研究/实验数据/神经退行性疾病/result2/DPIN_result",
				"E:/2013疾病研究/实验数据/神经退行性疾病/result2/TSPPIN_result",
				//"E:/2013疾病研究/实验数据/topologyAnalysis/result/TSPPIN_result",
				//"E:/2013疾病研究/实验数据/topologyAnalysis/result/PPIN_result",
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
