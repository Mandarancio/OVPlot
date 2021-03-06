package ovplot;

import gui.components.nodes.InNode;
import gui.components.ovnode.OVNodeComponent;
import gui.constants.ComponentSettings;
import gui.enums.EditorMode;
import gui.interfaces.OVContainer;
import gui.support.Setting;

import java.awt.Color;

import org.math.plot.plots.BarPlot;
import org.math.plot.plots.CloudPlot2D;
import org.math.plot.plots.HistogramPlot2D;
import org.math.plot.plots.LinePlot;
import org.math.plot.plots.Plot;
import org.math.plot.plots.ScatterPlot;
import org.math.plot.plots.StaircasePlot;
import org.w3c.dom.Element;

import core.SlotInterface;
import core.SlotListener;
import core.Value;
import core.ValueType;

public class OVValuePlot extends OVNodeComponent implements SlotListener {

	/**
     *
     */
	private static final long serialVersionUID = 1146188205942638920L;
	private static final String _Type = "Type", _Plot = "Plot",
			_Color = "Color";
	private static final String _Xin = "X in", _Yin = "Y in", _Clear = "Clear";
	private OVPlotComponent plotPanel_;
	private Plot plot_;
	private double[][] values_ = null;
	private int _xCounter, _yCounter;

	// private InNode yInNode_;

	public OVValuePlot(OVContainer father) {
		super(father);
		Setting s = new Setting(_Plot, "line", this);
		addNodeSetting(ComponentSettings.SpecificCategory, s);
		s = new Setting(_Type, PlotType.LINE, this);
		addNodeSetting(ComponentSettings.SpecificCategory, s);
		s = new Setting(_Color, new Color(255, 0, 0), this);
		addNodeSetting(ComponentSettings.SpecificCategory, s);
		if (father instanceof OVPlotComponent) {
			plotPanel_ = ((OVPlotComponent) father);
		}

		InNode n = addInput(_Xin, ValueType.DOUBLE);
		n.addListener(this);
		n = addInput(_Yin, ValueType.DOUBLE);
		n.addListener(this);
		n = addInput(_Clear, ValueType.VOID);
		n.addListener(this);

		getSetting(ComponentSettings.Name).setValue("Line");
	}

	public OVValuePlot(Element e, OVContainer father) {
		super(e, father);
		for (InNode n : inputs_) {
			n.addListener(this);
		}
		if (father instanceof OVPlotComponent) {
			plotPanel_ = ((OVPlotComponent) father);
		}
	}

	@Override
	public void setFather(OVContainer father) {
		super.setFather(father);

		if (father instanceof OVPlotComponent) {
			plotPanel_ = ((OVPlotComponent) father);
		}
	}

	@Override
	public void setMode(EditorMode mode) {
		if (mode.isExec()) {
			values_ = new double[0][0];
			_xCounter = 0;
			_yCounter = 0;
		} else {
			if (plotPanel_ != null && plotPanel_.getPlot() != null) {
				plotPanel_.getPlot().removePlot(plot_);
				plot_ = null;
			}
		}
		super.setMode(mode);
	}

	private void initPlot() {
		try {
			PlotType type = (PlotType) getNodeSetting(_Type).getValue()
					.getEnum();
			String name = getNodeSetting(_Plot).getValue().getString();
			Color color = getNodeSetting(_Color).getValue().getColor();
			switch (type) {
			case STAIRCASE:
				plot_ = new StaircasePlot(name, color, values_);
				break;
			case SCATTER:
				plot_ = new ScatterPlot(name, color, values_);
				break;
			case HISTOGRAM:
				plot_ = new HistogramPlot2D(name, color, values_, 0.5);
				break;
			case CLOUD:
				plot_ = new CloudPlot2D(name, color, values_, 0.2, 0.2);
				break;
			case BAR:
				plot_ = new BarPlot(name, color, values_);
			case LINE:
			default:
				plot_ = new LinePlot(name, color, values_);
				break;
			}
			plotPanel_.getPlot().addPlot(plot_);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void valueRecived(SlotInterface s, Value v) {
		try {
			if (s.getLabel().equals(_Xin)) {
				if (v.getType().isNumeric()) {
					if (_xCounter >= _yCounter) {
						double arr[][] = new double[_xCounter + 1][2];

						for (int i = 0; i < _xCounter; i++) {
							arr[i][0] = values_[i][0];
							arr[i][1] = values_[i][1];
						}
						arr[_xCounter][0] = v.getDouble();
						values_ = arr;
						_xCounter++;
					} else {
						values_[_xCounter][0] = v.getDouble();
						// if (yInNode_.isFree()){
						// values_[_xCounter][1]=_xCounter;
						// _yCounter++;
						// }
						_xCounter++;
					}
					if (_xCounter == _yCounter) {
						if (plot_ == null) {
							initPlot();
						}
						plot_.setData(values_);
						plotPanel_.revalidate();
						plotPanel_.getPlot().setAutoBounds();
					}
				}
			} else if (s.getLabel().equals(_Yin)) {
				if (v.getType().isNumeric()) {
					if (_yCounter >= _xCounter) {
						double arr[][] = new double[_yCounter + 1][2];

						for (int i = 0; i < _yCounter; i++) {
							arr[i][0] = values_[i][0];
							arr[i][1] = values_[i][1];
						}
						arr[_yCounter][1] = v.getDouble();
						values_ = arr;
						_yCounter++;
					} else {
						values_[_yCounter][1] = v.getDouble();
						_yCounter++;
					}
					if (_xCounter == _yCounter) {
						if (plot_ == null) {
							initPlot();
						}
						plot_.setData(values_);
						plotPanel_.getPlot().setAutoBounds();
					}
				}
			} else if (s.getLabel().equals(_Clear)) {
				if (plot_ != null) {
					plotPanel_.getPlot().removePlot(plot_);
					plot_ = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getKey() {
		return "Value Plot";
	}
}
