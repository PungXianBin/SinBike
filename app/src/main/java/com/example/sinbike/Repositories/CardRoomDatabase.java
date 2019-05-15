package com.example.sinbike.Repositories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sinbike.POJO.Card;

@Database(entities = {Card.class}, version = 2, exportSchema = false)
public abstract class CardRoomDatabase extends RoomDatabase {

    public abstract CardDao cardDao();
    private static CardRoomDatabase INSTANCE;

    public static CardRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CardRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardRoomDatabase.class, "card_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
