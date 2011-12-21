/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.utils;

import com.grt192.core.GRTLogger;
import com.grt192.core.GRTObject;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author Bonan
 */
public class Util{

    public static double roundValue(double val) {
        return round(val / .025) * .025;
    }

    public static int round(double x) {
        return ((int) x) + (x % 1 < .5 ? 0 : 1);
    }

    public static double distance(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static JSONObject toJSONObject(Hashtable hashtable) throws JSONException {
        JSONObject json = new JSONObject();
        Enumeration e = hashtable.keys();
        while (e.hasMoreElements()) {
            String key = ((String) e.nextElement());
            json.accumulate(key, hashtable.get(key));
        }
        return json;

    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static Vector scanCAN(boolean debug){
        int[] ids = new int[63];
        for(int id = 1; id< 64; id++){
            ids[id-1] = id;
        }
        return scanCAN(ids, debug);
    }

    public static Vector scanCAN(int[] ids, boolean debug){
        Vector v = new Vector();
        for(int j = 0; j<ids.length; j++){
            try {
                new CANJaguar(ids[j]);
            } catch (CANTimeoutException ex) {
                if(debug){
                    GRTLogger.extLog("Util","Fail on CANJag id "+ ids[j] );
                    ex.printStackTrace();
                }
                continue;
            }
            v.addElement(new Integer(j));
        }
        return v;
    }

    public static Vector scanCAN(int[] ids){
        return scanCAN(ids, false);
    }
}
