package com.brenoepic.clothes;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Getter
public class Clothing
{
    private final int id;
    private final String type;
    private final String set;
    private final int effect;
    public Clothing(final ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.type = result.getString("type");
        if(Objects.equals(this.type, "figure")) this.set = result.getString("figure");
        else this.set = result.getString("setid");
        this.effect = result.getInt("effect");
    }
}
