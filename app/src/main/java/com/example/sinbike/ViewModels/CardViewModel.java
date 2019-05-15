package com.example.sinbike.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Card;
import com.example.sinbike.Services.CardService;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private static final String TAG = "CardViewModel";
    private CardService cardService;

    public CardViewModel(Application application){
        super(application);
        this.cardService = new CardService(application);
    }

    public LiveData<List<Card>> getCards(){
        return this.cardService.getCard();
    }

    public void addCard(Card card){
        this.cardService.insertCard(card);
    }
}
