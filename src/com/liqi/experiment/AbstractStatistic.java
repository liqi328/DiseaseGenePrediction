package com.liqi.experiment;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStatistic implements Statistic {
	protected ExperimentDataBuffer dataBuffer;
	
	protected List<StatisticData> sdList;
	
	public AbstractStatistic(ExperimentDataBuffer dataBuf){
		this.dataBuffer = dataBuf;
		sdList = new ArrayList<StatisticData>();
	}
}
