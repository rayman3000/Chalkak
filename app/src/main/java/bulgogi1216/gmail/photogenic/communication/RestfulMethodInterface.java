package bulgogi1216.gmail.photogenic.communication;

/**
 * Created by dbsrh on 2017-10-29.
 */

public interface RestfulMethodInterface {
    public String getHeader_key();
    public String getHeader_value();
    public String[] getPath();
    String buildUri(String end_point, String... paths);
}
