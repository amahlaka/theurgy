/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.theurgy.common.config;

import com.github.klikli_dev.theurgy.common.config.value.CachedInt;
import net.minecraftforge.common.ForgeConfigSpec;

public class TheurgyConfig extends ConfigBase {

    //region Fields
    public final ForgeConfigSpec spec;
    public final CrystalSettings crystalSettings;
    public final EssentiaSettings essentiaSettings;
    //endregion Fields

    //region Initialization
    public TheurgyConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        this.crystalSettings = new CrystalSettings(this, builder);
        this.essentiaSettings = new EssentiaSettings(this, builder);

        this.spec = builder.build();
    }

    //endregion Initialization

    public class CrystalSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedInt primaMateriaSpreadEssentia;
        //endregion Fields

        //region Initialization
        public CrystalSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Crystal Settings").push("crystals");

            this.primaMateriaSpreadEssentia = CachedInt.cache(this,
                    builder.comment(
                            "The amount of dissolved essentia required in a chunk for a pure crystal to create a prima materia crystal.")
                            .define("primaMateriaSpreadEssentia", 1000));

            builder.pop();
        }
        //endregion Initialization
    }

    public class EssentiaSettings extends ConfigCategoryBase {
        //region Fields
        public final CachedInt crucibleEssentiaToDiffuse;
        public final CachedInt crucibleDiffuseTicks;
        //endregion Fields

        //region Initialization
        public EssentiaSettings(IConfigCache parent, ForgeConfigSpec.Builder builder) {
            super(parent, builder);
            builder.comment("Essentia Settings").push("essentia");

            this.crucibleEssentiaToDiffuse = CachedInt.cache(this,
                    builder.comment(
                            "The amount of essentia to diffuse from cauldrons after crucibleDiffuseTicks have elapsed.")
                            .define("cruciblcrucibleEssentiaToDiffuseeAmountToDissolve", 10));

            this.crucibleDiffuseTicks = CachedInt.cache(this,
                    builder.comment(
                            "The amount of ticks between diffusing essentia from crucibles.")
                            .define("crucibleDiffuseTicks", 200));

            builder.pop();
        }
        //endregion Initialization
    }
}