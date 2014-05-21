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

import ovplot.OVPlotComponent;
import ovplot.OVValuePlot;
import core.module.BaseModule;
import core.support.EnumManager;

/**
 * 
 * @author martino
 */
public class Base extends BaseModule {

	public Base() {
		super("OVPlot", "0.1");
		EnumManager.addEnum(OVValuePlot.PlotType.class);
	}

	@Override
	public ArrayList<JMenu> getNodeMenus() {
		ArrayList<JMenu> menus = new ArrayList<>();
		return menus;
	}

	@Override
	public ArrayList<JMenu> getGuiMenus() {
		ArrayList<JMenu> menus = new ArrayList<>();
		return menus;
	}

	@Override
	public HashMap<String, Class<? extends OVComponent>> getComponents() {
		HashMap<String, Class<? extends OVComponent>> classes = new HashMap<>();
		classes.put(OVValuePlot.getKey(), OVValuePlot.class);
		classes.put(OVPlotComponent.getKey(), OVPlotComponent.class);
		return classes;
	}

}
