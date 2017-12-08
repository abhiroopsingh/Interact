package com.Interact.controllers;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/*
 * Created by Andy Sin on 2017.12.07  * 
 * Copyright © 2017 Andy Sin. All rights reserved. * 
 */
/**
 *
 * @author Andy
 */
@Named("chartView")
@SessionScoped
public class ChartView implements Serializable {

    @Inject
    private StatsController statsController;

    private BarChartModel barModel;

    @PostConstruct
    public void init() {
        createBarModels();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries results = new ChartSeries();
        results.setLabel("Answers");
        results.set("A", statsController.getAInt());
        results.set("B", statsController.getBInt());
        results.set("C", statsController.getCInt());
        results.set("D", statsController.getDInt());
        results.set("E", statsController.getEInt());

        model.addSeries(results);

        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Results for Question");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Results");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Percantage of Total");
        yAxis.setMin(0);
        yAxis.setMax(100);
    }
}
