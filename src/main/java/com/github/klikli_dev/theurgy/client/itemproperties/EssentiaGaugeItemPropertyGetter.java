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

package com.github.klikli_dev.theurgy.client.itemproperties;

import com.github.klikli_dev.theurgy.common.item.tool.EssentiaGaugeItem;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class EssentiaGaugeItemPropertyGetter implements IItemPropertyGetter {
    //region Overrides
    @OnlyIn(Dist.CLIENT)
    @Override
    public float call(ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
        if (!stack.getOrCreateTag().contains(EssentiaGaugeItem.PROPERTY_TAG_NAME) ||
            stack.getTag().getFloat(EssentiaGaugeItem.PROPERTY_TAG_NAME) < 0)
            return EssentiaGaugeItem.NOT_FOUND;
        return stack.getTag().getFloat(EssentiaGaugeItem.PROPERTY_TAG_NAME);
    }
}