// SPDX-FileCopyrightText: 2024 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.theurgy.content.apparatus.digestionvat;

import com.klikli_dev.theurgy.content.behaviour.crafting.HasCraftingBehaviour;
import com.klikli_dev.theurgy.content.behaviour.fluidhandler.FluidHandlerBehaviour;
import com.klikli_dev.theurgy.content.behaviour.fluidhandler.OneTankFluidHandlerBehaviour;
import com.klikli_dev.theurgy.content.behaviour.interaction.InteractionBehaviour;
import com.klikli_dev.theurgy.content.behaviour.itemhandler.DynamicOneOutputSlotItemHandlerBehaviour;
import com.klikli_dev.theurgy.content.behaviour.itemhandler.ItemHandlerBehaviour;
import com.klikli_dev.theurgy.content.recipe.DigestionRecipe;
import com.klikli_dev.theurgy.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;


public class DigestionVatBlock extends Block implements EntityBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final VoxelShape BOUNDING_BOX = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    private static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected ItemHandlerBehaviour itemHandlerBehaviour;
    protected FluidHandlerBehaviour fluidHandlerBehaviour;

    protected InteractionBehaviour interactionBehaviour;

    public DigestionVatBlock(Properties pProperties) {
        super(pProperties);

        this.itemHandlerBehaviour = new DynamicOneOutputSlotItemHandlerBehaviour();
        this.fluidHandlerBehaviour = new OneTankFluidHandlerBehaviour();
        this.interactionBehaviour = new DigestionVatInteractionBehaviour();

        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(OPEN, true));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        //We do not check for client side because
        // a) returning success causes https://github.com/klikli-dev/theurgy/issues/158
        // b) client side BEs are separate objects even in SP, so modification in our behaviours is safe

        var interactionResult = this.interactionBehaviour.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
        if (interactionResult != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION) {
            return interactionResult;
        }

        if (this.fluidHandlerBehaviour.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult) == ItemInteractionResult.SUCCESS) {
            return ItemInteractionResult.SUCCESS;
        }

        if (this.itemHandlerBehaviour.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult) == ItemInteractionResult.SUCCESS) {
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        //Closed = is processing
        //has signal -> should be processing -> close

        boolean hasSignal = pLevel.hasNeighborSignal(pPos);
        boolean wasOpen = pState.getValue(OPEN);
        if (hasSignal && wasOpen) {

            var blockEntity = pLevel.getBlockEntity(pPos);
            if (!(blockEntity instanceof HasCraftingBehaviour<?, ?, ?>))
                return;

            @SuppressWarnings("unchecked") var vat = (HasCraftingBehaviour<?, DigestionRecipe, ?>) blockEntity;

            var craftingBehaviour = vat.craftingBehaviour();

            var recipe = craftingBehaviour.getRecipe();
            if (recipe.isPresent() && craftingBehaviour.canCraft(recipe.get())) {
                pLevel.setBlock(pPos, pState.setValue(OPEN, false), Block.UPDATE_CLIENTS);
            }
        } else if (!hasSignal && !wasOpen) {
            pLevel.setBlock(pPos, pState.setValue(OPEN, true), Block.UPDATE_CLIENTS);
        }
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            if (pLevel.getBlockEntity(pPos) instanceof DigestionVatBlockEntity blockEntity) {
                for (int i = 0; i < blockEntity.storageBehaviour.inventory.getSlots(); i++) {
                    Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), blockEntity.storageBehaviour.inventory.getStackInSlot(i));
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }


    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOUNDING_BOX;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HORIZONTAL_FACING, OPEN);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return BlockEntityRegistry.DIGESTION_VAT.get().create(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof DigestionVatBlockEntity blockEntity) {
                blockEntity.tickServer();
            }
        };
    }
}
