/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ovplot.module;

import gui.components.OVComponent;

import java.util.ArrayList;
import java.util.HashMap;

import ovplot.OVPlotComponent;
import ovplot.OVValuePlot;
import core.module.BaseModule;

/**
 * 
 * @author martino
 */
public class Base extends BaseModule {

	public Base() {
		super("OVPlot", "0.1");
	}

	@Override
	public ArrayList<Class<? extends Enum<?>>> getEnums() {
		ArrayList<Class<? extends Enum<?>>> list = super.getEnums();
		list.add(OVValuePlot.PlotType.class);
		return list;
	}

	@Override
	public HashMap<String, Class<? extends OVComponent>> getComponents() {
		HashMap<String, Class<? extends OVComponent>> classes = new HashMap<>();
		classes.put(OVValuePlot.getKey(), OVValuePlot.class);
		classes.put(OVPlotComponent.getKey(), OVPlotComponent.class);
		return classes;
	}

}
