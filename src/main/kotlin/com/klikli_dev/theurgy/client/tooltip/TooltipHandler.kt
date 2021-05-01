/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
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

package com.klikli_dev.theurgy.client.tooltip

import com.klikli_dev.theurgy.Theurgy
import com.klikli_dev.theurgy.registry.TooltipRegistry
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
object TooltipHandler {
    private val shiftForMoreInformation =
        TranslationTextComponent("tooltip.${Theurgy.MOD_ID}.shift_for_more_information")

    @SubscribeEvent
    fun onAddInformation(event: ItemTooltipEvent) {
        val tooltip = TooltipRegistry.tooltips[event.itemStack.item]
        if (tooltip != null && tooltip.predicate.invoke(event.itemStack)) {
            if (!Screen.hasShiftDown()) {
                //we cache the text component
                if(tooltip.tooltip == null)
                    tooltip.tooltip = TranslationTextComponent("${event.itemStack.item.translationKey}.tooltip")
                event.toolTip.add(tooltip.tooltip);
                if (tooltip.withShift) {
                    event.toolTip.add(StringTextComponent(""))
                    event.toolTip.add(shiftForMoreInformation)
                }
            } else {
                if (tooltip.withShift) {
                    //we cache the text component
                    if(tooltip.shiftTooltip == null)
                        tooltip.shiftTooltip = TranslationTextComponent("${event.itemStack.item.translationKey}.tooltip.shift")
                    event.toolTip.add(tooltip.shiftTooltip)
                }
            }
        }
    }
}