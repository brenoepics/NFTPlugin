package com.brenoepic.command;

import com.brenoepic.NFTAvatar;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;

public class UpdateLooksCommand extends Command {

  public UpdateLooksCommand(String permission, String[] keys) {
    super(permission, keys);
  }

  @Override
  public boolean handle(GameClient gameClient, String[] params) {
    boolean loaded = NFTAvatar.manager.load();
    gameClient.getHabbo().whisper(getResponse(loaded));
    return true;
  }

  private String getResponse(boolean loaded) {
    String success = "commands.cmd_update_looks.successfully";
    String error = "commands.cmd_update_looks.error";
    return Emulator.getTexts().getValue(loaded ? success : error);
  }
}
