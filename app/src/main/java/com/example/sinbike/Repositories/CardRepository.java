package com.example.sinbike.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Card;

import java.util.List;

public class CardRepository {

    private static final String TAG = "CardRepository";

    private CardDao cardDao;
    private LiveData<List<Card>> cardList;

    public CardRepository(Application application){
        CardRoomDatabase db = CardRoomDatabase.getInstance(application);

        cardDao = db.cardDao();
        this.cardList = cardDao.get();
    }

    public LiveData<List<Card>> get(){
        return this.cardList;
    }

    public void insert(Card card){
        new insertAsyncTask(this.cardDao).execute(card);
    }

    private static class insertAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao mAsyncTaskDao;

        public insertAsyncTask(CardDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Card... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
