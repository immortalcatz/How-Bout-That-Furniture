package com.momnop.furniture.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFurnitureConnecting extends BlockFurniture {
	
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

    public BlockFurnitureConnecting(Material materialIn, float hardness, SoundType type, String unlocalizedName, CreativeTabs tab) {
		super(materialIn, hardness, type, unlocalizedName, tab);
		setDefaultState(this.getDefaultState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST });
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		return state;
	}
	
	public boolean isAdjacentBlockOfMyTypeOrOpaque(IBlockAccess world, BlockPos position, EnumFacing facing) {

        assert null != world : "world cannot be null";
        assert null != position : "position cannot be null";
        assert null != this : "type cannot be null";

        BlockPos newPosition = position.offset(facing);
        IBlockState blockState = world.getBlockState(newPosition);
        Block block = (null == blockState) ? null : blockState.getBlock();
        
        if (this == block) {
        	return true;
        } else if (block instanceof BlockFurnitureConnecting || block instanceof BlockFurnitureConnectingFacing) {
        	return true;
        }
        else if (blockState.isOpaqueCube()) {
        	return true;
        } else {
        	return false;
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position) {

        state = state
                .withProperty(EAST, this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.EAST))
                .withProperty(NORTH, this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.NORTH))
                .withProperty(SOUTH, this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.SOUTH))
                .withProperty(WEST, this.isAdjacentBlockOfMyTypeOrOpaque(world, position, EnumFacing.WEST));
        
        return state;
    }
    
    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world,
    		BlockPos pos, EnumFacing side) {
    	if (side == EnumFacing.UP) {
    		return true;
    	}
    	return false;
    }
}
