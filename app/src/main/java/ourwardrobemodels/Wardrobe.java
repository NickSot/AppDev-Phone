package ourwardrobemodels;

import java.util.ArrayList;

public class Wardrobe {
    public class WardrobeUser {
        private String nickname;
        private Long uId;

        public WardrobeUser(Long uId, String nickname) {
            this.nickname = nickname;
            this.uId = uId;
        }

        public String getNickname() {
            return nickname;
        }

        public Long getId() {
            return uId;
        }
    }

    private Long wId;
    private String nickname;
    private String creationTime;
    private String wardrobeType;
    private Long adminId;
    private ArrayList<Clothe> clothes = new ArrayList<>();
    private ArrayList<WardrobeUser> users = new ArrayList<>();

    public Wardrobe(Long wId, String nickname, String creationTime, String wardrobeType, Long adminId) {
        this.wId = wId;
        this.nickname = nickname;
        this.creationTime = creationTime;
        this.wardrobeType = wardrobeType;
        this.adminId = adminId;
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

    public Long getAdminId() {
        return adminId;
    }

    public ArrayList<WardrobeUser> getUsers() { return users; }

    public void addUser(Long uId, String nickname) {
        users.add(new WardrobeUser(uId, nickname));
    }
}
