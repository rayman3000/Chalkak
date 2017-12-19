package bulgogi1216.gmail.photogenic.communication;

import java.io.UnsupportedEncodingException;

/**
 * Created by dbsrh on 2017-10-27.
 */

public interface DELETEMethod extends RestfulMethodInterface {
    //Body Data Generator
    String getW3FormEncodedData(String char_set) throws UnsupportedEncodingException;
}
