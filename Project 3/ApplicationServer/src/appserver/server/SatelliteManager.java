package appserver.server;

import appserver.comm.ConnectivityInfo;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class SatelliteManager {

    // (the one) hash table that contains the connectivity information of all satellite servers
    static private Hashtable<String, ConnectivityInfo> satellites = null;

    public SatelliteManager() {
        // ..
    }

    public void registerSatellite(ConnectivityInfo satelliteInfo) {
        // add the satellite's name and corresponding connectivity info to the
        // hash table
        satellites.put(satelliteInfo.getName(), satelliteInfo);
    }

    public ConnectivityInfo getSatelliteForName(String satelliteName) {
        // look for satellite's connectivity info based on satellite name
        return satellites.get(satelliteName);
    }
}
