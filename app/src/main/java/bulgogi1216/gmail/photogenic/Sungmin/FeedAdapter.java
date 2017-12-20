package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.components.utils.Tools;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import bulgogi1216.gmail.photogenic.R;

/**
 * Created by Sungmin on 2017-12-21.
 */

public class FeedAdapter extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<FeedBean> mData;

    public FeedAdapter(Context mContext, ArrayList<FeedBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView=mInflater.inflate(R.layout.sungmin_feedactivity, parent, false);
        CircularImageView ProfileImage = (CircularImageView)convertView.findViewById(R.id.feed_profileimage);
        TextView NickName = (TextView)convertView.findViewById(R.id.feed_nickname);
        TextView Place = (TextView)convertView.findViewById(R.id.feed_place);
        TextView Description = (TextView)convertView.findViewById(R.id.feed_description);
        ImageView Photo = (ImageView)convertView.findViewById(R.id.feed_photo);

        NickName.setText(mData.get(position).getmNickName());
        Tools.displayImageOriginal(mContext, ProfileImage, "http://164.125.36.72:9967/uploads/modified/"+mData.get(position).getmProfilePhoto().trim());
        Place.setText(mData.get(position).getmPlace());
        Description.setText(mData.get(position).getmDescription());
        Tools.displayImageOriginal(mContext, Photo, "http://164.125.36.72:9967/uploads/modified/"+mData.get(position).getmPhoto().trim());

        return convertView;
    }
}
