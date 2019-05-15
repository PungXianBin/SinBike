package com.example.sinbike.Repositories.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public final class CompletionLiveData extends MutableLiveData<Resource<Object>> implements OnCompleteListener<Void> {

    @Override
    public final void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            setValue(new Resource<>(true));
        } else {
            setValue(new Resource<>(task.getException()));
        }
    }
}
