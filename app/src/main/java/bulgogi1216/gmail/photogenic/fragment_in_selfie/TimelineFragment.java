package bulgogi1216.gmail.photogenic.fragment_in_selfie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.adapter.AdapterTimeline;
import bulgogi1216.gmail.photogenic.databinding.FragmentTimelineBinding;
import bulgogi1216.gmail.photogenic.model.Timeline;
import bulgogi1216.gmail.photogenic.model.TimelineList;

/**
 * Created by bulgo on 2017-11-02.
 */

public class TimelineFragment extends Fragment {
    private FragmentTimelineBinding mBinding;
    private Context mContext;
    private AdapterTimeline mAdapterTimeline;

    private void initSpinner() {
        mBinding.spinner.setItems(getResources().getStringArray(R.array.spinner_titles_in_timeline));
        mBinding.spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void initTimelineContent() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setHasFixedSize(true);

        //set data and list adapter
        mAdapterTimeline = new AdapterTimeline();
        mBinding.recyclerView.setAdapter(mAdapterTimeline);

        // on item list clicked
        mAdapterTimeline.setOnItemClickListener(new AdapterTimeline.OnItemClickListener() {
            @Override
            public void onItemClick(View _view, Timeline _item, int _pos) {
                Toast.makeText(mContext, String.valueOf(_pos), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timeline, container, false);
        mContext = getContext();

        initSpinner();
        initTimelineContent();

        List<Timeline> timelineList = TimelineList.get().getTimelines();
        timelineList.add(new Timeline());
        timelineList.add(new Timeline());
        timelineList.add(new Timeline());
        timelineList.add(new Timeline());
        timelineList.add(new Timeline());
        timelineList.add(new Timeline());

        return mBinding.getRoot();
    }
}
