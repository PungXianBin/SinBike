package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.List;

public class Fault extends Model {

    private List<String> image;
    private String description;
    private @ServerTimestamp Timestamp dateOfReporting;
    private String category;
    private String accountId;
    private String bicycleId;

    public Fault() {
    }

    public Fault(List<String> image, String description, Timestamp dateOfReporting, String category, String accountId, String bicycleId) {
        this.image = image;
        this.description = description;
        this.dateOfReporting = dateOfReporting;
        this.category = category;
        this.accountId = accountId;
        this.bicycleId = bicycleId;
    }

    public String getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(String bicycleId) {
        this.bicycleId = bicycleId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateOfReporting() {
        return dateOfReporting;
    }

    public void setDateOfReporting(Timestamp dateOfReporting) {
        this.dateOfReporting = dateOfReporting;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
