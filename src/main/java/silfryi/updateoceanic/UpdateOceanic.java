package silfryi.updateoceanic;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.GameData;
import silfryi.updateoceanic.biomes.BiomeWaterColorOverride;
import silfryi.updateoceanic.biomes.UpdateOceanicBiomes;
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

    @SubscribeEvent
    public void register(RegistryEvent.Register<Biome> evt)
    {
        //Biome properties
        UpdateOceanicBiomes.deepWarmOcean = new BiomeWaterColorOverride(new Biome.BiomeProperties("Deep Warm Ocean").setBaseHeight(-1.8f).setHeightVariation(0.1f).setTemperature(1.05f).setRainfall(1.0f));
        UpdateOceanicBiomes.warmOcean = new BiomeWaterColorOverride(new Biome.BiomeProperties("Deep Warm Ocean").setBaseHeight(-1.8f).setHeightVariation(0.1f).setTemperature(1.0f));
        UpdateOceanicBiomes.deepLukewarmOcean = new BiomeWaterColorOverride(new Biome.BiomeProperties("Deep Warm Ocean").setBaseHeight(-1.8f).setHeightVariation(0.1f).setTemperature(0.7f));
        UpdateOceanicBiomes.lukewarmOcean = new BiomeWaterColorOverride(new Biome.BiomeProperties("Deep Warm Ocean").setBaseHeight(-1.8f).setHeightVariation(0.1f).setTemperature(0.75f));
        UpdateOceanicBiomes.deepColdOcean = new BiomeWaterColorOverride(new Biome.BiomeProperties("Deep Warm Ocean").setBaseHeight(-1.8f).setHeightVariation(0.1f).setTemperature(0.2f));
        UpdateOceanicBiomes.coldOcean = new BiomeWaterColorOverride(new Biome.BiomeProperties("Deep Warm Ocean").setBaseHeight(-1.8f).setHeightVariation(0.1f).setTemperature(0.25f));
        UpdateOceanicBiomes.deepFrozenOcean = new BiomeWaterColorOverride(new Biome.BiomeProperties("Deep Warm Ocean").setBaseHeight(-1.8f).setHeightVariation(0.1f).setTemperature(-0.05f));
        UpdateOceanicBiomes.frozenOcean = new BiomeWaterColorOverride(new Biome.BiomeProperties("Deep Warm Ocean").setBaseHeight(-1.8f).setHeightVariation(0.1f).setTemperature(0.0f));

        //Biome registry names outside of constructor
        UpdateOceanicBiomes.deepWarmOcean.setRegistryName(UpdateOceanic.NAME, "deep_warm_ocean");
        UpdateOceanicBiomes.warmOcean.setRegistryName(UpdateOceanic.NAME, "warm_ocean");
        UpdateOceanicBiomes.deepLukewarmOcean.setRegistryName(UpdateOceanic.NAME, "lukewarm_ocean");
        UpdateOceanicBiomes.lukewarmOcean.setRegistryName(UpdateOceanic.NAME, "deep_lukewarm_ocean");
        UpdateOceanicBiomes.deepColdOcean.setRegistryName(UpdateOceanic.NAME, "deep_cold_ocean");
        UpdateOceanicBiomes.coldOcean.setRegistryName(UpdateOceanic.NAME, "cold_ocean");
        UpdateOceanicBiomes.deepFrozenOcean.setRegistryName(UpdateOceanic.NAME, "deep_frozen_ocean");
        UpdateOceanicBiomes.frozenOcean.setRegistryName(UpdateOceanic.NAME, "frozen_ocean");

        //Register deep versions, then normal ones
        evt.getRegistry().registerAll(UpdateOceanicBiomes.deepWarmOcean, UpdateOceanicBiomes.deepLukewarmOcean, UpdateOceanicBiomes.deepColdOcean, UpdateOceanicBiomes.deepFrozenOcean);
        evt.getRegistry().registerAll(UpdateOceanicBiomes.warmOcean, UpdateOceanicBiomes.lukewarmOcean, UpdateOceanicBiomes.coldOcean, UpdateOceanicBiomes.frozenOcean);

        //Biome Dictionary type registration
        BiomeDictionary.addTypes(UpdateOceanicBiomes.deepWarmOcean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER, BiomeDictionary.Type.HOT);
        BiomeDictionary.addTypes(UpdateOceanicBiomes.warmOcean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER, BiomeDictionary.Type.HOT);
        BiomeDictionary.addTypes(UpdateOceanicBiomes.deepLukewarmOcean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER);
        BiomeDictionary.addTypes(UpdateOceanicBiomes.lukewarmOcean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER);
        BiomeDictionary.addTypes(UpdateOceanicBiomes.deepColdOcean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER, BiomeDictionary.Type.COLD);
        BiomeDictionary.addTypes(UpdateOceanicBiomes.coldOcean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER, BiomeDictionary.Type.COLD);
        BiomeDictionary.addTypes(UpdateOceanicBiomes.deepFrozenOcean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER, BiomeDictionary.Type.SNOWY);
        BiomeDictionary.addTypes(UpdateOceanicBiomes.frozenOcean, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.WATER, BiomeDictionary.Type.SNOWY);
    }
}
