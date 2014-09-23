package ovplot;

import gui.components.OVComponent;
import gui.components.OVComponentContainer;
import gui.components.nodes.OVLine;
import gui.constants.ComponentSettings;
import gui.enums.EditorMode;
import gui.interfaces.OVContainer;
import gui.support.Setting;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.math.plot.Plot2DPanel;
import org.w3c.dom.Element;

import core.Value;

public class OVPlotComponent extends OVComponentContainer {

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	private static final String xAxis = "X axis";
	private static final String yAxis = "Y axis";

	private Plot2DPanel plot_;
	private JMenu menu_;

	public OVPlotComponent(OVContainer father) {
		super(father);
		this.setLayout(new BorderLayout());
		getSetting(ComponentSettings.SizeW).setValue(300);
		getSetting(ComponentSettings.SizeH).setValue(300);
		getSetting(ComponentSettings.Name).setValue("PlotPanel");
		Setting s = new Setting(xAxis, "X", this);
		addSetting(ComponentSettings.SpecificCategory, s);
		s = new Setting(yAxis, "Y", this);
		addSetting(ComponentSettings.SpecificCategory, s);
		initPlot();
		initMenu();
	}

	public OVPlotComponent(Element e, OVContainer father) {
		super(e, father);
		this.setLayout(new BorderLayout());

		initPlot();
		initMenu();
		triggerSettings();
	}

	private void initPlot() {
		plot_ = new Plot2DPanel();
		plot_.setAxisLabel(0, "X");
		plot_.setAxisLabel(1, "Y");
		this.add(plot_, BorderLayout.CENTER);
	}

	@Override
	public void setMode(EditorMode mode) {

		if (mode != getMode()) {
			if (mode == EditorMode.GUI || mode.isExec()) {
				if (getMode() == EditorMode.NODE) {
					hideAll();
					this.setLayout(new BorderLayout());
					plot_.setBounds(5, 5, getWidth() - 20, getHeight() - 20);
					this.add(plot_, BorderLayout.CENTER);
				}
				if (getMode().isExec()) {
					plot_.removeAllPlots();
				}
			} else {
				this.remove(plot_);
				this.setLayout(null);
				showAll();
			}
			super.setMode(mode);
		}
	}

	private void showAll() {
		for (OVLine l : lines_) {
			if (l.getParent() == null) {
				this.add(l);
			}
			l.setVisible(true);
		}
		for (OVComponent c : components_) {
			if (c.getParent() == null) {
				this.add(c);
			}
			c.setVisible(true);
		}
	}

	private void hideAll() {
		for (OVLine l : lines_) {
			this.remove(l);
		}
		for (OVComponent c : components_) {
			this.remove(c);
		}
	}

	@Override
	public void showMenu(Point point) {
		if (getMode() == EditorMode.NODE) {
			super.showCustomMenu(point, menu_); // To change body
		} // of generated
			// methods,
			// choose Tools
			// | Templates.
	}

	@Override
	public boolean compatible(OVComponent c) {
		return (c instanceof OVValuePlot || c instanceof OVArrayPlot)
				&& getMode() == EditorMode.NODE;
	}

	public Plot2DPanel getPlot() {
		return plot_;
	}

	private void initMenu() {
		menu_ = new JMenu("Plot");
		JMenuItem i = new JMenuItem(OVValuePlot.getKey());
		i.setActionCommand(i.getText());
		menu_.add(i);
		i = new JMenuItem(OVArrayPlot.getKey());
		i.setActionCommand(i.getText());
		menu_.add(i);
	}

	@Override
	public void valueUpdated(Setting s, Value v) {
		if (s != null && s.getName().equals(xAxis)) {
			if (plot_ != null)
				plot_.setAxisLabel(0, v.getString());
		} else if (s != null && s.getName().equals(yAxis)) {
			if (plot_ != null)
				plot_.setAxisLabel(1, v.getString());
		} else
			super.valueUpdated(s, v);
	}

	public static String getKey() {
		return "Plot component";
	}
}
