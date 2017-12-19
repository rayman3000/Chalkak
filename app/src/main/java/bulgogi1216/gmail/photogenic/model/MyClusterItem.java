package bulgogi1216.gmail.photogenic.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by JIA on 2017-10-30.
 */

public class MyClusterItem implements ClusterItem {
    private final LatLng mPosition;
    private String title;
    private String snippet;

    public MyClusterItem(double lat, double lng, String title, String snippet) {
        this.title = title;
        this.snippet = snippet;
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }
}