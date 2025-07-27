package net.shizotoaster.bygone_nether_fixes.mixin.bygone_nether;

import com.izofar.bygonenether.entity.PiglinPrisoner;
import com.izofar.bygonenether.entity.ai.behaviour.ModStopHoldingItemIfNoLongerAdmiring;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModStopHoldingItemIfNoLongerAdmiring.class)
public class ModStopHoldingItemIfNoLongerAdmiringMixin<E extends PiglinPrisoner> {
    @Inject(method = "checkExtraStartConditions(Lnet/minecraft/server/level/ServerLevel;Lcom/izofar/bygonenether/entity/PiglinPrisoner;)Z", at = @At("HEAD"), cancellable = true)
    private void custom_conditions(ServerLevel serverLevel, E owner, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!owner.getOffhandItem().isEmpty() && !(owner.getOffhandItem().getItem() instanceof ShieldItem));
    }
}
