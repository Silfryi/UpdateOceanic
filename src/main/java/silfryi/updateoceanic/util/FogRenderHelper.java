package silfryi.updateoceanic.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FogRenderHelper {
    private static float red;
    private static float green;
    private static float blue;
    private static int lastWaterFogColor = -1;
    private static int waterFogColor = -1;
    private static long waterFogUpdateTime = -1L;

    public Vec3d getAndUpdateColor(EntityPlayer player,World world) {

        long time = world.getTotalWorldTime();
        int baseBiomeColorization = getWaterFogColor(world.getBiome(player.getPosition().add(-0.5, 0, -0.5)));
        if (waterFogUpdateTime < 0L) {
            lastWaterFogColor = baseBiomeColorization;
            waterFogColor = baseBiomeColorization;
            waterFogUpdateTime = time;
        }

        float lastRed = lastWaterFogColor >> 16 & 255;
        float lastGreen = lastWaterFogColor >> 8 & 255;
        float lastBlue = lastWaterFogColor & 255;
        float newRed = waterFogColor >> 16 & 255;
        float newGreen = waterFogColor >> 8 & 255;
        float newBlue = waterFogColor & 255;

        float f = MathHelper.clamp((float) (time - waterFogUpdateTime) / 100.0F, 0.0F, 1.0F);
        float f1 = newRed + f * (lastRed - newRed);
        float f2 = newGreen + f * (lastGreen - newGreen);
        float f3 = newBlue + f * (lastBlue - newBlue);
        red = f1 / 255.0F;
        green = f2 / 255.0F;
        blue = f3 / 255.0F;

        if (lastWaterFogColor != baseBiomeColorization) {
            lastWaterFogColor = baseBiomeColorization;
            waterFogColor = MathHelper.floor(f1) << 16 | MathHelper.floor(f2) << 8 | MathHelper.floor(f3);
            waterFogUpdateTime = time;
        }

        double d0 = player.posY * 0.03125D;
        if (d0 < 1.0D) {
            if (d0 < 0.0D) d0 = 0.0D;

            d0 = d0 * d0;
            red = (float) ((double) red * d0);
            green = (float) ((double) green * d0);
            blue = (float) ((double) blue * d0);
        }

        float waterBrightness = FogRenderHelper.getWaterBrightness(player);

        float f9 = Math.min(1.0F / red, Math.min(1.0F / green, 1.0F / blue));
        if (Float.isInfinite(f9)) f9 = Math.nextAfter(f9, 0.0);
        red = red * (1.0F - waterBrightness) + red * f9 * waterBrightness;
        green = green * (1.0F - waterBrightness) + green * f9 * waterBrightness;
        blue = blue * (1.0F - waterBrightness) + blue * f9 * waterBrightness;

        return new Vec3d(red, green, blue);
    }

    public void resetFog() {
        waterFogUpdateTime = -1L;
    }

    public static float getFogDensity(EntityPlayer player, World world) {
        float f = 0.05f - FogRenderHelper.getWaterBrightness(player) * FogRenderHelper.getWaterBrightness(player) * 0.03F;
        Biome biome = world.getBiome(player.getPosition());
        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) {
            f += 0.005F;
        }
        return f;
    }

    public static float getWaterBrightness(EntityPlayer entity) {
        int bubblesAmount = Math.min(Math.max(600 - entity.getAir() * 2, 0), 600);
        if ((float)bubblesAmount >= 600.0F) {
            return 1.0F;
        } else {
            float f2 = MathHelper.clamp((float)bubblesAmount / 100.0F, 0.0F, 1.0F);
            float f3 = (float)bubblesAmount < 100.0F ? 0.0F : MathHelper.clamp(((float)bubblesAmount - 100.0F) / 500.0F, 0.0F, 1.0F);
            return f2 * 0.6F + f3 * 0.39999998F;
        }
    }

    public static int getWaterFogColor(Biome biome) {
        switch (biome.getWaterColorMultiplier()) {
            case 4445678:
                return 270131; //warm
            case 3750089:
                return 329011; //frozen
            case 4566514:
                return 267827; //lukewarm
            case 4020182:
                return 329011; //cold
            case 4159204:
                return 329011; //standard
            case 6388580:
                return 2302743; //swamp
            default:
                return ColorShiftHelper.getFogShiftFromColor(biome.getWaterColorMultiplier());
        }
    }
}
