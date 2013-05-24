package com.liqi.statistic;

import java.util.ArrayList;
import java.util.List;

import com.liqi.experiment.ExperimentDataBuffer;

public abstract class AbstractStatistic implements Statistic {
	protected ExperimentDataBuffer dataBuffer;
	
	protected List<StatisticData> sdList;
	
	public AbstractStatistic(){}
	
	public AbstractStatistic(ExperimentDataBuffer dataBuf){
		this.dataBuffer = dataBuf;
		sdList = new ArrayList<StatisticData>();
	}
}
