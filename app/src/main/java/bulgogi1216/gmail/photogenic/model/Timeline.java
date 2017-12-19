package bulgogi1216.gmail.photogenic.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bulgo on 2017-11-02.
 */

public class Timeline {
    private UUID mUUID;
    private int mParentViewID;
    private User mUser;
    private Date mDate;
    private String mComment;

    public UUID getUUID() {
        return mUUID;
    }

    public int getParentViewID() {
        return mParentViewID;
    }

    public void setParentViewID(int _parentViewID) {
        mParentViewID = _parentViewID;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User _user) {
        mUser = _user;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date _date) {
        mDate = _date;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String _comment) {
        mComment = _comment;
    }
}
