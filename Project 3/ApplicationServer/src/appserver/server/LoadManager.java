package appserver.server;

import java.util.ArrayList;

/**
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 * @author Randy Duerinck, Kevin Imlay, Yasmin Vega
 */
public class LoadManager {

    static ArrayList satellites = null;
    static int lastSatelliteIndex = -1;

    public LoadManager() {
        satellites = new ArrayList<String>();
    }

    public void satelliteAdded(String satelliteName) {
        // add satellite
        satellites.add(satelliteName);
        System.out.println("[LoadManager.satelliteAdded] Added " + satelliteName);
    }


    public String nextSatellite() throws Exception {
        synchronized (satellites) 
        {
            // implement policy that returns the satellite name according to a round robin methodology
            if( lastSatelliteIndex == satellites.size()-1 )
            {
                lastSatelliteIndex = 0;
            }
            else
            {
                lastSatelliteIndex ++;
            }
        }
        
        return (String)this.satellites.get(lastSatelliteIndex); // ... name of satellite who is supposed to take job
        
    }
}
