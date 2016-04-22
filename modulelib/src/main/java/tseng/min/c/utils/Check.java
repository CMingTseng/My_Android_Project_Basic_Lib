package tseng.min.c.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Neo on 2015/11/18.
 */
public class Check {
    public static final int NONE_ENCRYPTION = 0;
    public static final int WPS_ENCRYPTION = 1;
    public static final int WEP_ENCRYPTION = 2;


    public static final int WPA_PSK_TKIP_ENCRYPTION = 3;
    public static final int WPA_PSK_CCMP_ENCRYPTION = 4;
    public static final int WPA_PSK_CCMP_TKIP_ENCRYPTION = 5;

    public static final int WPA2_PSK_TKIP_ENCRYPTION = 6;
    public static final int WPA2_PSK_CCMP_ENCRYPTION = 7;
    public static final int WPA2_PSK_CCMP_TKIP_ENCRYPTION = 8;

    //    WPA-PSK-CCMP+TKIP_WPA2-PSK-CCMP+TKIP_WPS_ESS
    public static int CheckWifiInfoType(String capabilities) {
        int type = -1;
        final String[] preprocess = capabilities.split("_");
        for (String auth : preprocess) {
            if (auth.indexOf("NONE") != -1) {
                type = NONE_ENCRYPTION;
            }
            if (auth.indexOf("WPS") != -1) {
                type = WPS_ENCRYPTION;
            }
            if (auth.indexOf("WEP") != -1) {
                type = WEP_ENCRYPTION;
            }
            if (auth.indexOf("WPA-PSK-CCMP") != -1) {
                type = WPA_PSK_CCMP_ENCRYPTION;
            }
            if (auth.indexOf("WPA-PSK-TKIP") != -1) {
                type = WPA_PSK_TKIP_ENCRYPTION;
            }
            if (auth.indexOf("WPA-PSK-CCMP+TKIP") != -1) {
                type = WPA_PSK_CCMP_TKIP_ENCRYPTION;
            }

            if (auth.indexOf("WPA2-PSK-CCMP") != -1) {
                type = WPA2_PSK_CCMP_ENCRYPTION;
            }
            if (auth.indexOf("WPA2-PSK-TKIP") != -1) {//FIXME check this string !!!
                type = WPA2_PSK_TKIP_ENCRYPTION;
            }
            if (auth.indexOf("WPA2-PSK-CCMP+TKIP") != -1) {
                type = WPA2_PSK_CCMP_TKIP_ENCRYPTION;
            }
        }

        return type;
    }

    public static boolean CheckDirect(String dir) {
        final File rootFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + dir);
        if (!rootFolder.exists()) {
            rootFolder.mkdir();
        }
        return rootFolder.exists();
    }
}
