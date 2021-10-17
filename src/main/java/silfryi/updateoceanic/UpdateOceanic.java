package silfryi.updateoceanic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import silfryi.updateoceanic.blocks.BlockNewDynamicLiquid;
import silfryi.updateoceanic.blocks.BlockNewStaticLiquid;
import silfryi.updateoceanic.proxy.CommonProxy;

@Mod(modid = UpdateOceanic.MODID, name = UpdateOceanic.NAME, version = UpdateOceanic.VERSION)
public class UpdateOceanic {
    public static final String MODID = "updateoceanic";
    public static final String NAME = "Update Oceanic";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide="silfryi.updateoceanic.proxy.ClientProxy", serverSide="silfryi.updateoceanic.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("stuffs");
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> worldIn != null && pos != null ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : 4159204, Blocks.WATER, Blocks.FLOWING_WATER);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("stuffs2");
        UpdateOceanic.proxy.registerEventHandlers();
    }
}
