package com.liqi.experiment;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStatistic implements Statistic {
	protected Buffer buffer;
	
	protected List<StatisticData> sdList;
	
	public AbstractStatistic(Buffer buffer){
		this.buffer = buffer;
		sdList = new ArrayList<StatisticData>();
	}
}
