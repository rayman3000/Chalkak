package bulgogi1216.gmail.photogenic.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

public class SelfieCard implements Comparable<SelfieCard> {
	private UUID mUUID;
	private int mProfileImg;
	private String mProfileImgUrl;
	private String mName;
	private String[] addr;
	private int mSelfieImg;
	private String mSelfieImgUrl;
	private String mText;
	private Date mDate;
	private boolean section = false;

	public SelfieCard() {
	}

	public UUID getUUID() {
		return mUUID;
	}

	public String getProfileImgUrl() {
		return mProfileImgUrl;
	}

	public void setProfileImgUrl(String _profileImgUrl) {
		mProfileImgUrl = _profileImgUrl;
	}

	public String getName() {
		return mName;
	}

	public void setName(String _name) {
		mName = _name;
	}

	public String[] getAddr() {
		return addr;
	}

	public void setAddr(String[] _addr) {
		addr = _addr;
	}

	public String getSelfieImgUrl() {
		return mSelfieImgUrl;
	}

	public void setSelfieImgUrl(String _selfieImgUrl) {
		mSelfieImgUrl = _selfieImgUrl;
	}

	public String getText() {
		return mText;
	}

	public void setText(String _text) {
		mText = _text;
	}

	public int getProfileImg() {
		return mProfileImg;
	}

	public void setProfileImg(int _profileImg) {
		mProfileImg = _profileImg;
	}

	public int getSelfieImg() {
		return mSelfieImg;
	}

	public void setSelfieImg(int _selfieImg) {
		mSelfieImg = _selfieImg;
	}

	public boolean isSection() {
		return section;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date _date) {
		mDate = _date;
	}

	@Override
	public int compareTo(@NonNull SelfieCard _selfieCard) {
		return mDate.getTime() <= _selfieCard.getDate().getTime() ? -1 : 1;
	}
}
