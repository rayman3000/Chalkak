package bulgogi1216.gmail.photogenic.Sungmin;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import bulgogi1216.gmail.photogenic.communication.Member.MemberGET;
import bulgogi1216.gmail.photogenic.communication.Member.MemberPUT;
import bulgogi1216.gmail.photogenic.communication.Photo.PhotoGET;
import bulgogi1216.gmail.photogenic.communication.Photo.PhotoPUT;
import bulgogi1216.gmail.photogenic.communication.RestfulAPI;
import bulgogi1216.gmail.photogenic.database.Photo;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import bulgogi1216.gmail.photogenic.R;

/**
 * Created by Sungmin on 2017-11-01.
 */

public class GridImage extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterGridImage mAdapterGridImage;
    private String mJSONString;
    private List<Photo> mItems = null;
    private Context mContext;

    public static final String TAG = GridImage.class.getSimpleName();

    public GridImage() {}

    public static GridImage getInstance() {
        return new GridImage();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sungmin_gridimageholder, container, false);
        mContext = view.getContext();
        initComponent(view);
        return view;
    }


    private void initComponent(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclergridimageholder);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this.getContext(), 3), true));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        // 이미지 받아와서 리스트에 삽입하는 기능 수행
        mJSONString = getImages();
        mItems = MakePhoto();

        mAdapterGridImage = new AdapterGridImage(this.getContext(), mItems);
        mRecyclerView.setAdapter(mAdapterGridImage);
        mAdapterGridImage.setOnItemClickListener(new AdapterGridImage.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Photo obj, int position) {
                showCustomDialog(obj);
            }
        });
    }

    private void showCustomDialog(Photo photo){
        final Dialog dialog = new Dialog(this.getContext());
        final boolean isLike;
        Log.i("ysm_raters", String.valueOf(photo.getRaters()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.sungmin_dialog_photo);
        dialog.setCancelable(true);
        ImageView photogenic = (ImageView) dialog.findViewById(R.id.photogenic);
        Tools.displayImageOriginal(this.getContext(), photogenic, "http://164.125.36.72:9967/uploads/origin/"+photo.getPhoto_url().trim());
        TextView description = (TextView) dialog.findViewById(R.id.dialog_description);
        description.setText(photo.getDescription());
        RatingBarView person_rating = (RatingBarView) dialog.findViewById(R.id.person_starView);
        person_rating.setmClickable(false);
        person_rating.setStar((int)(photo.getPerson()/photo.getRaters()));
        RatingBarView background_rating = (RatingBarView) dialog.findViewById(R.id.background_starView);
        background_rating.setmClickable(false);
        background_rating.setStar((int)(photo.getBackground()/photo.getRaters()));
        RatingBarView creativity_rating = (RatingBarView) dialog.findViewById(R.id.creativity_starView);
        creativity_rating.setmClickable(false);
        creativity_rating.setStar((int)(photo.getCreativity()/photo.getRaters()));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final String photoID = photo.get_id();
        Log.i("ysm_photoid", photoID);
        final String photoURL = photo.getPhoto_url();

        //like 상태가져오기
        final ImageView pushLike = (ImageView) dialog.findViewById(R.id.push_like);
        isLike = GetLikeState(photo.getPhoto_url(), "dbsrhksdnd@gmail.com");
        if(isLike)
            pushLike.setImageResource(R.drawable.heart_fill);
        else
            pushLike.setImageResource(R.drawable.heart_empty);

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((ImageView)dialog.findViewById(R.id.push_like)).setOnClickListener(new View.OnClickListener() {
        //pushLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLike){
                    pushLike.setImageResource(R.drawable.heart_empty);
                    SetLikeState(photoID, photoURL, isLike, "dbsrhksdnd@gmail.com");
                    dialog.dismiss();
                }
                else {
                    pushLike.setImageResource(R.drawable.heart_fill);
                    SetLikeState(photoID, photoURL, isLike, "dbsrhksdnd@gmail.com");
                    dialog.dismiss();
                }
            }
        });
        ((TextView)dialog.findViewById(R.id.goeavluate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog(photoID);
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showRatingDialog(final String photoId)
    {

        final Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.sungmin_evaluation);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final AppCompatRatingBar Person = (AppCompatRatingBar)dialog.findViewById(R.id.rating_person);
        final AppCompatRatingBar Background = (AppCompatRatingBar)dialog.findViewById(R.id.rating_background);
        final AppCompatRatingBar Creativity = (AppCompatRatingBar)dialog.findViewById(R.id.rating_creativity);

        ((ImageButton) dialog.findViewById(R.id.evaluation_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.evaluation_finish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingService(photoId, (int)Person.getRating(), (int)Background.getRating(), (int)Creativity.getRating());
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private String getImages(){
        String result="";
        RestfulAPI restfulAPI = new RestfulAPI(getString(R.string.end_point),"UTF-8");
        PhotoGET photoGET = new PhotoGET("http");
        photoGET.setCultural_property(((Information)getActivity()).GetCulturalPropertyID());
        try {
            result = restfulAPI.GET(photoGET);
            Log.i("RESTresult", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean GetLikeState(String image, String _id) {
        boolean likeState = false;
        String result = "";
        RestfulAPI restfulAPI = new RestfulAPI("164.125.36.72:9967", "UTF-8");
        MemberGET memberGET = new MemberGET("http", _id);
        try {
            result = restfulAPI.GET(memberGET);
            JSONObject jobj = new JSONObject(result);
            JSONArray likes = jobj.getJSONArray("liked_photos");
            for(int i=0; i<likes.length(); i++)
                if(image.equals(likes.getString(i)))
                    likeState = true;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return likeState;
    }
    private void SetLikeState(final String photoID, final String photo, final boolean likeState, final String _id)
    {
        RestfulAPI restfulAPI = new RestfulAPI("164.125.36.72:9967", "UTF-8");
        MemberPUT memberPUT = new MemberPUT("http", _id);
        memberPUT.setLiked_photo(photo);
        PhotoPUT photoPUT = new PhotoPUT("http", photoID);

        Log.i("photo", photoID + ", " + photo + " " + likeState);
        if(likeState)
        {
            memberPUT.setOperation(false);
            photoPUT.setLikes(false);
        }
        else
        {
            photoPUT.setLikes(true);
            memberPUT.setOperation(true);
        }
        try {
            restfulAPI.PUT(memberPUT);
            restfulAPI.PUT(photoPUT);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void RatingService(final String _id, final int person, final int background, final int creativity)
    {
        Log.i("ysm_rating", _id + ", " + person  + ", " + background  + ", " + creativity);
        RestfulAPI restfulAPI = new RestfulAPI("164.125.36.72:9967", "UTF-8");
        PhotoPUT photoPUT = new PhotoPUT("http", _id);
        photoPUT.setRaters(true);
        photoPUT.setPerson(person);
        photoPUT.setBackground(background);
        photoPUT.setCreativity(creativity);
        try {
            restfulAPI.PUT(photoPUT);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private List<Photo> MakePhoto() {
        List<Photo> PhotoList = new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(mJSONString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Photo photo = new Photo(jsonObject);
                PhotoList.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return PhotoList;
    }
}
