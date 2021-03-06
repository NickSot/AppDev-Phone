package ourwardrobemodels;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class User {
    private Long uId;
    private String nickname;
    private String password;
    private Bitmap avatar;
    private String OAuthToken;
    private String gender;
    private ArrayList<Wardrobe> wardrobes;

    public User(Long uId, String nickname, String password, Bitmap avatar, String OAuthToken, String gender) {
        this.uId = uId;
        this.nickname = nickname;
        this.password = password;
        this.avatar = avatar;
        this.OAuthToken = OAuthToken;
        this.gender = gender;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public void setWardrobes(ArrayList<Wardrobe> wardrobes) {
        this.wardrobes = wardrobes;
    }

    public Long getId() {
        return uId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getOAuthToken() {
        return OAuthToken;
    }

    public ArrayList<Wardrobe> getWardrobes() {
        return wardrobes;
    }

    public String getGender() {
        return gender;
    }
}
