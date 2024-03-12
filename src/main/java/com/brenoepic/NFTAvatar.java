package com.brenoepic;

import static com.brenoepic.clothes.ClothingUtility.checkUpdate;
import static com.brenoepic.util.Database.loadTexts;

import com.brenoepic.clothes.ClothingManager;
import com.brenoepic.command.UpdateLooksCommand;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.users.UserEnterRoomEvent;
import com.eu.habbo.plugin.events.users.UserSavedLookEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class NFTAvatar extends HabboPlugin implements EventListener {
  public static final ClothingManager manager = new ClothingManager();

  public void onEnable() {
    Emulator.getPluginManager().registerEvents(this, this);
    log.info("[NFTAvatarPlugin] was successfully loaded! Discord: brenoepic");
  }

  public void onDisable() {
    // empty, not needed
  }

  public boolean hasPermission(Habbo habbo, String s) {
    return false;
  }

  @EventHandler
  public void onEmulatorLoaded(EmulatorLoadedEvent event) {
    loadTexts();

    // Commands
    String[] keys = Emulator.getTexts().getValue("commands.keys.cmd_update_looks").split(";");
    CommandHandler.addCommand(new UpdateLooksCommand("cmd_update_looks", keys));
  }

  @EventHandler
  public void onUserEnterRoom(UserEnterRoomEvent event) {
    if (event.habbo == null || event.room == null) return;
    checkUpdate(event.habbo, event.room);
  }

  @EventHandler
  public void onUserSaveLookEvent(UserSavedLookEvent event) {
    if (event.habbo == null || event.habbo.getHabboInfo().getCurrentRoom() == null) return;
    checkUpdate(event.habbo, event.habbo.getHabboInfo().getCurrentRoom(), event.newLook);
  }

  public static void main(String[] args) {
    System.out.println("Hello world!");
  }
}
