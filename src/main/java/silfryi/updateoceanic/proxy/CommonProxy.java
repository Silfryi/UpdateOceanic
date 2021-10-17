package silfryi.updateoceanic.proxy;

import net.minecraftforge.common.MinecraftForge;
import silfryi.updateoceanic.blocks.UOBlocks;
import silfryi.updateoceanic.util.FogRenderHelper;

public class CommonProxy {

    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new UOBlocks());
    }

    public void preInit() {
        // TODO Auto-generated method stub

    }

    public void init() {
        // TODO Auto-generated method stub

    }

    public void preInitBlocks() {
        // TODO Auto-generated method stub

    }

    public void preInitItems() {
        // TODO Auto-generated method stub

    }

    public FogRenderHelper getFogRenderHelper() {
        return null;
    }
}
