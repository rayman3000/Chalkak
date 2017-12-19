package bulgogi1216.gmail.photogenic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import bulgogi1216.gmail.photogenic.databinding.ItemMostFavoriteSelfieCardBinding;
import bulgogi1216.gmail.photogenic.databinding.ItemSectionInMostFavoriteSelfieCardBinding;
import bulgogi1216.gmail.photogenic.model.SelfieCard;
import bulgogi1216.gmail.photogenic.model.SelfieCardList;

public class AdapterMostFavoriteSelfieCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "AdpMostFavoriteSelfieCard";

    private final int VIEW_ITEM = 1;
    private final int VIEW_SECTION = 0;

    private List<SelfieCard> mItems;
    private Context mContext;
/*    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, People obj, int position);
    }*/

    public AdapterMostFavoriteSelfieCard(Context _context) {
        mContext = _context;
        mItems = SelfieCardList.get().getSelfieCards();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private final ItemMostFavoriteSelfieCardBinding mBinding;

        private OriginalViewHolder(ItemMostFavoriteSelfieCardBinding _binding) {
            super(_binding.getRoot());
            mBinding = _binding;
        }

        public void bind(SelfieCard _item) {
            mBinding.name.setText(_item.getName());
            mBinding.profileImage.setImageResource(_item.getProfileImg());
            mBinding.selfieImage.setImageResource(_item.getSelfieImg());
            mBinding.text.setText(_item.getText());

            mBinding.executePendingBindings();
        }
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        private final ItemSectionInMostFavoriteSelfieCardBinding mBinding;

        private SectionViewHolder(ItemSectionInMostFavoriteSelfieCardBinding _binding) {
            super(_binding.getRoot());
            mBinding = _binding;
        }

        public void bind(SelfieCard _item) {
            mBinding.titleSection.setText("test section");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        Object binding;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW_ITEM:
                binding = ItemMostFavoriteSelfieCardBinding.inflate(layoutInflater, parent, false);
                vh = new OriginalViewHolder((ItemMostFavoriteSelfieCardBinding)binding);
                break;

            case VIEW_SECTION:
                binding = ItemSectionInMostFavoriteSelfieCardBinding.inflate(layoutInflater, parent, false);
                vh = new SectionViewHolder((ItemSectionInMostFavoriteSelfieCardBinding)binding);
                break;

            default:
                //Log.e(TAG, "vh is null");
                vh = null;
                break;
        }

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SelfieCard item = mItems.get(position);

        if(holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.bind(item);
        } else {
            SectionViewHolder view = (SectionViewHolder) holder;
            view.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.mItems.get(position).isSection() ? VIEW_SECTION : VIEW_ITEM;
    }

    public void insertItem(int _i, SelfieCard _item){
        mItems.add(_i, _item);
        notifyItemInserted(_i);
    }
}