package util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.squawk.util.StringTokenizer;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


/**
 *
 * @author Bonan
 */
public class Util {

    public static double doubleValue(String s) {
        return Double.valueOf(s).doubleValue();
    }

    public static double doubleValue(Object o) {
        return ((Double) o).doubleValue();
    }

    public static double roundValue(double val) {
        return round(val / .025) * .025;
    }

    public static int round(double x) {
        return ((int) x) + (x % 1 < .5 ? 0 : 1);
    }

    public static double distance(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

//    public static JSONObject toJSONObject(Hashtable hashtable) throws JSONException {
//        JSONObject json = new JSONObject();
//        Enumeration e = hashtable.keys();
//        while (e.hasMoreElements()) {
//            String key = ((String) e.nextElement());
//            json.accumulate(key, hashtable.get(key));
//        }
//        return json;
//
//    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

//    public static Vector scanCAN(boolean debug) {
//        int[] ids = new int[63];
//        for (int id = 1; id < 64; id++) {
//            ids[id - 1] = id;
//        }
//        return scanCAN(ids, debug);
//    }

//    public static Vector scanCAN(int[] ids, boolean debug) {
//        Vector v = new Vector();
//        for (int j = 0; j < ids.length; j++) {
//            try {
//                new CANJaguar(ids[j]);
//            } catch (CANTimeoutException ex) {
//                if (debug) {
//                    GRTLogger.extLog("Util", "Fail on CANJag id " + ids[j]);
//                    ex.printStackTrace();
//                }
//                continue;
//            }
//            v.addElement(new Integer(ids[j]));
//        }
//        return v;
//    }

//    public static Vector scanCAN(int[] ids) {
//        return scanCAN(ids, false);
//    }

//    public static void scanCAN(int size) {
//        int runs = 0;
//        while (true) {
//            Vector v = Util.scanCAN(false);
//            System.out.println("CAN Devices detected: " + v.toString());
//            if (v.size() == size && runs++ >= 4) {
//                break;
//            }
//        }
//    }

    public static String[] separateString(String msg) {
        StringTokenizer stk = new StringTokenizer(msg, " ");
        String[] result = new String[stk.countTokens()];
        for (int i = 0; stk.hasMoreTokens(); i++) {
            result[i] = stk.nextToken();
        }
        return result;
    }

    public static String arraytoString(String[] s) {
        String retu = "[";
        for (int i = 0; i < s.length; i++) {
            retu += s[i] + ", ";
        }
        retu += " ]";
        return retu;
    }
}