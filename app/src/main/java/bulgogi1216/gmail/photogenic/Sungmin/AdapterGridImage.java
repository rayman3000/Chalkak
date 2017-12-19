package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.material.components.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.database.Photo;

/**
 * Created by Sungmin on 2017-10-31.
 */

public class AdapterGridImage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Photo> mItems = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, Photo obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterGridImage(Context context, List<Photo> items){
        this.mItems = items;
        mContext = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImage;
        public TextView mUser;
        public TextView mStars;
        public TextView mLikes;

        public OriginalViewHolder(View v){
            super(v);
            mImage = (ImageView) v.findViewById(R.id.grid_image);
            mUser = (TextView) v.findViewById(R.id.grid_user);
            mStars = (TextView) v.findViewById(R.id.grid_stars);
            mLikes = (TextView) v.findViewById(R.id.grid_likes);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sungmin_gridimage, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Photo obj = mItems.get(position);
        if(holder instanceof OriginalViewHolder){
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.mUser.setText(obj.getUploader());
            view.mStars.setText(String.valueOf(obj.getRating()));
            view.mLikes.setText(String.valueOf(obj.getLikes()));
            Tools.displayImageOriginal(mContext, view.mImage, "http://164.125.36.72:9967/uploads/modified/"+obj.getPhoto_url().trim());
            view.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(v, mItems.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
