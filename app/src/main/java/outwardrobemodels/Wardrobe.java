package outwardrobemodels;

import java.util.ArrayList;

public class Wardrobe {
    private Long wId;
    private String nickname;
    private String creationTime;
    private String wardrobeType;
    private ArrayList<Clothe> clothes;

    public Wardrobe(Long wId, String nickname, String creationTime, String wardrobeType) {
        this.wId = wId;
        this.nickname = nickname;
        this.creationTime = creationTime;
        this.wardrobeType = wardrobeType;
    }

    public ArrayList<Clothe> getClothes() {
        return clothes;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public void setWardrobeType(String wardrobeType) {
        this.wardrobeType = wardrobeType;
    }

    public Long getwId() {
        return wId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getWardrobeType() {
        return wardrobeType;
    }
}
