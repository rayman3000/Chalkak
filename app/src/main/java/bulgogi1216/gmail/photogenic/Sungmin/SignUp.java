package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import bulgogi1216.gmail.photogenic.R;

/**
 * Created by Sungmin on 2017-12-19.
 */

public class SignUp extends AppCompatActivity{
    private AutoCompleteTextView mNickname;
    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private CircularImageView mProfileImage;
    private Button mSearchProfileImage;
    private Button mSubmit;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_signupform);
        mNickname = (AutoCompleteTextView)findViewById(R.id.signup_nickname);
        mEmail = (AutoCompleteTextView)findViewById(R.id.signup_email);
        mPassword = (EditText) findViewById(R.id.signup_password);
        mConfirmPassword = (EditText) findViewById(R.id.signup_confirmpassword);
        mProfileImage = (CircularImageView) findViewById(R.id.signup_profieimage);
        mSearchProfileImage = (Button) findViewById(R.id.signup_searchprofileimage);
        mSubmit = (Button) findViewById(R.id.signup_button);

        mProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.profileimage_default));

        mSearchProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlbumPicker();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB에 저장
            }
        });
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.i("imageresult",)

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            Log.i("imageurl", uri.toString());
        }
    }

    public void startAlbumPicker(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
}
