/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ovplot.module;

import gui.components.OVComponent;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ovplot.OVArrayPlot;
import ovplot.OVPlotComponent;
import ovplot.OVValuePlot;
import ovplot.PlotType;
import core.module.BaseModule;

/**
 * 
 * @author martino
 */
public class Base extends BaseModule {

	public Base() {
		super("OVPlot", "0.1","Martino Ferrari <manda.mgf@gmail.com>");
	}

	@Override
	public ArrayList<Class<? extends Enum<?>>> getEnums() {
		ArrayList<Class<? extends Enum<?>>> list = super.getEnums();
		list.add(PlotType.class);
		return list;
	}

	@Override
	public ArrayList<JMenu> getGuiMenus() {
		ArrayList<JMenu> list = super.getGuiMenus();
		JMenu m = new JMenu("OVPlot");
		JMenuItem i = new JMenuItem(OVPlotComponent.getKey());
		i.setActionCommand(i.getText());
		m.add(i);
		list.add(m);
		return list;
	}

	@Override
	public HashMap<String, Class<? extends OVComponent>> getComponents() {
		HashMap<String, Class<? extends OVComponent>> classes = new HashMap<>();
		classes.put(OVValuePlot.getKey(), OVValuePlot.class);
		classes.put(OVArrayPlot.getKey(), OVArrayPlot.class);
		classes.put(OVPlotComponent.getKey(), OVPlotComponent.class);
		return classes;
	}

}
