package bulgogi1216.gmail.photogenic.geofencing;

public class GeofenceTimer {
    private static GeofenceTimer sGeofenceTimer;
    private long currentTime;
    private long blockingTime;

    private GeofenceTimer() {
        currentTime = System.currentTimeMillis();
        blockingTime = System.currentTimeMillis();
    }

    public static GeofenceTimer get() {
        if(sGeofenceTimer == null) {
            sGeofenceTimer = new GeofenceTimer();
        }
        return sGeofenceTimer;
    }

    public void setTimer() {
        blockingTime = System.currentTimeMillis();
    }

    public boolean notificationAvailableCheck() {
        currentTime = System.currentTimeMillis();

        return (currentTime - blockingTime > 200000);
    }
}
