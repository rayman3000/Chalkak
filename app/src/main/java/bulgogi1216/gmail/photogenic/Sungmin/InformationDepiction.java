package bulgogi1216.gmail.photogenic.Sungmin;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.material.components.model.Image;
import com.material.components.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import bulgogi1216.gmail.photogenic.R;

/**
 * Created by Sungmin on 2017-10-29.
 */

public class InformationDepiction extends Fragment {

    //Head Photo Represent
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private Runnable runnable = null;
    private Handler handler = new Handler();

    public static final String TAG = InformationDepiction.class.getSimpleName();
    /*private static int[] array_image_place = {
            com.material.components.R.drawable.image_12,
            com.material.components.R.drawable.image_13,
            com.material.components.R.drawable.image_14,
            com.material.components.R.drawable.image_15,
            com.material.components.R.drawable.image_8,
    };*/
    private static String[] array_title_place = {
            "Dui fringilla ornare finibus, orci odio",
            "Mauris sagittis non elit quis fermentum",
            "Mauris ultricies augue sit amet est sollicitudin",
            "Suspendisse ornare est ac auctor pulvinar",
            "Vivamus laoreet aliquam ipsum eget pretium",
    };
    ArrayList<String> asd;

    private String[] array_image_place;

    private static String[] array_brief_place = {
            "Foggy Hill",
            "The Backpacker",
            "River Forest",
            "Mist Mountain",
            "Side Park",
    };

    public InformationDepiction(){}

    public static InformationDepiction getInstance(){
        return new InformationDepiction();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sungmin_information_depiction, container, false);
        /*for(int i=0;i<asd.size();i++)
        {
            Log.i("Depiction", asd.get(i));
        }*/
        initComponent(view);
        return view;
    }

    private void initComponent(final View view){
        asd = ((Information)getActivity()).GetDepiction();
        //array_image_place = new String[asd.size()];
        layout_dots = (LinearLayout)view.findViewById(R.id.layout_dots);
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        adapterImageSlider = new AdapterImageSlider(this.getActivity(), asd);

        final List<Image> items = new ArrayList<>();

        /*for (int i = 0; i < array_image_place.length; i++){
            Image obj = new Image();
            obj.image = array_image_place[i];
            obj.imageDrw = getResources().getDrawable(obj.image);
            obj.name = array_title_place[i];
            obj.brief = array_brief_place[i];
            items.add(obj);
        }*/

        adapterImageSlider.setItems(asd);
        viewPager.setAdapter(adapterImageSlider);

        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
        //((TextView)view.findViewById(R.id.title)).setText(items.get(0).name);
        //((TextView)view.findViewById(R.id.brief)).setText(items.get(0).brief);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //((TextView)view.findViewById(R.id.title)).setText(items.get(position).name);
                //((TextView)view.findViewById(R.id.brief)).setText(items.get(position).brief);
                addBottomDots(layout_dots, adapterImageSlider.getCount(), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //startAutoSlider(adapterImageSlider.getCount());
    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current){
        ImageView[] dots = new ImageView[size];
        layout_dots.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new ImageView(this.getActivity());
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(com.material.components.R.drawable.shape_circle_outline);
            layout_dots.addView(dots[i]);
        }
        if(dots.length > 0){
            dots[current].setImageResource(com.material.components.R.drawable.shape_circle);
        }
    }

    private void startAutoSlider(final int count){
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if(pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private static class AdapterImageSlider extends PagerAdapter {
        private ArrayList<String> items;
        private Activity act;
        private AdapterImageSlider.OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, Image obj);
        }

        public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, ArrayList<String> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public String getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(ArrayList<String> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final String ImageURL = items.get(position);
            /*Bitmap bm;
            try {
                URL url = new URL(ImageURL);
                URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(com.material.components.R.layout.item_slider_image, container, false);

            ImageView image = (ImageView) v.findViewById(com.material.components.R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(com.material.components.R.id.lyt_parent);
            Tools.displayImageOriginal(act, image, ImageURL);
            /*lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });*/

            ((ViewPager) container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }

    }

    @Override
    public void onDestroy() {
        if(runnable != null) handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
