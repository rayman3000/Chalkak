package bulgogi1216.gmail.photogenic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by bulgo on 2017-11-01.
 */

public class SelfieCardList {
    private static SelfieCardList sSelfieCardList;

    private List<SelfieCard> mSelfieCards;
    private List<SelfieCard> mSelfieOfFriendCards;

    private SelfieCardList() {
        mSelfieCards = new ArrayList<>();
        mSelfieOfFriendCards = new ArrayList<>();
    }

    public static SelfieCardList get() {
        if(sSelfieCardList == null) {
            sSelfieCardList = new SelfieCardList();
        }
        return sSelfieCardList;
    }

    public List<SelfieCard> getSelfieCards() {
        return mSelfieCards;
    }

    public List<SelfieCard> getSelfieOfFriendCards() {
        return mSelfieOfFriendCards;
    }

    public SelfieCard getMostFavoriteSelfieCard(UUID _uuid) {
        for (SelfieCard selfieCard : mSelfieCards) {
            if(selfieCard.getUUID().equals(_uuid)) {
                return selfieCard;
            }
        }
        return null;
    }
}
