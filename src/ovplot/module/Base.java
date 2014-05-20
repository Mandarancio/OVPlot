/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ovplot.module;

import core.module.BaseModule;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JMenu;

/**
 *
 * @author martino
 */
public class Base extends BaseModule{

    public Base() {
        super("OVPlot", "0.1");
    }

    @Override
    public ArrayList<JMenu> getNodeMenus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<JMenu> getGuiMenus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, Class<?>> getComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
