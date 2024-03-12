package io.github.brenoepics.clothes

import io.github.brenoepics.NFTAvatar
import com.eu.habbo.Emulator
import com.eu.habbo.habbohotel.rooms.Room
import com.eu.habbo.habbohotel.users.Habbo
import java.util.*
import java.util.regex.Pattern

class ClothingUtility private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun checkUpdate(habbo: Habbo, room: Room, look: String = habbo.habboInfo.look) {
            val clothing = NFTAvatar.manager.getClothing(look)
            if (clothing != null) {
                updateEffect(habbo, room, clothing)
                return
            }

            checkLookParts(habbo, room, look)
        }

        fun checkLookParts(habbo: Habbo?, room: Room, look: String) {
            val newLookParts = look.split(Pattern.quote(".").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            Arrays.stream(newLookParts).forEach { part: String -> handlePart(habbo, room, part) }
        }

        fun handlePart(habbo: Habbo?, room: Room, part: String) {
            if (!part.contains("-")) return

            val data = part.split(Pattern.quote("-").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val setId = (if (data.size >= 2) data[1] else "-1").toInt()
            if (setId <= 0) {
                return
            }

            val clothing = NFTAvatar.manager.getClothingBySetId(setId)
            if (clothing != null) {
                updateEffect(habbo, room, clothing)
            }
        }

        fun updateEffect(habbo: Habbo?, room: Room, clothing: Clothing) {
            val giveEffect = Runnable { room.giveEffect(habbo, clothing.effect, -1) }
            Emulator.getThreading().run(giveEffect, 500)
        }
    }
}
