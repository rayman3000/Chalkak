package bulgogi1216.gmail.photogenic.communication.Member;

import android.net.Uri;

import java.io.UnsupportedEncodingException;

import bulgogi1216.gmail.photogenic.communication.POSTMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;
import bulgogi1216.gmail.photogenic.communication.Tool;

/**
 * Created by dbsrh on 2017-10-27.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class MemberPOST extends RestfulMethod implements POSTMethod, MemberSetting {
    private String user_id;
    private String user_pw;
    private boolean kakao_linkage = false;
    private boolean facebook_linkage = false;

    public MemberPOST(String scheme, String user_id, String user_pw){
        super(scheme);
        this.user_id = user_id;
        this.user_pw = user_pw;
        setHeader("Content-Type", "application/x-www-form-urlencoded");

    }

    @Override
    public String getW3FormEncodedData(String char_set) throws UnsupportedEncodingException {
        StringBuilder data = new StringBuilder();
        Tool.appendValue(data, "kakao_linkage", (kakao_linkage)?"true":"false", char_set, false);
        Tool.appendValue(data, "facebook_linkage", (facebook_linkage)?"true":"false", char_set, false);
        Tool.appendValue(data, "user_id", user_id, char_set, false);
        Tool.appendValue(data, "user_pw", user_pw, char_set, true);
        return data.toString();
    }

    @Override
    public String buildUri(String end_point, String... paths) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .encodedAuthority(end_point);
        for(String path : paths){
            builder.appendPath(path);
        }
        return builder.build().toString();
    }

    @Override
    public String[] getPath() {
        return path;
    }

    public boolean isKakao_linkage() {
        return kakao_linkage;
    }

    public void setKakao_linkage(boolean kakao_linkage) {
        this.kakao_linkage = kakao_linkage;
        if(kakao_linkage){
            if(facebook_linkage)facebook_linkage = false;
        }
    }

    public boolean isFacebook_linkage() {
        return facebook_linkage;
    }

    public void setFacebook_linkage(boolean facebook_linkage) {
        this.facebook_linkage = facebook_linkage;
        if(facebook_linkage){
            if(kakao_linkage)kakao_linkage = false;
        }
    }
}
