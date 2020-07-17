package nl.spraxs.vivewand.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

/**
 * Created by Spraxs
 * Date: 24-7-2019
 */

public class ItemUtil {

    /*
     * * PersistentData * *
     */

    public static <T> void setPersistentData(Plugin pluginInstance, ItemStack itemStack, String key, PersistentDataType<T, T> type, T t) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(pluginInstance, key);
        itemMeta.getPersistentDataContainer().set(namespacedKey, type, t);

        itemStack.setItemMeta(itemMeta);
    }

    public static <T> boolean hasPersistentData(Plugin pluginInstance, ItemStack itemStack, String key, PersistentDataType<T, T> type) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(pluginInstance, key);
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        return container.has(namespacedKey , type);
    }

    public static <T> T getPersistentData(Plugin pluginInstance, ItemStack itemStack, String key, PersistentDataType<T, T> type) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(pluginInstance, key);
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(namespacedKey, type)) {
            return container.get(namespacedKey, type);
        }

        return (T) null;
    }

    public static void removePersistentData(Plugin pluginInstance, ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key_ = new NamespacedKey(pluginInstance, key);

        container.remove(key_);
        itemStack.setItemMeta(meta);
    }
}
