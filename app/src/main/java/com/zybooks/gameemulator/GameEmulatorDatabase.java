package com.zybooks.gameemulator;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Player.class}, version = 1)
public abstract class GameEmulatorDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "gameemulator.db";

    private static GameEmulatorDatabase gameEmulatorDatabase;

    // Singleton
    public static GameEmulatorDatabase getInstance(Context context) {
        if (gameEmulatorDatabase == null) {
            gameEmulatorDatabase = Room.databaseBuilder(context, GameEmulatorDatabase.class,
                    DATABASE_NAME).allowMainThreadQueries().build();
        }
        return gameEmulatorDatabase;
    }

    public abstract PlayerDao playerDao();
}
