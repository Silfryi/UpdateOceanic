package silfryi.updateoceanic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import silfryi.updateoceanic.UpdateOceanic;

@Mod.EventBusSubscriber(modid= UpdateOceanic.MODID)
public class UOBlocks {

    public static BlockNewDynamicLiquid flowingWater;
    public static BlockNewStaticLiquid water;

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        System.out.println("stuffs0");
        //Make some new water blocks with no bad fog calcs and the correct light opacity
        flowingWater = (BlockNewDynamicLiquid) new BlockNewDynamicLiquid(Material.WATER).setHardness(100.0F).setLightOpacity(1).setUnlocalizedName("water").setRegistryName("flowing_water");
        water = (BlockNewStaticLiquid) new BlockNewStaticLiquid(Material.WATER).setHardness(100.0F).setLightOpacity(1).setUnlocalizedName("water").setRegistryName("water");

        //Actually register those blocks
        event.getRegistry().register(flowingWater);
        event.getRegistry().register(water);
    }
}
