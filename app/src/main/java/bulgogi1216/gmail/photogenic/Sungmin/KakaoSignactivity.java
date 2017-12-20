package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import bulgogi1216.gmail.photogenic.MainActivity;
import bulgogi1216.gmail.photogenic.R;

public class KakaoSignactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_kakao_signactivity);
        requestMe();
    }


    //사용자 상태를 알아보기위해 카톡 me api
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    Log.e("error","session failed");

                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("세션이 닫힘", "세션닫힘");
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                Log.e("카톡회원이아님","카톡회원");
            } // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                //성공시 이메일과 닉네임을 네비바로 보냄
                SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("session_id", String.valueOf(userProfile.getId()));
                editor.putString("session_nickname", userProfile.getNickname().toString());

                Log.e("UserProfile", userProfile.toString()); // 원하는 사용자 프로필 여기!!
                Log.e("UserProfile", userProfile.getId() + "");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("value", userProfile.getNickname().toString());
                //intent.putExtra("email", userProfile.getEmail().toString());
                if(userProfile.getProfileImagePath()!=null) {
                    intent.putExtra("profileimg", userProfile.getProfileImagePath().toString());
                    editor.putString("session_profileimg",userProfile.getProfileImagePath().toString());
                }
                startActivity(intent);



//              redirectMainActivity(); // 로그인 성공시 MainActivity로
            }
        });
    }

    private void redirectMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
