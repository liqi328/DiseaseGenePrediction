package com.liqi.centrality.model;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class Centrality implements Comparable<Centrality>{
	public static final String[] HEADER = {"BC", "CC", "DC", "EC", "IC", "SC", "SoECC", "BN", "MNC", "DMNC", "EPC"}; 
	public static final Map<String, Integer> centralityType = new HashMap<String, Integer>();
	
	static{
		initCentralityType();
	}
	
	private static void initCentralityType(){		
		for(int i=0; i< HEADER.length; ++i){
			centralityType.put(HEADER[i], i);
		}
	}
	
	public Centrality(){
		centralities = new double[centralityType.size()];
	}
	
	/*默认按 疾病数目 比较- 降序*/
	@Override
	public int compareTo(Centrality anthorCty){
//		if(!(anthorCty instanceof Centrality)){
//			throw new ClassCastException("A Centrality object expected.");
//		}
		int number = ((Centrality)anthorCty).getNumberOfDisease();
		return number - this.getNumberOfDisease();
	}
	
	public String getHprdId() {
		return hprdId;
	}
	
	public void setHprdId(String hprdId) {
		this.hprdId = hprdId;
	}
	
	public int getNumberOfDisease() {
		return numberOfDisease;
	}
	
	public void setNumberOfDisease(int numberOfDisease) {
		this.numberOfDisease = numberOfDisease;
	}
	
	public double getBC(){
		return centralities[centralityType.get("BC")];
	}
	
	public void setBC(double bc){
		this.centralities[centralityType.get("BC")] = bc;
	}
	
	public double getCC(){
		return centralities[centralityType.get("CC")];
	}
	
	public void setCC(double cc){
		this.centralities[centralityType.get("CC")] = cc;
	}
	
	public double getDC(){
		return centralities[centralityType.get("DC")];
	}
	
	public void setDC(double dc){
		this.centralities[centralityType.get("DC")] = dc;
	}
	
	public double getEC(){
		return centralities[centralityType.get("EC")];
	}
	
	public void setEC(double ec){
		this.centralities[centralityType.get("EC")] = ec;
	}
	
	public double getIC(){
		return centralities[centralityType.get("IC")];
	}
	
	public void setIC(double ic){
		this.centralities[centralityType.get("IC")] = ic;
	}
	
	public double getSC(){
		return centralities[centralityType.get("SC")];
	}
	
	public void setSC(double sc){
		this.centralities[centralityType.get("SC")] = sc;
	}
	
	public double getSoECC(){
		return centralities[centralityType.get("SoECC")];
	}
	
	public void setSoECC(double soecc){
		this.centralities[centralityType.get("SoECC")] = soecc;
	}
	
	public double getBN(){
		return centralities[centralityType.get("BN")];
	}
	
	public void setBN(double bn){
		this.centralities[centralityType.get("BN")] = bn;
	}
	
	public double getMNC(){
		return centralities[centralityType.get("MNC")];
	}
	
	public void setMNC(double mnc){
		this.centralities[centralityType.get("MNC")] = mnc;
	}
	
	public double getDMNC(){
		return centralities[centralityType.get("DMNC")];
	}
	
	public void setDMNC(double dmnc){
		this.centralities[centralityType.get("DMNC")] = dmnc;
	}
	
	public double getEPC(){
		return centralities[centralityType.get("EPC")];
	}
	
	public void setEPC(double epc){
		this.centralities[centralityType.get("EPC")] = epc;
	}

	private String hprdId;
	private int numberOfDisease;
	private double[] centralities;
	
	
	/* 降序-排序 ,以内部类的方式实现Centrality的比较器*/
	public class CentralityComparator implements Comparator<Centrality>{
		public CentralityComparator(String key) throws NullPointerException{
			/* 此处应该确保 key 合法*/
			this.sortIndex = centralityType.get(key);
		}

		@Override
		public int compare(Centrality cty, Centrality anthorCty){
//			if(!(cty instanceof Centrality) || !(anthorCty instanceof Centrality)){
//				throw new ClassCastException("A Centrality object expected.");
//			} 
			
			return new Double(anthorCty.centralities[sortIndex]).compareTo(cty.centralities[sortIndex]);
		}
		
		private int sortIndex;
	}
	
	public class CentralityByIdComparator implements Comparator<Centrality>{
		@Override
		public int compare(Centrality cty, Centrality anthorCty) {
			return cty.getHprdId().compareTo(anthorCty.getHprdId());
		}
	}
}



