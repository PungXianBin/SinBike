package com.example.sinbike.Repositories.common;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * A Base Model to be extended by other models to add ids.
 */
@IgnoreExtraProperties
public class Model {
    @Exclude
    public String id;

    public <T extends Model> T withId(@NonNull final String id) {
        this.id = id;
        return (T) this;
    }
}
