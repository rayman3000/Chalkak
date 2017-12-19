package bulgogi1216.gmail.photogenic.fragment_in_main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bulgogi1216.gmail.photogenic.CameraActivity;
import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.SelfieActivity;
import bulgogi1216.gmail.photogenic.Sungmin.ChatBotActivity;
import bulgogi1216.gmail.photogenic.Sungmin.ProvinceGridList;
import bulgogi1216.gmail.photogenic.adapter.AdapterListHomeMenu;
import bulgogi1216.gmail.photogenic.databinding.FragmentHomeBinding;
import bulgogi1216.gmail.photogenic.model.HomeMenuCategory;

/**
 * Created by bulgo on 2017-10-27.
 */

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private Context mContext;
    private AdapterListHomeMenu mAdapterListHomeMenu;
    private String[] mMenuTitles;

    private void initComponent() {
        mMenuTitles = getResources().getStringArray(R.array.menu_titles_in_main);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setNestedScrollingEnabled(false);

        //set data and list adapter
        mAdapterListHomeMenu = new AdapterListHomeMenu();
        mBinding.recyclerView.setAdapter(mAdapterListHomeMenu);

        // on item list clicked
        mAdapterListHomeMenu.setOnItemClickListener(new AdapterListHomeMenu.OnItemClickListener() {
            @Override
            public void onItemClick(View _view, HomeMenuCategory _item) {
                if(_item.getTitle().equals(mMenuTitles[0])) {
                    Intent intent = new Intent(mContext, SelfieActivity.class);
                    startActivity(intent);
                } else if(_item.getTitle().equals(mMenuTitles[2])) {
                    Intent intent = new Intent(mContext, CameraActivity.class);
                    startActivity(intent);
                } else if(_item.getTitle().equals(mMenuTitles[1])) {
                    Intent intent = new Intent(mContext, ChatBotActivity.class);
                    startActivity(intent);
                } else if(_item.getTitle().equals(mMenuTitles[3])) {
                    Intent intent = new Intent(mContext, ProvinceGridList.class);
                    startActivity(intent);
                }
            }
        });
    }

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mContext = mBinding.getRoot().getContext();

        initComponent();

        return mBinding.getRoot();
    }
}
