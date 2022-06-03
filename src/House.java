import java.util.*;
import processing.core.PImage;
//Entity functions and actions

/**
 * An this that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */

public final class House extends EntityCls
{
    private int resourceCount = 0;
    private int resourceLimit;
    private boolean isMega;
    public House(
            String id,
            Point position,
            PImage image, int fenceReq, boolean mega)
    {
        super(id, position, image);
        resourceLimit = fenceReq;
        isMega = mega;
    }
    public void increaseResource(int amt){
        resourceCount += amt;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public boolean isMega(){
        return isMega;
    }


}
