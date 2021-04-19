package appserver.server;

import appserver.comm.ConnectivityInfo;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 * @author Yasmin Vega, Randy Duerinck
 */

// SatelliteManager registers satellites given all their connectivity information
public class SatelliteManager {

    // (the one) hash table that contains the connectivity information of all satellite servers
    static private Hashtable<String, ConnectivityInfo> satellites = null;

    public SatelliteManager() {
        // initialize hash table
        satellites = new Hashtable<String, ConnectivityInfo>();
    }

    public void registerSatellite(ConnectivityInfo satelliteInfo) {
        // add the satellite's name and corresponding connectivity info to the
        // hash table
        satellites.put(satelliteInfo.getName(), satelliteInfo);
        System.out.println("[SatelliteManager.registerSatellite] Satellite: "
                             + satelliteInfo.getName() + " registered");
    }
    
    public ConnectivityInfo getSatelliteForName(String satelliteName) {
        // get satellite's connectivity info based on satellite name
        return this.satellites.get(satelliteName);
    }
}
