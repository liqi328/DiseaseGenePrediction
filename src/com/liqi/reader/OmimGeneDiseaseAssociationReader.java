package com.liqi.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.liqi.model.Disease;
import com.liqi.model.Gene;


/* 专门读取OMIM数据库中morbidmap文件
 * morbidmap中的一行，表示基因与疾病的关系
 * 
 * For the file morbidmap, the fields are, in order:
1  - Disorder, <disorder MIM no.> (<phene mapping key>)
2  - Gene/locus symbols
3  - Gene/locus MIM no.
4  - cytogenetic location
 * */
public class OmimGeneDiseaseAssociationReader  extends AbstractModelReader {
	/*String:omimId*/
	private Map<String, Gene> diseaseGeneMap;
	/*String:diseaseName*/
	private Map<String, Disease> diseaseMap;
	
	
	public OmimGeneDiseaseAssociationReader(String filename) {
		super(filename);
		diseaseMap = new HashMap<String, Disease>();
		diseaseGeneMap = new HashMap<String, Gene>();
	}
	
	public Map<String, Gene> getOmimDiseaseGeneMap(){
		return this.diseaseGeneMap;
	}
	
	public Map<String, Disease> getOmimDiseaseMap(){
		return this.diseaseMap;
	}


	@Override
	public void read() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(super.filename)));
			String line = null;
			Gene gene = null;
			Disease d = null;
			while((line = in.readLine()) != null){
				gene = createOneGene(line);
				d = createOneDisease(line);
				d.addGene(gene);				
				gene.addDisease(d);
			}
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		
	private Gene createOneGene(String line){
		String[] values = line.split("\\|");
		Gene gene = null;
		
		if(diseaseGeneMap.containsKey(values[FileFormat.OMIM_ID_COLUMN])){
			gene = diseaseGeneMap.get(values[FileFormat.OMIM_ID_COLUMN]);
		}else{
			gene = new Gene();
			gene.setSymbols(values[FileFormat.GENE_SYMBOL_COLUMN]);
			gene.setOmimId(values[FileFormat.OMIM_ID_COLUMN]);
			diseaseGeneMap.put(gene.getOmimId(), gene);
		}
	
		return gene;
	}
	
	private Disease createOneDisease(String line){
		String[] values = line.split("\\|");
		Disease d = null;
		
		int index = FileFormat.DISEASE_NAME_COLUMN;	//疾病名称，对应的列号
		
		if(diseaseMap.containsKey(values[index])){
			d = diseaseMap.get(values[index]);
		}else{
			d = new Disease();
			d.setName(values[index]);
			diseaseMap.put(d.getName(), d);
		}
		return d;
	}
	
	
	private interface FileFormat{
		public static final int DISEASE_NAME_COLUMN = 0;	//疾病名称，对应的列号
		public static final int GENE_SYMBOL_COLUMN  = 1;  	//基因Symbol, 对应的列号
		public static final int OMIM_ID_COLUMN 		= 2;	//基因OmimId, 对应的列号
	}
	
	
	public static void main(String[] args){
		OmimGeneDiseaseAssociationReader reader = new OmimGeneDiseaseAssociationReader(
				"E:/2013疾病研究/疾病数据/OMIM/morbidmap");
		reader.read();
		Map<String, Gene> diseaseGeneMap = reader.getOmimDiseaseGeneMap();
		System.out.println("size:" + diseaseGeneMap.size());
		String line = "17,20-lyase deficiency, isolated, 202110 (3)|CYP17A1, CYP17, P450C17|609300|10q24.32";
		System.out.println(line.split("\\|").length);
	}


}
