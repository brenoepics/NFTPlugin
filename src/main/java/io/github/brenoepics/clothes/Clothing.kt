package io.github.brenoepics.clothes

import java.sql.ResultSet

class Clothing
    (result: ResultSet) {
    val id: Int = result.getInt("id")
    val type: String = result.getString("type")
    var set: String? = null
    val effect: Int

    init {
        if (this.type == "figure") this.set = result.getString("figure")
        else this.set = result.getString("setid")
        this.effect = result.getInt("effect")
    }
}
