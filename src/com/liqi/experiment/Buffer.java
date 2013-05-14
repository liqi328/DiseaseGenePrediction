package com.liqi.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.liqi.graph.Graph;
import com.liqi.model.Disease;
import com.liqi.model.Gene;
import com.liqi.reader.PPIReader;
import com.liqi.util.WriterUtil;

/* 已废弃，被ExperimentDataBuffer.java代替*/
@Deprecated
public class Buffer {
	private Graph ppi;
	
	private InputArgument inputArg;
	
	/*OmimId,gene*/
	private Map<String, Gene> omimGene;
	
	private Map<String, Gene> hprdGene;
	private Map<String, Disease> diseases;
	
	public Buffer(InputArgument inputArg){
		omimGene = new HashMap<String, Gene>();
		hprdGene = new HashMap<String, Gene>();
		diseases = new HashMap<String, Disease>();
		this.inputArg = inputArg;
	}
	
	public void initBuffer(){
		readPPI();
		readGeneOmim();
		readDiseaseGene();		
	}
	
	public void readPPI(){
		String inFilename = inputArg.getPpiFilename();
		//ppi = PPIView.readPPI(inFilename);
		PPIReader reader = new PPIReader(inFilename);
		reader.read();
		ppi = reader.getPPI();
	}
	
	public void readDiseaseGene(){
		String inFilename = inputArg.getGeneDiseaseAssociationFilename();	
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(inFilename)));
			String line = in.readLine();
			String[] cols = null;
			Gene gene = null;
			while((line = in.readLine()) != null){
				cols = line.split("\t");
				gene = omimGene.get(cols[1]);
				if(null == gene)continue;
				gene.setSymbols(cols[0]);
				Disease d = diseases.get(cols[2]);
				if(d == null){
					d = new Disease();
					d.setName(cols[2]);
				}
				
				d.addGene(gene);
				
				gene.addDisease(d);
				diseases.put(cols[2], d);
			}
			
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readGeneOmim(){
		String inFilename = inputArg.getIdMappingFilename();		
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(inFilename)));
			String line = in.readLine();
			String[] cols = null;
			Gene gene = null;
			while((line = in.readLine()) != null){
				cols = line.split("\t");
				gene = new Gene();
				gene.setOmimId(cols[0]);
				gene.setHprdId(cols[1]);
				
				omimGene.put(cols[0], gene);
				hprdGene.put(cols[1], gene);
			}
			
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void printDiseaseGene(){
		StringBuffer buf = new StringBuffer();
		buf.append("Gene number = ").append(omimGene.size()).append("\n");
		buf.append("Hprd_Id\tGene_Omim_Id\tGene_Symbols\tDisorders\n");
		Gene gene = null;
		Iterator<Map.Entry<String, Gene>> itr = omimGene.entrySet().iterator();
		while(itr.hasNext()){
			gene = itr.next().getValue();
			buf.append(gene.getHprdId()).append("\t");
			buf.append(gene.getOmimId()).append("\t");
			buf.append(gene.getSymbols()).append("\t");
			buf.append(gene.getDiseaseSet().size()).append("\n\t\t\t");
			
			Iterator<Disease> dItr = gene.getDiseaseSet().iterator();
			while(dItr.hasNext()){
				buf.append(dItr.next().getName()).append("\n\t\t\t");
			}
			buf.append("\n");
		}
		String filename = "E:/2013疾病研究/实验数据/神经退行性疾病/gene.txt";
		WriterUtil.write(filename, buf.toString());
	}
	
	public void printDisease(){
		StringBuffer buf = new StringBuffer();
		buf.append("Disorder number = ").append(diseases.size()).append("\n");
		buf.append("Disease_Name\t Gene\n");
		Disease d = null;
		Iterator<Map.Entry<String, Disease>> itr = diseases.entrySet().iterator();
		while(itr.hasNext()){
			d = itr.next().getValue();
			buf.append(d.getName()).append("\t");
			buf.append(d.getDiseaseGene().size()).append("\n\t");
			
			Iterator<Gene> dItr = d.getDiseaseGene().iterator();
			while(dItr.hasNext()){
				buf.append(dItr.next().getOmimId()).append("\n\t");
			}
			buf.append("\n");
		}
		String filename = "E:/2013疾病研究/实验数据/神经退行性疾病/disease.txt";
		WriterUtil.write(filename, buf.toString());
	}

	public Graph getPpi() {
		return ppi;
	}

	public Map<String, Gene> getOmimGene() {
		return omimGene;
	}

	public Map<String, Gene> getHprdGene() {
		return hprdGene;
	}

	public Map<String, Disease> getDiseases() {
		return diseases;
	}
	
	public String getOutputDirectory(){
		return this.inputArg.getOutputDir();
	}
}
