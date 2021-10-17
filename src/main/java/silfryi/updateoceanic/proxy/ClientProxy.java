package silfryi.updateoceanic.proxy;

import net.minecraftforge.common.MinecraftForge;
import silfryi.updateoceanic.event.ColorEventHandler;
import silfryi.updateoceanic.util.FogRenderHelper;

public class ClientProxy extends CommonProxy {

    private static final FogRenderHelper clientFogHelper = new FogRenderHelper();

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(new ColorEventHandler());
    }

    @Override
    public FogRenderHelper getFogRenderHelper() {
        return clientFogHelper;
    }
}
