package silfryi.updateoceanic.event;

import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import silfryi.updateoceanic.UpdateOceanic;
import silfryi.updateoceanic.util.ColorShiftHelper;
import silfryi.updateoceanic.util.FogRenderHelper;

import static net.minecraft.client.renderer.GlStateManager.FogMode.EXP2;

public class ColorEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onFogDensityEvent(EntityViewRenderEvent.FogDensity event) {
        if (event.getEntity().world.getBlockState(event.getEntity().getPosition().up()).getBlock() instanceof BlockLiquid && event.getEntity() instanceof EntityPlayer) {
            GlStateManager.setFog(EXP2);
            event.setDensity(FogRenderHelper.getFogDensity((EntityPlayer) event.getEntity(), event.getEntity().world));
            event.setCanceled(true);
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onFogColorsEvent(EntityViewRenderEvent.FogColors event) {
        if (event.getEntity().world.getBlockState(event.getEntity().getPosition().up()).getBlock() instanceof BlockLiquid && event.getEntity() instanceof EntityPlayer) {
            Vec3d colors = UpdateOceanic.proxy.getFogRenderHelper().getAndUpdateColor((EntityPlayer) event.getEntity(), event.getEntity().world);
            event.setRed((float) colors.x);
            event.setGreen((float) colors.y);
            event.setBlue((float) colors.z);
        } else
            UpdateOceanic.proxy.getFogRenderHelper().resetFog();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onBiomeWaterColorsEvent(BiomeEvent.GetWaterColor event) {
        //if (event.getBiome() instanceof  BiomeNoColorChanges) return;
        if (event.getOriginalColor() == 14745518) event.setNewColor(6388580); //swamps have special handling for new vMC color
        else if (event.getOriginalColor() == 16777215) event.setNewColor(4159204); //anything with the default water color have special handling for new vMC color
        else
            event.setNewColor(ColorShiftHelper.getWhiteAppliedWaterColor(event.getOriginalColor()));
    }
}
