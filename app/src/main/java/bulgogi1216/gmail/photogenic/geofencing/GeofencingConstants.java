/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bulgogi1216.gmail.photogenic.geofencing;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Constants used in this sample.
 */

final class GeofencingConstants {

    private GeofencingConstants() {
    }

    private static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";


    static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 60;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    // static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    static final float GEOFENCE_RADIUS_IN_METERS = 500; // 1 mile, 1.6 km

    static final int GEOFENCE_LOITERING_DELAY_IN_MINUTES = 10;
    static final int GEOFENCE_LOITERING_DELAY_IN_MILLISECONDS =
            GEOFENCE_LOITERING_DELAY_IN_MINUTES * 60 * 1000;
    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();

    static {
        BAY_AREA_LANDMARKS.put("제도관", new LatLng(35.2305434,129.081202));

        BAY_AREA_LANDMARKS.put("롯데리아", new LatLng(35.231245,129.0849645));

        BAY_AREA_LANDMARKS.put("집", new LatLng(35.2326041,129.0856003));

        BAY_AREA_LANDMARKS.put("부산대역", new LatLng(35.22979,129.0888378));

    }
}
