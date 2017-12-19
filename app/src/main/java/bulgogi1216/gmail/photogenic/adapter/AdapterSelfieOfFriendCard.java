package bulgogi1216.gmail.photogenic.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.databinding.ItemSelfieOfFriendCardBinding;
import bulgogi1216.gmail.photogenic.model.SelfieCard;
import bulgogi1216.gmail.photogenic.model.SelfieCardList;
import bulgogi1216.gmail.photogenic.util.ImageUtil;

/**
 * Created by bulgo on 2017-11-02.
 */

public class AdapterSelfieOfFriendCard extends BaseAdapter {
    private Context mContext;
    private List<SelfieCard> mItems;
    private LayoutInflater mInflater;

    public AdapterSelfieOfFriendCard(Context _context) {
        mContext = _context;
        mItems = SelfieCardList.get().getSelfieOfFriendCards();
    }

    @Override
    public Object getItem(int _i) {
        return mItems.get(_i);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int _i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        if (mInflater == null) {
            mInflater = ((Activity) _parent.getContext()).getLayoutInflater();
        }

        // Perform the binding
        ItemSelfieOfFriendCardBinding binding = DataBindingUtil.getBinding(_convertView);

        if(binding == null) {
            binding = DataBindingUtil.inflate(mInflater, R.layout.item_selfie_of_friend_card, _parent, false);
        }

        if (_position != 0)
            binding.layoutHeaderOfItem.setVisibility(View.GONE);
        else
            binding.layoutHeaderOfItem.setVisibility(View.VISIBLE);

        SelfieCard item = mItems.get(_position);

        ImageUtil.displayRoundImage(binding.profileImg, item.getProfileImgUrl(), null);
        ImageUtil.displayImage(binding.selfieImg, item.getSelfieImgUrl(), null);
        binding.lvisHours.setText(_position + 1 + " hours ago");
        binding.name.setText(item.getText());

        binding.selfieImg.setTag(_position);
        binding.likeBtn.setTag(_position);
        binding.commentBtn.setTag(_position);
        binding.putInBtn.setTag(_position);
        binding.friends.setTag(_position);

        binding.executePendingBindings();

        // Return the bound view
        return binding.getRoot();
    }
}
