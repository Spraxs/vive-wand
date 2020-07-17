package nl.spraxs.vivewand.framework;

import lombok.Getter;
import nl.spraxs.vivewand.ViveWand;
import nl.spraxs.vivewand.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.vivecraft.VivePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spraxs
 * Date: 12/4/2019
 */

public class Wand {

    public Wand(Material type, String name) {
        viveWand = ViveWand.Instance;

        this.itemStack = new ItemStack(type);

        ItemMeta im = itemStack.getItemMeta();

        im.setDisplayName(name);

        this.itemStack.setItemMeta(im);
    }

    private @Getter ItemStack itemStack;

    private @Getter List<Spell> spells = new ArrayList<>();

    private ViveWand viveWand;

    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    public void removeSpell(Spell spell) {
        spells.remove(spell);
    }

    public Spell nextSpell() {
        if (spells.isEmpty()) return null;

        int index = ItemUtil.getPersistentData(viveWand, itemStack, "spell", PersistentDataType.INTEGER);

        if (index == spells.size()) {
            index = 0;
        } else {
            index++;
        }

        return spells.get(index);
    }

    public void castSpell(VivePlayer vivePlayer, World world) {
        if (spells.isEmpty()) return;

        int index = ItemUtil.getPersistentData(viveWand, itemStack, "spell", PersistentDataType.INTEGER);

        spells.get(index).onCast(vivePlayer, world);
    }


    public void save() {
        ItemUtil.setPersistentData(ViveWand.Instance, this.itemStack, "wand", PersistentDataType.INTEGER, viveWand.getWands().size());
        ItemUtil.setPersistentData(ViveWand.Instance, this.itemStack, "spell", PersistentDataType.INTEGER, 0);

        viveWand.getWands().put(viveWand.getWands().size(), this);
    }
}
