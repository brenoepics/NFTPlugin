package io.github.brenoepics.util

import com.eu.habbo.Emulator

object Database {
    @JvmStatic
    fun loadTexts() {
        // Load texts
        Emulator.getTexts().register("commands.description.cmd_update_looks", ":update_looks")
        Emulator.getTexts().register("commands.keys.cmd_update_looks", "update_looks")
        Emulator.getTexts()
            .register("commands.cmd_update_looks.successfully", "Successfully updated NFT Plugin!")
        Emulator.getTexts().register("commands.cmd_update_looks.error", "Oops, Something went wrong!")
    }
}
