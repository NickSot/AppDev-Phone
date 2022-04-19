package ourwardrobemodels;

import android.graphics.Bitmap;

public class Clothe {
    private Long cId;
    private String clotheType;
    private Bitmap image;
    private Long ogId;
    private String originalUser;

    public String getOriginalUser() {
        return originalUser;
    }

    public Clothe(Long cId, String clotheType, Bitmap image, Long ogId, String ogUser) {
        this.cId = cId;
        this.clotheType = clotheType;
        this.image = image;
        this.ogId = ogId;
        this.originalUser = ogUser;
    }

    public void setClotheType(String clotheType) {
        this.clotheType = clotheType;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setOgId(Long ogId) {
        this.ogId = ogId;
    }

    public Long getcId() {
        return cId;
    }

    public String getClotheType() {
        return clotheType;
    }

    public Bitmap getImage() {
        return image;
    }

    public Long getOgId() {
        return ogId;
    }
}
