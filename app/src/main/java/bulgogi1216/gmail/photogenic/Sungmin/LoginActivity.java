package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import bulgogi1216.gmail.photogenic.LoadingActivity;
import bulgogi1216.gmail.photogenic.R;

public class LoginActivity extends AppCompatActivity {
    //private final ISessionCallback callback = new SessionCallback();
    //private Session session;
    private Button mSignup;
    private Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_activity_login);

        mSignIn = (Button)findViewById(R.id.sign_in_btn);
        mSignup = (Button)findViewById(R.id.sign_up_btn);

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(LoginActivity.this, SignUp.class);
                startActivity(Login);
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignIn = new Intent(LoginActivity.this, LoadingActivity.class);
                startActivity(SignIn);
            }
        });
        //session = Session.getCurrentSession();
        //session.addCallback(callback);
        //session.checkAndImplicitOpen();
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.e("세선연결","성공가입창");
            redirectSignupActivity(); //세션연결 성공 가입창
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.sungmin_activity_login);//  // 세션연결실패시 다시 로그인화면으로
        }
    }

    protected void redirectSignupActivity() { //세션 연결 성공시 가입 창 가입창은 카톡과 카스만
        final Intent intent = new Intent(this, KakaoSignactivity.class);
        startActivity(intent);
        finish();
    }*/
}
