package bulgogi1216.gmail.photogenic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bulgo on 2017-11-02.
 */

public class TimelineList {
    private static TimelineList sTimelineList;

    private List<Timeline> mTimelines;

    private TimelineList() {
        mTimelines = new ArrayList<>();
    }

    public List<Timeline> getTimelines() {
        return mTimelines;
    }

    public static TimelineList get() {
        if(sTimelineList == null) {
            sTimelineList = new TimelineList();
        }
        return sTimelineList;
    }

    public Timeline getTimeline(UUID _uuid) {
        for (Timeline timeline : mTimelines) {
            if(timeline.getUUID().equals(_uuid)) {
                return timeline;
            }
        }
        return null;
    }
}
