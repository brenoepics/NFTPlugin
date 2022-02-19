package com.brenoepic.clothes;


import com.brenoepic.SpecialLooks;
import com.eu.habbo.Emulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ClothingManager
{
    private final Map<Integer, Clothing> clothes;

    public ClothingManager() {
        this.clothes = new HashMap<>();
        this.load();
    }

    public boolean load() {
        this.dispose();
        try (final Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM `special_looks`");
             final ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                final Clothing cloth = new Clothing(set);
                this.clothes.put(set.getInt("id"), cloth);
            }
        }
        catch (SQLException e) {
            SpecialLooks.LOGGER.error("[Special-Looks]", e);
            return false;
        }
        SpecialLooks.LOGGER.info("[Special-Looks] Loaded " + this.clothes.size() + " Special Looks successfully!");
        return true;
    }

    public void dispose() {
        this.clothes.clear();
    }

    public Clothing getClothingBySetId(final int setId) {
        return this.clothes.values().stream().filter(clothing -> clothing.getSet() != null && clothing.getType().equals("setid") && Integer.parseInt(clothing.getSet()) == setId).findFirst().orElse(null);
    }

    public Clothing getClothing(final String figure) {
        return this.clothes.values().stream().filter(clothing ->
                        clothing.getSet() != null &&
                        clothing.getType().equals("figure") &&
                        clothing.getSet().equals(figure))
                .findFirst().orElse(null);
    }

    public List<Integer> getEffectList(){
        List<Integer> effects = new ArrayList<>();
        this.clothes.values().forEach(clothing -> effects.add(clothing.getEffect()));
        return effects;
    }

}
