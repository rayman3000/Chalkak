package bulgogi1216.gmail.photogenic.Sungmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import bulgogi1216.gmail.photogenic.R;

/**
 * Created by Sungmin on 2017-12-21.
 */

public class FeedActivity extends AppCompatActivity {
    ListView mFeedList;
    ArrayList<FeedBean> mFeed = null;
    FeedAdapter mFeedAdapter = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_feedactivity);
        mFeedList = (ListView) findViewById(R.id.feed_list);

        mFeed = new ArrayList<FeedBean>();

        //데이터 쿼리

        mFeedAdapter = new FeedAdapter(this, mFeed);
        mFeedList.setAdapter(mFeedAdapter);
    }


}
