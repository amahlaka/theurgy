/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.theurgy.client.render;

import com.klikli_dev.theurgy.config.ClientConfig;
import com.klikli_dev.theurgy.item.AlchemicalSulfurItem;
import com.klikli_dev.theurgy.registry.ItemRegistry;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class SulfurBEWLR extends BlockEntityWithoutLevelRenderer {

    private static final SulfurBEWLR instance = new SulfurBEWLR();
    private static final ItemStack emptyJarStack = new ItemStack(ItemRegistry.EMPTY_JAR.get());
    private static final ItemStack labeledEmptyJarStack = new ItemStack(ItemRegistry.EMPTY_JAR_LABELED.get());
    private static final ItemStack labelStack = new ItemStack(ItemRegistry.JAR_LABEL.get());

    public SulfurBEWLR() {
        super(null, null);
    }

    public static SulfurBEWLR get() {
        return instance;
    }

    private static boolean isLeftHand(ItemTransforms.TransformType type) {
        return type == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || type == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
    }

    @Override
    public void renderByItem(ItemStack sulfurStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        var renderSource = ClientConfig.get().rendering.renderSulfurSourceItem.get();

        //if we do not render the source we show a simplified labeled icon with pixels representing fictional text
        var jarStack = renderSource ? emptyJarStack : labeledEmptyJarStack;

        pPoseStack.popPose();
        pPoseStack.pushPose(); //reset pose that we get from the item renderer, it moves by half a block which we don't want

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        BakedModel model = itemRenderer.getModel(jarStack, null, null, 0);

        var flatLighting = pTransformType == ItemTransforms.TransformType.GUI && !model.usesBlockLight();
        if (flatLighting)
            Lighting.setupForFlatItems();

        itemRenderer.render(jarStack, pTransformType, isLeftHand(pTransformType), pPoseStack, pBuffer, pPackedLight, pPackedOverlay, model);

        //note: if we reset to 3d item light here it ignores it above and renders dark .. idk why

        //if we render the source we render a text-less clean label and the source item on top of the jar stack
        if(renderSource){
            this.renderLabel(sulfurStack, pTransformType, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
            this.renderContainedItem(sulfurStack, pTransformType, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }

    }

    public void renderLabel(ItemStack sulfurStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        BakedModel labelModel = itemRenderer.getModel(labelStack, null, null, 0);

        //now apply the transform to the label to make it look right in-world -> because below we render with gui transform which would mess it up
        labelModel = labelModel.applyTransform(pTransformType, pPoseStack, isLeftHand(pTransformType));

        pPoseStack.pushPose();

        float pixel = 1f / 16f;
        pPoseStack.translate(0, 0, pixel * 0.5); //move it before item

        //pPoseStack.translate(0, -pixel * 3, 0); //position it on the item -> center
        pPoseStack.scale(1F, 1F, 0.01F); //flatten item

        Lighting.setupForFlatItems(); //always render label flat
        itemRenderer.render(labelStack, ItemTransforms.TransformType.GUI, isLeftHand(pTransformType), pPoseStack, pBuffer, pPackedLight, pPackedOverlay, labelModel);
        //note: if we reset to 3d item light here it ignores it above and renders dark .. idk why

        pPoseStack.popPose();
    }

    public void renderContainedItem(ItemStack sulfurStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        var containedStack = AlchemicalSulfurItem.getSourceStack(sulfurStack);
        if (!containedStack.isEmpty()) {
            BakedModel containedModel = itemRenderer.getModel(containedStack, null, null, 0);

            //not sure why we don't have to apply transform here. Somehow label carries over, because if we do not call label our rendering is off
            //containedModel = containedModel.applyTransform(pTransformType, pPoseStack, isLeftHand(pTransformType));

            pPoseStack.pushPose();

            float pixel = 1f / 16f;
            pPoseStack.translate(0, 0, pixel * 0.6); //move it before label

            var scale = 0.36f;
            pPoseStack.scale(scale, scale, scale);
            pPoseStack.translate(0, -pixel * 3.2, 0); //position it on the item -> center
            pPoseStack.scale(0.74F, 0.74F, 0.01F); //flatten item

            Lighting.setupForFlatItems(); //always render "labeled" item flat
            itemRenderer.render(containedStack, ItemTransforms.TransformType.GUI, isLeftHand(pTransformType), pPoseStack, pBuffer, pPackedLight, pPackedOverlay, containedModel);
            //note: if we reset to 3d item light here it ignores it above and renders dark .. idk why

            pPoseStack.popPose();
        }
    }
}