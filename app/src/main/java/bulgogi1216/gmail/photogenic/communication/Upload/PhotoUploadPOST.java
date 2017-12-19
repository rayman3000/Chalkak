package bulgogi1216.gmail.photogenic.communication.Upload;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import bulgogi1216.gmail.photogenic.communication.POSTMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;

/**
 * Created by dbsrh on 2017-10-29.
 */

public class PhotoUploadPOST extends RestfulMethod implements POSTMethod, PhotoUploadSetting {

    private Context context;
    private String filename;


    private final String crlf = "\r\n";
    private final String twoHyphens = "--";
    private final String boundary = "*****";

    public PhotoUploadPOST(String scheme, Context context , String filename){
        super(scheme);
        this.filename = filename;
        this.context = context;
        setHeader("Connection", "Keep-Alive");
        setHeader("Cache-Control", "no-cache");
        setHeader("Content-Type", "multipart/form-data;boundary="+ boundary);
    }
    @Override
    public String getW3FormEncodedData(String char_set) throws UnsupportedEncodingException {
        //https://stackoverflow.com/questions/11766878/sending-files-using-post-with-httpurlconnection
        return null;
    }


    @Override
    public String[] getPath() {
        return path;
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
    public byte[] loadImageFile() throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] bytes = new byte[file.available()];
        file.read(bytes);
        file.close();
        return bytes;
    }
}
