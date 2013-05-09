package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.model.Tissue;
import com.liqi.model.TissueSpecificGeneExpression;

/* ���ݿ⣺GEO, http://www.ncbi.nlm.nih.gov/geo
 * ��֯�����Ի���������
 * */
public class TissuSpecificGeneExpressionReader extends ModelReader{
	/* ��֯����-��֯*/
	private Map<String, Tissue> tissueMap;
	/*entrezGeneid-gene�������*/
	private Map<String, TissueSpecificGeneExpression> tsGeneExpressionMap;
	
	public TissuSpecificGeneExpressionReader(String filename){
		super(filename);
		tissueMap = new HashMap<String, Tissue>();
		tsGeneExpressionMap = new HashMap<String, TissueSpecificGeneExpression>();
	}
	
	public Map<String, Tissue> getTissueMap(){
		return this.tissueMap;
	}
	
	public Map<String, TissueSpecificGeneExpression> getTsGeneExpressionMap(){
		return this.tsGeneExpressionMap;
	}
	
	public void read(){
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			String line = null;
			while((line = in.readLine()) != null){
				if(line.startsWith("^SUBSET")){
					readTissue(in);
				}else if(line.startsWith("!dataset_table_begin")){
					readGeneExpressionData(in);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readTissue(BufferedReader in) throws IOException{
		Tissue ts = new Tissue();
		String line = in.readLine();//read !subset_dataset_id
		line = in.readLine();		//read !subset_sample_id 
		
		ts.setTissueName(line.split("=")[1].trim());
		
		line = in.readLine();		//read !subset_sample_id
		
		String[] sampleIds = line.split("=")[1].split(",");
		ts.setFirstSampleId(sampleIds[0].trim());
		ts.setSecondSampleId(sampleIds[1].trim());	
		
		tissueMap.put(ts.getTissueName(), ts);
	}
	
	private void readGeneExpressionData(BufferedReader in) throws IOException{
		String line = in.readLine();	
		String[] header = line.split("\t");		
		
		TissueSpecificGeneExpression tsGeneExpression = null;
		while((line = in.readLine()) != null){
			if(line.indexOf("--Control") >= 0){
				continue;
			}
			if(line.startsWith("!dataset_table_end")){
				break;
			}
			tsGeneExpression = createTissueSpecificGeneExpression(line, header);
						
			addOneTissueSpecificGeneExpression(tsGeneExpression);				
		}
	}
	
	private TissueSpecificGeneExpression createTissueSpecificGeneExpression(String line, String[] header){
		TissueSpecificGeneExpression tsGeneExpression = new TissueSpecificGeneExpression();
		String[] values = line.split("\t");
		int i = 1;
		tsGeneExpression.setIdentifier(values[i++]);
		
		for(; i< 79 * 2 + 2; ++i){
			tsGeneExpression.setExpressionValue(header[i], 
					Double.parseDouble(values[i]));
		}
		tsGeneExpression.setGeneSymbol(values[++i]);
		tsGeneExpression.setEntrezId(values[++i]);
		
		return tsGeneExpression;		
	}
	
	private void addOneTissueSpecificGeneExpression(TissueSpecificGeneExpression tsge){		
		/* ȥ������IDΪ�յ�*/
		if(tsge.getEntrezId().equals("")){
			return;
		}
		
		/*һ����������ж��� �������
		 * ȡƽ��ֵ
		 * */
		TissueSpecificGeneExpression tmp = tsGeneExpressionMap.get(tsge.getEntrezId());
		if(tmp != null){		
			//ȡƽ��ֵ
			NumberFormat nf = new DecimalFormat("#.00");
			
			Map.Entry<String, Double> entry = null;
			Iterator<Map.Entry<String, Double>> itr = tmp.createExpressionValueIterator();
			while(itr.hasNext()){
				entry = itr.next();
				tsge.setExpressionValue(entry.getKey(), Double.parseDouble(nf.format((entry.getValue() +
						tsge.getExpressionValue(entry.getKey())) / 2.0)));
			}
		}	
		
		tsGeneExpressionMap.put(tsge.getEntrezId(), tsge);
	}
}
