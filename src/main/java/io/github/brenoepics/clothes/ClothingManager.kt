package io.github.brenoepics.clothes

import com.eu.habbo.Emulator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.SQLException
import java.util.stream.Collectors

class ClothingManager {
    private val clothes = HashMap<Int, Clothing?>()

    init {
        this.load()
    }

    fun load(): Boolean {
        this.dispose()
        try {
            Emulator.getDatabase().dataSource.connection.use { connection ->
                connection.prepareStatement("SELECT * FROM `special_looks`").use { statement ->
                    statement.executeQuery().use { set ->
                        while (set.next()) {
                            clothes[set.getInt("id")] = Clothing(set)
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            log.error("[NFTAvatarPlugin]", e)
            return false
        }
        log.info(
            "[NFTAvatarPlugin] Loaded {} NFT avatars successfully!",
            clothes.size
        )
        return true
    }

    fun dispose() {
        clothes.clear()
    }

    fun getClothingBySetId(setId: Int): Clothing? {
        return clothes.values.stream()
            .filter { clothing: Clothing? -> filterSetId(setId, clothing) }
            .findFirst()
            .orElse(null)
    }

    fun getClothing(figure: String): Clothing? {
        return clothes.values.stream()
            .filter { clothing: Clothing? -> filterFigure(figure, clothing) }
            .findFirst()
            .orElse(null)
    }

    val effectList: List<Int>
        get() = clothes.values.stream().map { obj: Clothing? -> obj!!.effect }.collect(Collectors.toList())

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ClothingManager::class.java)
        private fun filterSetId(setId: Int, clothing: Clothing?): Boolean {
            return clothing!!.set != null && clothing.type == "setid" && clothing.set!!.toInt() == setId
        }

        private fun filterFigure(figure: String, clothing: Clothing?): Boolean {
            return clothing!!.set != null && clothing.type == "figure" && clothing.set == figure
        }
    }
}
