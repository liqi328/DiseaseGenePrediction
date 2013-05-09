import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.liqi.centrality.alg.ClusteringCoefficient;
import com.liqi.graph.Graph;
import com.liqi.util.ProgramRunTimeCalculator;
import com.liqi.view.PPIView;


public class MainDriver {
	
	public static void write(String filename, String content){
		File file = new File(filename);
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(content);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void testReadPPI() {
		String inFilename = "E:/2013疾病研究/实验数据/PPI-6种/HPRD_ppi.txt";
		String outFilename = "E:/2013疾病研究/实验数据/PPI-6种/HPRD_tmp_ppi.txt";
		String ccFilename = "E:/2013疾病研究/实验数据/PPI-6种/clusterCoefficient.txt";
		ProgramRunTimeCalculator tc = new ProgramRunTimeCalculator();
		System.out.println("-------------Read PPI----------");
		tc.start();
		Graph ppi = PPIView.readPPI(inFilename);
		tc.stop();
		System.out.println(tc.report());
		System.out.println("-------------Write PPI----------");
		tc.reset();
		tc.start();
		//PPIView.writePPI(outFilename, ppi);
		tc.stop();
		System.out.println(tc.report());
		
		System.out.println("-------------Clustering Coefficent----------");
		tc.reset();
		tc.start();
		ClusteringCoefficient ccAlg = new ClusteringCoefficient();
		ccAlg.run(ppi);
		write(ccFilename, ccAlg.getReuslt());
		tc.stop();
		System.out.println(tc.report());
		
	}
	
//	public static void testReadPPI2() {
//		String inFilename = "E:/2013疾病研究/实验数据/PPI-6种/HPRD_ppi.txt";
//		String outFilename = "E:/2013疾病研究/实验数据/PPI-6种/HPRD_tmp_ppi.txt";
//		PPIView2 view = new PPIView2();
//		Graph<Node, DefaultEdge> ppi = PPIView2.readPPI(inFilename);
//		System.out.println("-------------print----------");
//		PPIView2.writePPI(outFilename, ppi);
//		
//	}
	
	public static void main(String[] args){
		testReadPPI();
	}
}
