package tseng.min.c.utils;

import android.net.wifi.WifiConfiguration;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Neo on 2015/12/1.
 */
public class WifiHotspot {
    private static final String TAG = WifiHotspot.class.getSimpleName();

    //  http://stackoverflow.com/questions/24670772/retrieving-the-data-type-for-an-object-using-reflection
    public static String getPreSharedKey(WifiConfiguration wificfg) {
        String key;
        try {
            Field keyField = wificfg.getClass().getDeclaredField("preSharedKey");
            keyField.setAccessible(true);
            key = keyField.get(wificfg).toString();
            Log.d(TAG, "Show :  " + key);
//            Class intClass = Class.forName(key);
//            Constructor intCons = intClass.getConstructor(String.class);
//            Integer i = (Integer) intCons.newInstance(key.toString());
//            System.out.println(i);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
