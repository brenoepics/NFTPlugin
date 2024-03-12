package com.brenoepic.clothes;

import com.brenoepic.NFTAvatar;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ClothingUtility {
  private ClothingUtility() {
    throw new IllegalStateException("Utility class");
  }

  public static void checkUpdate(Habbo habbo, Room room) {
    checkUpdate(habbo, room, habbo.getHabboInfo().getLook());
  }

  public static void checkUpdate(Habbo habbo, Room room, String look) {
    Clothing clothing = NFTAvatar.manager.getClothing(look);
    if (clothing != null) {
      updateEffect(habbo, room, clothing);
      return;
    }

    checkLookParts(habbo, room, look);
  }

  public static void checkLookParts(Habbo habbo, Room room, String look) {
    String[] newLookParts = look.split(Pattern.quote("."));
    Arrays.stream(newLookParts).forEach(part -> handlePart(habbo, room, part));
  }

  public static void handlePart(Habbo habbo, Room room, String part) {
    if (!part.contains("-")) return;

    String[] data = part.split(Pattern.quote("-"));
    int setId = Integer.parseInt(data.length >= 2 ? data[1] : "-1");
    if (setId <= 0) {
      return;
    }

    Clothing clothing = NFTAvatar.manager.getClothingBySetId(setId);
    if (clothing != null) {
      updateEffect(habbo, room, clothing);
    }
  }

  public static void updateEffect(Habbo habbo, Room room, Clothing clothing) {
    Runnable giveEffect = () -> room.giveEffect(habbo, clothing.getEffect(), -1);
    Emulator.getThreading().run(giveEffect, 500);
  }
}
