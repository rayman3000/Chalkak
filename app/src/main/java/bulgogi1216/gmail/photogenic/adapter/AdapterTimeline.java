package bulgogi1216.gmail.photogenic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bulgogi1216.gmail.photogenic.databinding.ItemTimelineBinding;
import bulgogi1216.gmail.photogenic.model.Timeline;
import bulgogi1216.gmail.photogenic.model.TimelineList;

/**
 * Created by bulgo on 2017-11-02.
 */

public class AdapterTimeline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Timeline> mItems;
    private AdapterTimeline.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(AdapterTimeline.OnItemClickListener _onItemClickListener) {
        this.mOnItemClickListener = _onItemClickListener;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimelineBinding mBinding;

        private OriginalViewHolder(ItemTimelineBinding _binding) {
            super(_binding.getRoot());
            mBinding = _binding;
        }

        public void bind(Timeline _item) {
            mBinding.date.setText("1991.12.16");
//            mBinding.setVariable(BR.home_menu_category, _item);
            mBinding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View _view, Timeline _item, int _pos);
    }

    public AdapterTimeline() {
        mItems = TimelineList.get().getTimelines();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTimelineBinding binding = ItemTimelineBinding.inflate(layoutInflater, parent, false);

        return new OriginalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            Timeline item = mItems.get(position);
            view.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
