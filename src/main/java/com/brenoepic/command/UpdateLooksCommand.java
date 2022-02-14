package com.brenoepic.command;

import com.brenoepic.SpecialLooks;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;


public class UpdateLooksCommand extends Command {

    public UpdateLooksCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params)
    {
            boolean loaded = SpecialLooks.getSpecialLooks().load();
            if(loaded)
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.cmd_update_looks.successfully"));
            else gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.cmd_update_looks.error"));
        return true;
    }
}