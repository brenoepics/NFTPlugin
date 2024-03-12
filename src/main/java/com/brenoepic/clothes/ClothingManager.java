package com.brenoepic.clothes;

import com.eu.habbo.Emulator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClothingManager {
  private final HashMap<Integer, Clothing> clothes;

  public ClothingManager() {
    this.clothes = new HashMap<>();
    this.load();
  }

  public boolean load() {
    this.dispose();
    try (final Connection connection = Emulator.getDatabase().getDataSource().getConnection();
        final PreparedStatement statement =
            connection.prepareStatement("SELECT * FROM `special_looks`");
        final ResultSet set = statement.executeQuery()) {
      while (set.next()) {
        final Clothing cloth = new Clothing(set);
        this.clothes.put(set.getInt("id"), cloth);
      }
    } catch (SQLException e) {
      log.error("[NFTAvatarPlugin]", e);
      return false;
    }
    log.info("[NFTAvatarPlugin] Loaded {} NFT avatars successfully!", this.clothes.size());
    return true;
  }

  public void dispose() {
    this.clothes.clear();
  }

  public Clothing getClothingBySetId(final int setId) {
    return this.clothes.values().stream()
        .filter(clothing -> filterSetId(setId, clothing))
        .findFirst()
        .orElse(null);
  }

  private static boolean filterSetId(int setId, Clothing clothing) {
    return clothing.getSet() != null
        && clothing.getType().equals("setid")
        && Integer.parseInt(clothing.getSet()) == setId;
  }

  public Clothing getClothing(final String figure) {
    return this.clothes.values().stream()
        .filter(clothing -> filterFigure(figure, clothing))
        .findFirst()
        .orElse(null);
  }

  private static boolean filterFigure(String figure, Clothing clothing) {
    return clothing.getSet() != null
        && clothing.getType().equals("figure")
        && clothing.getSet().equals(figure);
  }

  public List<Integer> getEffectList() {
    return this.clothes.values().stream().map(Clothing::getEffect).collect(Collectors.toList());
  }
}
