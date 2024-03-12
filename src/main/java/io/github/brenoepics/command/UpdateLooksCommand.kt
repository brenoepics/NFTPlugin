package io.github.brenoepics.command

import io.github.brenoepics.NFTAvatar
import com.eu.habbo.Emulator
import com.eu.habbo.habbohotel.commands.Command
import com.eu.habbo.habbohotel.gameclients.GameClient

class UpdateLooksCommand(permission: String?, keys: Array<String?>?) : Command(permission, keys) {
    override fun handle(gameClient: GameClient, params: Array<String>): Boolean {
        val loaded = NFTAvatar.manager.load()
        gameClient.habbo.whisper(getResponse(loaded))
        return true
    }

    private fun getResponse(loaded: Boolean): String {
        val success = "commands.cmd_update_looks.successfully"
        val error = "commands.cmd_update_looks.error"
        return Emulator.getTexts().getValue(if (loaded) success else error)
    }
}
