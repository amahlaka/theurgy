/*
 * SPDX-FileCopyrightText: 2023 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.theurgy.datagen.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.klikli_dev.theurgy.Theurgy;
import com.klikli_dev.theurgy.TheurgyConstants;
import com.klikli_dev.theurgy.content.item.DivinationRodItem;
import com.klikli_dev.theurgy.registry.ItemRegistry;
import com.klikli_dev.theurgy.registry.ItemTagRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class ShapedRecipeProvider extends JsonRecipeProvider {

    public ShapedRecipeProvider(PackOutput packOutput) {
        super(packOutput, Theurgy.MODID, "crafting/shaped");
    }

    @Override
    void buildRecipes(BiConsumer<ResourceLocation, JsonObject> recipeConsumer) {
        //Divination Rods
        this.makeRecipe("divination_rod_t1", new RecipeBuilder(
                ItemRegistry.DIVINATION_ROD_T1.get(), 1, this.makeDivinationRodSettings(ItemRegistry.DIVINATION_ROD_T1.get()))
                .pattern(" GR")
                .pattern(" RG")
                .pattern("R  ")
                .define('G', Tags.Items.GLASS)
                .define('R', Tags.Items.RODS_WOODEN)
        );

        var amethystDivinationRodSettings = this.makeDivinationRodSettings(ItemRegistry.AMETHYST_DIVINATION_ROD.get());
        amethystDivinationRodSettings.addProperty(TheurgyConstants.Nbt.Divination.LINKED_BLOCK_ID, "minecraft:budding_amethyst");
        this.makeRecipe("amethyst_divination_rod", new RecipeBuilder(
                ItemRegistry.AMETHYST_DIVINATION_ROD.get(), 1, amethystDivinationRodSettings)
                .pattern(" GP")
                .pattern(" RG")
                .pattern("R  ")
                .define('P', Tags.Items.DYES_PURPLE)
                .define('G', Tags.Items.GLASS)
                .define('R', Tags.Items.RODS_WOODEN)
        );

        this.makeRecipe("sulfur_attuned_divination_rod_abundant", new RecipeBuilder(
                ItemRegistry.SULFUR_ATTUNED_DIVINATION_ROD_ABUNDANT.get(), 1, this.makeDivinationRodSettings(ItemRegistry.SULFUR_ATTUNED_DIVINATION_ROD_ABUNDANT.get()), "theurgy:divination_rod")
                .pattern(" GS")
                .pattern(" RG")
                .pattern("R  ")
                .define('G', Tags.Items.GLASS)
                .define('R', Tags.Items.RODS_WOODEN)
                .define('S', ItemTagRegistry.ALCHEMICAL_SULFURS_ABUNDANT)
        );
        this.makeRecipe("sulfur_attuned_divination_rod_common", new RecipeBuilder(
                ItemRegistry.SULFUR_ATTUNED_DIVINATION_ROD_COMMON.get(), 1, this.makeDivinationRodSettings(ItemRegistry.SULFUR_ATTUNED_DIVINATION_ROD_COMMON.get()), "theurgy:divination_rod")
                .pattern(" GS")
                .pattern(" RG")
                .pattern("R  ")
                .define('G', Tags.Items.GLASS)
                .define('R', Tags.Items.RODS_WOODEN)
                .define('S', ItemTagRegistry.ALCHEMICAL_SULFURS_COMMON)
        );

        this.makeRecipe("divination_rod_t2", new RecipeBuilder(
                ItemRegistry.DIVINATION_ROD_T2.get(), 1, this.makeDivinationRodSettings(ItemRegistry.DIVINATION_ROD_T2.get()))
                .pattern(" GM")
                .pattern(" AG")
                .pattern("R  ")
                .define('G', Tags.Items.GLASS)
                .define('R', Tags.Items.RODS_WOODEN)
                .define('A', Tags.Items.GEMS_AMETHYST)
                .define('M', Tags.Items.NUGGETS_GOLD)
        );
        this.makeRecipe("sulfur_attuned_divination_rod_rare", new RecipeBuilder(
                ItemRegistry.SULFUR_ATTUNED_DIVINATION_ROD_RARE.get(), 1, this.makeDivinationRodSettings(ItemRegistry.SULFUR_ATTUNED_DIVINATION_ROD_RARE.get()))
                .pattern(" GS")
                .pattern(" AG")
                .pattern("R  ")
                .define('G', Tags.Items.GLASS)
                .define('R', Tags.Items.RODS_WOODEN)
                .define('A', Tags.Items.GEMS_AMETHYST)
                .define('S', ItemTagRegistry.ALCHEMICAL_SULFURS_RARE)
        );

        this.makeRecipe("divination_rod_t3", new RecipeBuilder(
                ItemRegistry.DIVINATION_ROD_T3.get(), 1, this.makeDivinationRodSettings(ItemRegistry.DIVINATION_ROD_T3.get()))
                .pattern(" GD")
                .pattern(" QG")
                .pattern("A  ")
                .define('G', Tags.Items.GLASS)
                .define('Q', Tags.Items.GEMS_QUARTZ)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('A', Tags.Items.GEMS_AMETHYST)
        );
        this.makeRecipe("sulfur_attuned_divination_rod_precious", new RecipeBuilder(
                ItemRegistry.SULFUR_ATTUNED_DIVINATION_ROD_PRECIOUS.get(), 1, this.makeDivinationRodSettings(ItemRegistry.SULFUR_ATTUNED_DIVINATION_ROD_PRECIOUS.get()))
                .pattern(" GS")
                .pattern(" DG")
                .pattern("A  ")
                .define('G', Tags.Items.GLASS)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('A', Tags.Items.GEMS_AMETHYST)
                .define('S', ItemTagRegistry.ALCHEMICAL_SULFURS_PRECIOUS)
        );

        this.makeRecipe("divination_rod_t4", new RecipeBuilder(
                ItemRegistry.DIVINATION_ROD_T4.get(), 1, this.makeDivinationRodSettings(ItemRegistry.DIVINATION_ROD_T4.get()))
                .pattern(" GM")
                .pattern(" RG")
                .pattern("A  ")
                .define('G', Tags.Items.GLASS)
                .define('R', Tags.Items.RODS_BLAZE)
                .define('M', Tags.Items.ORES_NETHERITE_SCRAP)
                .define('A', Tags.Items.GEMS_AMETHYST)
        );

        this.makeRecipe(ItemRegistry.PYROMANTIC_BRAZIER.get(), new RecipeBuilder(
                ItemRegistry.PYROMANTIC_BRAZIER.get(), 1)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("SSS")
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('S', Tags.Items.STONE)
        );


        this.makeRecipe(ItemRegistry.CALCINATION_OVEN.get(), new RecipeBuilder(
                ItemRegistry.CALCINATION_OVEN.get(), 1)
                .pattern(" I ")
                .pattern("ICI")
                .pattern(" I ")
                .define('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
        );


        this.makeRecipe(ItemRegistry.DISTILLER.get(), new RecipeBuilder(
                ItemRegistry.DISTILLER.get(), 1)
                .pattern(" I ")
                .pattern("ICI")
                .pattern("III")
                .define('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
        );

        this.makeRecipe(ItemRegistry.LIQUEFACTION_CAULDRON.get(), new RecipeBuilder(
                ItemRegistry.LIQUEFACTION_CAULDRON.get(), 1)
                .pattern("CCC")
                .pattern("IBI")
                .pattern("SSS")
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Items.CAULDRON)
                .define('S', Tags.Items.STONE)
        );

        this.makeRecipe(ItemRegistry.SAL_AMMONIAC_ACCUMULATOR.get(), new RecipeBuilder(
                ItemRegistry.SAL_AMMONIAC_ACCUMULATOR.get(), 1)
                .pattern("ICI")
                .pattern("SIS")
                .pattern("S S")
                .define('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.STONE)
        );

        this.makeRecipe(ItemRegistry.SAL_AMMONIAC_TANK.get(), new RecipeBuilder(
                ItemRegistry.SAL_AMMONIAC_TANK.get(), 1)
                .pattern("ICI")
                .pattern("ICI")
                .pattern("RCR")
                .define('R', Tags.Items.RODS_WOODEN)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
        );

        this.makeRecipe(ItemRegistry.INCUBATOR.get(), new RecipeBuilder(
                ItemRegistry.INCUBATOR.get(), 1)
                .pattern("PSP")
                .pattern("GGG")
                .pattern("SCS")
                .define('P', ItemTags.PLANKS)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('S', Tags.Items.STONE)
        );

        this.makeRecipe(ItemRegistry.INCUBATOR_MERCURY_VESSEL.get(), new RecipeBuilder(
                ItemRegistry.INCUBATOR_MERCURY_VESSEL.get(), 1)
                .pattern("IMI")
                .pattern("GCG")
                .pattern("III")
                .define('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('M', ItemTagRegistry.ALCHEMICAL_MERCURIES)
        );

        this.makeRecipe(ItemRegistry.INCUBATOR_SALT_VESSEL.get(), new RecipeBuilder(
                ItemRegistry.INCUBATOR_SALT_VESSEL.get(), 1)
                .pattern("ISI")
                .pattern("cCc")
                .pattern("III")
                .define('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('c', Tags.Items.INGOTS_COPPER)
                .define('S', ItemTagRegistry.ALCHEMICAL_SALTS)
        );

        this.makeRecipe(ItemRegistry.INCUBATOR_SULFUR_VESSEL.get(), new RecipeBuilder(
                ItemRegistry.INCUBATOR_SULFUR_VESSEL.get(), 1)
                .pattern("ISI")
                .pattern("ICI")
                .pattern("III")
                .define('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', ItemTagRegistry.ALCHEMICAL_SULFURS)
        );
    }

    public JsonObject makeDivinationRodSettings(DivinationRodItem rodItem) {
        return this.makeDivinationRodSettings(rodItem.defaultTier, rodItem.defaultAllowedBlocksTag, rodItem.defaultDisallowedBlocksTag, rodItem.defaultRange, rodItem.defaultDuration, rodItem.defaultDurability);
    }

    public JsonObject makeDivinationRodSettings(Tier defaultTier, TagKey<Block> defaultAllowedBlocksTag, TagKey<Block> defaultDisallowedBlocksTag, int defaultRange, int defaultDuration, int defaultDurability) {
        JsonObject settings = new JsonObject();

        settings.addProperty(TheurgyConstants.Nbt.Divination.SETTING_TIER, TierSortingRegistry.getName(defaultTier).toString());
        settings.addProperty(TheurgyConstants.Nbt.Divination.SETTING_ALLOWED_BLOCKS_TAG, defaultAllowedBlocksTag.location().toString());
        settings.addProperty(TheurgyConstants.Nbt.Divination.SETTING_DISALLOWED_BLOCKS_TAG, defaultDisallowedBlocksTag.location().toString());
        settings.addProperty(TheurgyConstants.Nbt.Divination.SETTING_RANGE, defaultRange);
        settings.addProperty(TheurgyConstants.Nbt.Divination.SETTING_DURATION, defaultDuration);
        settings.addProperty(TheurgyConstants.Nbt.Divination.SETTING_DURABILITY, defaultDurability);

        return settings;
    }

    public void makeRecipe(ItemLike result, RecipeBuilder recipe) {
        this.makeRecipe(this.name(result.asItem()), recipe);
    }

    public void makeRecipe(String name, RecipeBuilder recipe) {
        this.recipeConsumer.accept(this.modLoc(name), recipe.build());
    }

    @Override
    public String getName() {
        return "Shaped Crafting Recipes";
    }

    private class RecipeBuilder {

        private final JsonObject recipe;

        public RecipeBuilder(ItemLike result) {
            this(result, 1);
        }

        public RecipeBuilder(ItemLike result, int count) {
            this(result, count, null);
        }

        public RecipeBuilder(ItemLike result, int count, @Nullable JsonObject nbt) {
            this(result, count, nbt, ForgeRegistries.RECIPE_SERIALIZERS.getKey(RecipeSerializer.SHAPED_RECIPE).toString());
        }

        public RecipeBuilder(ItemLike result, int count, @Nullable JsonObject nbt, String recipeType) {
            this(ShapedRecipeProvider.this.makeItemResult(ShapedRecipeProvider.this.locFor(result), count, nbt), recipeType);
        }

        public RecipeBuilder(JsonObject result, String recipeType) {
            this.recipe = new JsonObject();
            this.recipe.addProperty("type", recipeType);
            this.recipe.add("result", result);
            this.recipe.add("key", new JsonObject());
            this.recipe.add("pattern", new JsonArray());
        }

        public RecipeBuilder define(char key, TagKey<Item> tag) {
            return this.define(key, ShapedRecipeProvider.this.makeTagIngredient(ShapedRecipeProvider.this.locFor(tag)));
        }

        public RecipeBuilder define(char key, ItemLike item) {
            return this.define(key, ShapedRecipeProvider.this.makeItemIngredient(ShapedRecipeProvider.this.locFor(item)));
        }

        public RecipeBuilder define(char key, JsonObject ingredient) {
            var keyString = String.valueOf(key);
            var keys = this.recipe.getAsJsonObject("key");
            if (keys.has(keyString))
                throw new IllegalArgumentException("Key " + keyString + " already defined");

            keys.add(keyString, ingredient);
            return this;
        }

        public RecipeBuilder pattern(String pattern) {
            this.recipe.getAsJsonArray("pattern").add(pattern);
            return this;
        }

        public JsonObject build() {
            return this.recipe;
        }
    }
}