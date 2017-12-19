package bulgogi1216.gmail.photogenic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bulgogi1216.gmail.photogenic.BR;
import bulgogi1216.gmail.photogenic.databinding.ItemHomeMenuCategoryBinding;
import bulgogi1216.gmail.photogenic.model.HomeMenuCategory;
import bulgogi1216.gmail.photogenic.model.HomeMenuCategoryList;

/**
 * Created by bulgo on 2017-10-27.
 */

public class AdapterListHomeMenu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomeMenuCategory> mItems;
    private AdapterListHomeMenu.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(AdapterListHomeMenu.OnItemClickListener _onItemClickListener) {
        this.mOnItemClickListener = _onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View _view, HomeMenuCategory _item);
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private final ItemHomeMenuCategoryBinding mBinding;

        private OriginalViewHolder(ItemHomeMenuCategoryBinding _binding) {
            super(_binding.getRoot());
            mBinding = _binding;
        }

        public void bind(final HomeMenuCategory _item) {
            mBinding.setVariable(BR.home_menu_category, _item);
            mBinding.lytParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    if(mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(_view, _item);
                    }
                }
            });
            mBinding.executePendingBindings();
        }
    }

    public AdapterListHomeMenu() {
        mItems = HomeMenuCategoryList.get().getHomeMenuCategories();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemHomeMenuCategoryBinding binding = ItemHomeMenuCategoryBinding.inflate(layoutInflater, parent, true);

        return new OriginalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            HomeMenuCategory item = mItems.get(position);

            view.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
