package com.brenoepic;

import com.brenoepic.clothes.Clothing;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class SpecialLooks extends HabboPlugin implements EventListener {

    SpecialLooks INSTANCE;
    public static final Logger LOGGER = LoggerFactory.getLogger(SpecialLooks.class);
    public static ClothingManager clothings;

    public void onEnable() {
        Emulator.getPluginManager().registerEvents(this, this);
        INSTANCE = this;
        LOGGER.info("[Special-Looks 1.0] was successfully Loaded! Discord: BrenoEpic#9671");
    }

    public void onDisable() {

    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    @EventHandler
    public void onEmulatorLoaded(EmulatorLoadedEvent event) {
        clothings = new ClothingManager();
        // Load texts
        Emulator.getTexts().register("commands.description.cmd_update_looks", ":update_looks");
        Emulator.getTexts().register("commands.keys.cmd_update_looks", "update_looks");
        Emulator.getTexts().register("commands.cmd_update_looks.successfully", "You have successfully updated special looks!");
        Emulator.getTexts().register("commands.cmd_update_looks.error", "Oops, Something went wrong!");


        // Commands
        CommandHandler.addCommand(new UpdateLooksCommand("cmd_update_looks", Emulator.getTexts().getValue("commands.keys.cmd_update_looks").split(";")));

    }

    @EventHandler
    public void onUserEnterRoom(UserEnterRoomEvent event) {
        if (event.habbo != null && event.room != null) {
            Clothing clothing;
            clothing = SpecialLooks.getSpecialLooks().getClothing(event.habbo.getHabboInfo().getLook());
            if (clothing != null) {
                Clothing finalClothing = clothing;
                Emulator.getThreading().run(() -> {
                    event.room.giveEffect(event.habbo, finalClothing.getEffect(), -1);
                }, 500);
                return;
            }

            String[] newLookParts = event.habbo.getHabboInfo().getLook().split(Pattern.quote("."));

            for (String Part : newLookParts) {
                if (Part.contains("-")) {
                    String[] data = Part.split(Pattern.quote("-"));
                    int setId = Integer.parseInt(data.length >= 2 ? data[1] : "-1");
                    if (setId > 0) {
                        clothing = SpecialLooks.getSpecialLooks().getClothingBySetId(setId);
                        if (clothing != null) {
                            Clothing finalClothing1 = clothing;
                            Emulator.getThreading().run(() -> {
                                event.room.giveEffect(event.habbo, finalClothing1.getEffect(), -1);
                            }, 500);
                        }
                    }
                }
            }

        }
    }

    @EventHandler
    public void onUserSaveLookEvent(UserSavedLookEvent event) {
        if (event.habbo == null || event.habbo.getHabboInfo().getCurrentRoom() == null) return;
        Clothing clothing;
        clothing = SpecialLooks.getSpecialLooks().getClothing(event.newLook);
        if (clothing != null) {
            Clothing finalClothing = clothing;
            Emulator.getThreading().run(() -> {
                event.habbo.getHabboInfo().getCurrentRoom().giveEffect(event.habbo, finalClothing.getEffect(), -1);
            }, 500);
            return;
        }

        String[] newLookParts = event.newLook.split(Pattern.quote("."));
        for (String Part : newLookParts) {
            if (Part.contains("-")) {
                String[] data = Part.split(Pattern.quote("-"));
                int setId = Integer.parseInt(data.length >= 2 ? data[1] : "-1");
                if (setId > 0) {
                    clothing = SpecialLooks.getSpecialLooks().getClothingBySetId(setId);
                    if (clothing != null) {
                        Clothing finalClothing1 = clothing;
                        Emulator.getThreading().run(() -> {
                            event.habbo.getHabboInfo().getCurrentRoom().giveEffect(event.habbo, finalClothing1.getEffect(), -1);
                        }, 500);
                        return;
                    }
                }
            }
        }
        if (SpecialLooks.getSpecialLooks().getEffectList().contains(event.habbo.getRoomUnit().getEffectId())) {
            Emulator.getThreading().run(() -> {
                event.habbo.getHabboInfo().getCurrentRoom().giveEffect(event.habbo, event.habbo.getHabboInfo().getRank().getRoomEffect(), -1);
            }, 500);
        }
    }

    public static ClothingManager getSpecialLooks() {
        return clothings;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

}
