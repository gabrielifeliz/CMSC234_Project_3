package com.zybooks.gameemulator;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlayerDao {

    @Query("SELECT * FROM players ORDER BY player_name")
    public List<Player> getPlayers();

    @Query("SELECT player_name FROM players ORDER BY player_name")
    public List<String> getPlayerNames();

    @Query("SELECT * FROM players ORDER BY wins DESC")
    public List<Player> getPlayersMostWinsFirst();

    @Query("SELECT * FROM players ORDER BY losses DESC")
    public List<Player> getPlayersMostLossesFirst();

    @Query("SELECT * FROM players WHERE player_name = :playerName")
    public Player getPlayer(String playerName);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertPlayer(Player player);

    @Update
    public void updatePlayer(Player player);

    @Delete
    public void deletePlayer(Player player);

}
