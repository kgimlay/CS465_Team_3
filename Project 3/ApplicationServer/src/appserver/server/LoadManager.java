package appserver.server;

import java.util.ArrayList;

/**
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 * @author Randy Duerinck, Kevin Imlay, Yasmin Vega
 */
//LoadManager will use a round-robin methodology to balance the server load on
//several satellite servers: Earth, Venus, Mercury.
public class LoadManager {

    // satellites array list will hold the names of the satellites
    static ArrayList satellites = null;
    // the last accessed index of the satellites array list
    static int lastSatelliteIndex = -1;

    public LoadManager() {
        // initialize the satellites array list
        satellites = new ArrayList<String>();
    }

    public void satelliteAdded(String satelliteName) {
        // add satellite's name to the array list
        satellites.add(satelliteName);
        System.out.println("[LoadManager.satelliteAdded] Added " + satelliteName);
    }

    public String nextSatellite() throws Exception {
        synchronized (satellites) {
            // implement policy that returns the satellite name according to a round robin methodology
            if (lastSatelliteIndex == satellites.size() - 1) {
                // if we are at the end of the satellites array list, go back to
                // the beginning of the array list
                lastSatelliteIndex = 0;
            } else {
                // if we are not at the end of the array list, interate to the
                // next satellite
                lastSatelliteIndex++;
            }
        }

        // return the name of the satellite who is supposed to take the job
        return (String) this.satellites.get(lastSatelliteIndex);

    }
}
