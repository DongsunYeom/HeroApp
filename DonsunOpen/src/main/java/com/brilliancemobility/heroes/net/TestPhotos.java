package com.brilliancemobility.heroes.net;

/**
 * Created by dongsun on 13/11/16.
 */

public class TestPhotos {
    public String id;
    public String owner;
    public String secret;
    public String server;
    public int farm;
    public String title;
    public String ispublic;
    public String isfriend;
    public String isfamily;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIspublic() {
        return ispublic;
    }

    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
    }

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public String getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(String isfamily) {
        this.isfamily = isfamily;
    }

    public String getFlickrImgUrl(final TestEnum.ImageSize imageSize) {
        StringBuilder sb = new StringBuilder();

        sb.append("http://farm");
        sb.append(getFarm());
        sb.append(".static.flickr.com/");
        sb.append(getServer());
        sb.append("/");
        sb.append(getId());
        sb.append("_");
        sb.append(getSecret());
        sb.append(imageSize.getSuffix());
        sb.append(".jpg");

        return sb.toString();
    }
}
