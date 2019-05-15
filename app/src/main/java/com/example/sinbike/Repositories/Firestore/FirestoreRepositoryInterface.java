package com.example.sinbike.Repositories.Firestore;

import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.Repositories.common.Model;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.android.gms.tasks.Task;

public interface FirestoreRepositoryInterface<T extends Model> {

    Task<Void> create(T obj);
    QueryLiveData<T> readAll();
    MutableLiveData<Resource<Boolean>> update(String id, T obj);
    MutableLiveData<Resource<Boolean>> delete(String id);
}