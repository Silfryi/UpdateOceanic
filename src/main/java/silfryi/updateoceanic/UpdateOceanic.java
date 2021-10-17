package silfryi.updateoceanic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;
import silfryi.updateoceanic.blocks.BlockNewDynamicLiquid;
import silfryi.updateoceanic.blocks.BlockNewStaticLiquid;
import silfryi.updateoceanic.blocks.UOBlocks;
import silfryi.updateoceanic.proxy.CommonProxy;

import javax.annotation.Nonnull;

@Mod(modid = UpdateOceanic.MODID, name = UpdateOceanic.NAME, version = UpdateOceanic.VERSION)
@Mod.EventBusSubscriber
public class UpdateOceanic {
    public static final String MODID = "updateoceanic";
    public static final String NAME = "Update Oceanic";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide="silfryi.updateoceanic.proxy.ClientProxy", serverSide="silfryi.updateoceanic.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    @SubscribeEvent
    public void preInit(FMLPreInitializationEvent event) {
        //Make some new water blocks with no bad fog calcs and the correct light opacity
        UOBlocks.flowingWater = (BlockNewDynamicLiquid) new BlockNewDynamicLiquid(Material.WATER).setHardness(100.0F).setLightOpacity(1).setUnlocalizedName("water").setRegistryName(new ResourceLocation("flowing_water"));
        UOBlocks.water = (BlockNewStaticLiquid) new BlockNewStaticLiquid(Material.WATER).setHardness(100.0F).setLightOpacity(1).setUnlocalizedName("water").setRegistryName(new ResourceLocation("water"));
    }

    @EventHandler
    @SubscribeEvent
    public void init(FMLInitializationEvent event) {
        //Block init stuff to make sure we actually have it function
        GameData.register_impl(UOBlocks.flowingWater);
        GameData.register_impl(UOBlocks.water);
        //Block color stuff
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> worldIn != null && pos != null ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : 4159204, Blocks.WATER, Blocks.FLOWING_WATER);
    }

    @EventHandler
    @SubscribeEvent
    public void postInit(FMLPostInitializationEvent event) {
        //Event handler stuff
        UpdateOceanic.proxy.registerEventHandlers();
    }

    @SubscribeEvent
    public void registerBlocks(@Nonnull RegistryEvent.Register<Block> event) {
        //Register all the blocks
        event.getRegistry().registerAll(UOBlocks.flowingWater, UOBlocks.water);
    }
}
