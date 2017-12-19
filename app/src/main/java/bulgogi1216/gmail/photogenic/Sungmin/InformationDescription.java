package bulgogi1216.gmail.photogenic.Sungmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import bulgogi1216.gmail.photogenic.R;

/**
 * Created by Sungmin on 2017-10-30.
 */

public class InformationDescription extends Fragment{

    private JSONObject mJSON;
    private TextView mTitle;
    private TextView mAddress;
    private TextView mDescription;
    private TextView mClosedForTheDay;
    private TextView mTimeAvailable;
    private TextView mBabyEquipmentRental;
    private TextView mPetAvailable;
    private TextView mParking;
    public static final String TAG = InformationDescription.class.getSimpleName();

    public InformationDescription(){}

    public static InformationDescription getInstance(){
        return new InformationDescription();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sungmin_information_description, container, false);

       InitComponent(view);
        return view;
    }
    public void InitComponent(final View view)
    {
        mJSON = ((Information)getActivity()).getJSONObject();
        Log.i("jsonstring", mJSON.toString());
        mTitle = (TextView) view.findViewById(R.id.infotitle);
        mAddress = (TextView) view.findViewById(R.id.infoaddress);
        mDescription = (TextView) view.findViewById(R.id.infodescription);
        mClosedForTheDay = (TextView) view.findViewById(R.id.infoclosedfortheday);
        mTimeAvailable = (TextView) view.findViewById(R.id.infotimeavailable);
        mBabyEquipmentRental = (TextView) view.findViewById(R.id.infobabyequipmentrental);
        mPetAvailable = (TextView) view.findViewById(R.id.infopetavailable);
        mParking = (TextView) view.findViewById(R.id.infoparking);

        try {
            mTitle.setText(mJSON.getString("title"));
            mAddress.setText(mJSON.getString("address"));
            mDescription.setText("\t\t\t" + mJSON.getString("description"));
            mClosedForTheDay.setText(mJSON.getString("closed_for_the_day"));
            mTimeAvailable.setText(mJSON.getString("time_available"));
            mBabyEquipmentRental.setText(mJSON.getString("baby_equipment_rental"));
            mPetAvailable.setText(mJSON.getString("pet_available"));
            mParking.setText(mJSON.getString("parking"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
