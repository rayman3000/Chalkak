package bulgogi1216.gmail.photogenic.fragment_in_selfie;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.adapter.AdapterMostFavoriteSelfieCard;
import bulgogi1216.gmail.photogenic.adapter.AdapterMostFavoriteSelfieSlider;
import bulgogi1216.gmail.photogenic.databinding.FragmentBestSelfieBinding;
import bulgogi1216.gmail.photogenic.model.MostFavoriteSelfie;
import bulgogi1216.gmail.photogenic.util.Tools;

/**
 * Created by bulgo on 2017-10-31.
 */

public class BestSelfieFragment extends Fragment {
    private FragmentBestSelfieBinding mBinding;
    private Context mContext;
    private Runnable mRunnable = null;
    private Handler mHandler = new Handler();
    private AdapterMostFavoriteSelfieSlider mAdapterMostFavoriteSelfieSlider;
    private AdapterMostFavoriteSelfieCard mAdapterMostFavoriteSelfieCard;

    public static BestSelfieFragment newInstance() {
        return new BestSelfieFragment();
    }

    private static int[] array_image_place = {
            R.drawable.image_12,
            R.drawable.image_13,
            R.drawable.image_14,
            R.drawable.image_15,
            R.drawable.image_8,
    };

    private static String[] array_title_place = {
            "Dui fringilla ornare finibus, orci odio",
            "Mauris sagittis non elit quis fermentum",
            "Mauris ultricies augue sit amet est sollicitudin",
            "Suspendisse ornare est ac auctor pulvinar",
            "Vivamus laoreet aliquam ipsum eget pretium",
    };

    private static String[] array_brief_place = {
            "Foggy Hill",
            "The Backpacker",
            "River Forest",
            "Mist Mountain",
            "Side Park",
    };

    private void initSliderImage() {
        mAdapterMostFavoriteSelfieSlider = new AdapterMostFavoriteSelfieSlider(getActivity());

        final List<MostFavoriteSelfie> items = new ArrayList<>();
        for (int i = 0; i < array_image_place.length; i++) {
            MostFavoriteSelfie obj = new MostFavoriteSelfie();
            obj.setImage(array_image_place[i]);
            obj.setImageDrw(mContext.getDrawable(obj.getImage()));
            obj.setName(array_title_place[i]);
            obj.setBrief(array_brief_place[i]);
            items.add(obj);
        }

        mAdapterMostFavoriteSelfieSlider.setItems(items);
        mBinding.pager.setAdapter(mAdapterMostFavoriteSelfieSlider);

        // displaying selected image first
        mBinding.pager.setCurrentItem(0);
        addBottomDots(mAdapterMostFavoriteSelfieSlider.getCount(), 0);
        mBinding.title.setText(items.get(0).getName());
        mBinding.brief.setText(items.get(0).getBrief());
        mBinding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                mBinding.title.setText(items.get(pos).getName());
                mBinding.brief.setText(items.get(pos).getBrief());
                addBottomDots(mAdapterMostFavoriteSelfieSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        startAutoSlider(mAdapterMostFavoriteSelfieSlider.getCount());
    }

    private void initNewReleaseComponent() {
        Tools.displayImageOriginal(getActivity(), mBinding.lhsSelfieImageInNewSelfie, R.drawable.image_8);
        Tools.displayImageOriginal(getActivity(), mBinding.rhsSelfieImageInNewSelfie, R.drawable.image_9);
        Tools.displayImageOriginal(getActivity(), mBinding.lhsSelfieImageInRecommended, R.drawable.image_15);
        Tools.displayImageOriginal(getActivity(), mBinding.mhsSelfieImageInRecommended, R.drawable.image_14);
        Tools.displayImageOriginal(getActivity(), mBinding.rhsSelfieImageInRecommended, R.drawable.image_12);
        Tools.displayImageOriginal(getActivity(), mBinding.lhsSelfieImageInTopRated, R.drawable.image_2);
        Tools.displayImageOriginal(getActivity(), mBinding.rhsSelfieImageInTopRated, R.drawable.image_5);
    }

    private void addBottomDots(int _size, int _current) {
        ImageView[] dots = new ImageView[_size];

        mBinding.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(mContext);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            mBinding.layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[_current].setImageResource(R.drawable.shape_circle);
        }
    }

    private void startAutoSlider(final int _count) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                int pos = mBinding.pager.getCurrentItem();
                pos = pos + 1;
                if (pos >= _count) pos = 0;
                mBinding.pager.setCurrentItem(pos);
                mHandler.postDelayed(mRunnable, 3000);
            }
        };

        mHandler.postDelayed(mRunnable, 3000);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_best_selfie, container, false);
        mContext = mBinding.getRoot().getContext();

        initSliderImage();
        initNewReleaseComponent();

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        if (mRunnable != null) mHandler.removeCallbacks(mRunnable);

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
