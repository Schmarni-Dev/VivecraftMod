package org.vivecraft.client_vr.provider.control;

import net.minecraft.client.KeyMapping;

import java.util.ArrayList;

import org.vivecraft.client.VivecraftVRMod;

public class VRInputActionSet {
    public static final VRInputActionSet INGAME = new VRInputActionSet("/actions/ingame", "vivecraft.actionset.ingame",
            "leftright", false);
    public static final VRInputActionSet GUI = new VRInputActionSet("/actions/gui", "vivecraft.actionset.gui",
            "leftright", false);
    public static final VRInputActionSet GLOBAL = new VRInputActionSet("/actions/global", "vivecraft.actionset.global",
            "leftright", false);
    // MOD("/actions/mod", "vivecraft.actionset.mod", "leftright", false),
    public static final VRInputActionSet CONTEXTUAL = new VRInputActionSet("/actions/contextual",
            "vivecraft.actionset.contextual", "single", false);
    public static final VRInputActionSet KEYBOARD = new VRInputActionSet("/actions/keyboard",
            "vivecraft.actionset.keyboard", "single", true);
    public static final VRInputActionSet MIXED_REALITY = new VRInputActionSet("/actions/mixedreality",
            "vivecraft.actionset.mixedReality", "single", true);
    public static final VRInputActionSet TECHNICAL = new VRInputActionSet("/actions/technical",
            "vivecraft.actionset.technical", "leftright", true);

    public final String name;
    public final String localizedName;
    public final String usage;
    public final boolean advanced;
    public static ArrayList<VRInputActionSet> modded_sets = new ArrayList<>();
    int actions;

    VRInputActionSet(String name, String localizedName, String usage, boolean advanced) {
        this.name = name;
        this.localizedName = localizedName;
        this.usage = usage;
        this.advanced = advanced;
        this.actions = 0;
    }

    public static VRInputActionSet fromKeyBinding(KeyMapping keyBinding) {
        String s = keyBinding.getCategory();

        switch (s) {
            case "vivecraft.key.category.gui":
                return GUI;

            case "vivecraft.key.category.climbey":
                return CONTEXTUAL;

            case "vivecraft.key.category.keyboard":
                return KEYBOARD;

            default:
                return VivecraftVRMod.INSTANCE.isModBinding(keyBinding) ? get_free_modded_set() : INGAME;
        }
    }

    static VRInputActionSet get_free_modded_set() {
        for (var set : modded_sets) {
            if (set.actions < 255) {
                set.actions += 1;
                return set;
            }
        }
        var set = new VRInputActionSet("/actions/mod_" + String.valueOf(modded_sets.size()),
                "vivecraft.actionset.mod_" + String.valueOf(modded_sets.size()),
                "leftright", false);
        set.actions += 1;
        modded_sets.add(set);
        return set;
    }

    public static ArrayList<VRInputActionSet> values() {
        var list = new ArrayList<VRInputActionSet>();
        list.add(INGAME);
        list.add(GUI);
        list.add(GLOBAL);
        list.add(CONTEXTUAL);
        list.add(KEYBOARD);
        list.add(MIXED_REALITY);
        list.add(TECHNICAL);
        list.addAll(modded_sets);
        return list;
    }
}
