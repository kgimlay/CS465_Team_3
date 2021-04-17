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
        lastSatelliteIndex = 0;
    }

    public void satelliteAdded(String satelliteName) {
        // add satellite
        satellites.add(satelliteName);
    }


    public String nextSatellite() throws Exception {
        
        int numberSatellites;
        
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

        return (String)satellites.get(lastSatelliteIndex); // ... name of satellite who is supposed to take job
        
    }
}
