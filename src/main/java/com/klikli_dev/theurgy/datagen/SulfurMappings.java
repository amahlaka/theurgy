// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgy.datagen;

import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurItem;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurTier;
import com.klikli_dev.theurgy.content.item.sulfur.AlchemicalSulfurType;
import com.klikli_dev.theurgy.registry.SulfurRegistry;

import java.util.List;

public class SulfurMappings {
    private static List<AlchemicalSulfurItem> GEMS_ABUNDANT;

    private static List<AlchemicalSulfurItem> GEMS_COMMON;

    private static List<AlchemicalSulfurItem> GEMS_RARE;

    private static List<AlchemicalSulfurItem> GEMS_PRECIOUS;

    private static List<AlchemicalSulfurItem> METALS_ABUNDANT;


    private static List<AlchemicalSulfurItem> METALS_COMMON;


    private static List<AlchemicalSulfurItem> METALS_RARE;

    private static List<AlchemicalSulfurItem> METALS_PRECIOUS;

    private static List<AlchemicalSulfurItem> OTHER_MINERALS_ABUNDANT;
    private static List<AlchemicalSulfurItem> OTHER_MINERALS_COMMON;

    private static List<AlchemicalSulfurItem> OTHER_MINERALS_RARE;
    private static List<AlchemicalSulfurItem> OTHER_MINERALS_PRECIOUS;

    public static List<AlchemicalSulfurItem> gemsAbundant() {
        if (GEMS_ABUNDANT != null) {
            return GEMS_ABUNDANT;
        }

        GEMS_ABUNDANT = find(AlchemicalSulfurType.GEMS, AlchemicalSulfurTier.ABUNDANT);

        return GEMS_ABUNDANT;
    }

    public static List<AlchemicalSulfurItem> gemsCommon() {
        if (GEMS_COMMON != null) {
            return GEMS_COMMON;
        }

        GEMS_COMMON = find(AlchemicalSulfurType.GEMS, AlchemicalSulfurTier.COMMON);

        return GEMS_COMMON;
    }

    public static List<AlchemicalSulfurItem> gemsRare() {
        if (GEMS_RARE != null) {
            return GEMS_RARE;
        }

        GEMS_RARE = find(AlchemicalSulfurType.GEMS, AlchemicalSulfurTier.RARE);

        return GEMS_RARE;
    }

    public static List<AlchemicalSulfurItem> gemsPrecious() {
        if (GEMS_PRECIOUS != null) {
            return GEMS_PRECIOUS;
        }

        GEMS_PRECIOUS = find(AlchemicalSulfurType.GEMS, AlchemicalSulfurTier.PRECIOUS);

        return GEMS_PRECIOUS;
    }

    public static List<AlchemicalSulfurItem> metalsAbundant() {
        if (METALS_ABUNDANT != null) {
            return METALS_ABUNDANT;
        }

        METALS_ABUNDANT = find(AlchemicalSulfurType.METALS, AlchemicalSulfurTier.ABUNDANT);
        return METALS_ABUNDANT;
    }

    public static List<AlchemicalSulfurItem> metalsCommon() {
        if (METALS_COMMON != null) {
            return METALS_COMMON;
        }

        METALS_COMMON = find(AlchemicalSulfurType.METALS, AlchemicalSulfurTier.COMMON);

        return METALS_COMMON;
    }

    public static List<AlchemicalSulfurItem> metalsRare() {
        if (METALS_RARE != null) {
            return METALS_RARE;
        }

        METALS_RARE = find(AlchemicalSulfurType.METALS, AlchemicalSulfurTier.RARE);

        return METALS_RARE;
    }

    public static List<AlchemicalSulfurItem> metalsPrecious() {
        if (METALS_PRECIOUS != null) {
            return METALS_PRECIOUS;
        }

        METALS_PRECIOUS = find(AlchemicalSulfurType.METALS, AlchemicalSulfurTier.PRECIOUS);

        return METALS_PRECIOUS;
    }

    public static List<AlchemicalSulfurItem> otherMineralsAbundant() {
        if (OTHER_MINERALS_ABUNDANT != null) {
            return OTHER_MINERALS_ABUNDANT;
        }

        OTHER_MINERALS_ABUNDANT = find(AlchemicalSulfurType.OTHER_MINERALS, AlchemicalSulfurTier.ABUNDANT);

        return OTHER_MINERALS_ABUNDANT;
    }

    public static List<AlchemicalSulfurItem> otherMineralsCommon() {
        if (OTHER_MINERALS_COMMON != null) {
            return OTHER_MINERALS_COMMON;
        }

        OTHER_MINERALS_COMMON = find(AlchemicalSulfurType.OTHER_MINERALS, AlchemicalSulfurTier.COMMON);

        return OTHER_MINERALS_COMMON;
    }

    public static List<AlchemicalSulfurItem> otherMineralsRare() {
        if (OTHER_MINERALS_RARE != null) {
            return OTHER_MINERALS_RARE;
        }

        OTHER_MINERALS_RARE = find(AlchemicalSulfurType.OTHER_MINERALS, AlchemicalSulfurTier.RARE);

        return OTHER_MINERALS_RARE;
    }

    public static List<AlchemicalSulfurItem> otherMineralsPrecious() {
        if (OTHER_MINERALS_PRECIOUS != null) {
            return OTHER_MINERALS_PRECIOUS;
        }

        OTHER_MINERALS_PRECIOUS = find(AlchemicalSulfurType.OTHER_MINERALS, AlchemicalSulfurTier.PRECIOUS);

        return OTHER_MINERALS_PRECIOUS;
    }

    private static List<AlchemicalSulfurItem> find(AlchemicalSulfurType type, AlchemicalSulfurTier tier) {
        return SulfurRegistry.SULFURS.getEntries().stream()
                .map(entry -> entry.get())
                .filter(entry -> entry instanceof AlchemicalSulfurItem)
                .map(entry -> (AlchemicalSulfurItem) entry)
                .filter(entry -> entry.type() == type)
                .filter(entry -> entry.tier == tier)
                .toList();
    }
}
