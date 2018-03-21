
package com.mindnotix.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image  implements Serializable {

    @SerializedName("pg_gallery_id")
    @Expose
    private String pgGalleryId;
    @SerializedName("pg_name")
    @Expose
    private String pgName;
    @SerializedName("pg_link")
    @Expose
    private String pgLink;
    @SerializedName("pg_type")
    @Expose
    private String pgType;
    @SerializedName("pg_date")
    @Expose
    private String pgDate;

    public String getPgGalleryId() {
        return pgGalleryId;
    }

    public void setPgGalleryId(String pgGalleryId) {
        this.pgGalleryId = pgGalleryId;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgLink() {
        return pgLink;
    }

    public void setPgLink(String pgLink) {
        this.pgLink = pgLink;
    }

    public String getPgType() {
        return pgType;
    }

    public void setPgType(String pgType) {
        this.pgType = pgType;
    }

    public String getPgDate() {
        return pgDate;
    }

    public void setPgDate(String pgDate) {
        this.pgDate = pgDate;
    }

}
