package bulgogi1216.gmail.photogenic.communication.Member;

import android.net.Uri;

import bulgogi1216.gmail.photogenic.communication.GETMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;

/**
 * Created by dbsrh on 2017-10-27.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class MemberGET extends RestfulMethod implements GETMethod, MemberSetting{
    private String user_id;

    public MemberGET(String scheme, String user_id){
        super(scheme);
        this.user_id = user_id;
        setHeader("Content-Type", "application/x-www-form-urlencoded");

    }

    @Override
    public String buildUri(String end_point, String... paths) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .encodedAuthority(end_point)
                .appendQueryParameter("user_id", user_id);
        for(String path : paths){
            builder.appendPath(path);
        }
        return builder.build().toString();
    }

    @Override
    public String[] getPath() {
        return path;
    }
}
