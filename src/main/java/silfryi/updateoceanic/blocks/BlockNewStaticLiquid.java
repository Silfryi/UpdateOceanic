package silfryi.updateoceanic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNewStaticLiquid extends BlockStaticLiquid {

    public BlockNewStaticLiquid(Material materialIn) {
        super(materialIn);
        this.setTickRandomly(false);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
        return originalColor;
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 1;
    }

    @Override
    public int getLightOpacity(IBlockState state) {
        return 1;
    }

    @Override
    public Block setLightOpacity(int opacity) {return this;}
}
