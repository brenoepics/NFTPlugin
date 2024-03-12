package io.github.brenoepics

import io.github.brenoepics.clothes.ClothingManager
import io.github.brenoepics.clothes.ClothingUtility.Companion.checkUpdate
import io.github.brenoepics.command.UpdateLooksCommand
import io.github.brenoepics.util.Database.loadTexts
import com.eu.habbo.Emulator
import com.eu.habbo.habbohotel.commands.CommandHandler
import com.eu.habbo.habbohotel.users.Habbo
import com.eu.habbo.plugin.EventHandler
import com.eu.habbo.plugin.EventListener
import com.eu.habbo.plugin.HabboPlugin
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent
import com.eu.habbo.plugin.events.users.UserEnterRoomEvent
import com.eu.habbo.plugin.events.users.UserSavedLookEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
class NFTAvatar : HabboPlugin(), EventListener {
    override fun onEnable() {
        Emulator.getPluginManager().registerEvents(this, this)
        log.info("[NFTAvatarPlugin] was successfully loaded! Discord: brenoepic")
    }

    override fun onDisable() {
        // empty, not needed
    }

    override fun hasPermission(habbo: Habbo, s: String): Boolean {
        return false
    }

    @EventHandler
    fun onEmulatorLoaded(event: EmulatorLoadedEvent?) {
        loadTexts()

        // Commands
        val keys: Array<String?> = Emulator.getTexts().getValue("commands.keys.cmd_update_looks").split(";".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()
        CommandHandler.addCommand(UpdateLooksCommand("cmd_update_looks", keys))
    }

    @EventHandler
    fun onUserEnterRoom(event: UserEnterRoomEvent) {
        if (event.habbo == null || event.room == null) return
        checkUpdate(event.habbo, event.room)
    }

    @EventHandler
    fun onUserSaveLookEvent(event: UserSavedLookEvent) {
        if (event.habbo == null || event.habbo.habboInfo.currentRoom == null) return
        checkUpdate(event.habbo, event.habbo.habboInfo.currentRoom, event.newLook)
    }

    companion object {
        val manager: ClothingManager = ClothingManager()
        private val log: Logger = LoggerFactory.getLogger(NFTAvatar::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            println("Hello world!")
        }
    }
}
