package net.shizotoaster.bygone_nether_fixes.mixin.bygone_nether;

import com.izofar.bygonenether.entity.IShieldedMob;
import com.izofar.bygonenether.entity.PiglinHunter;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.player.Player;
import net.shizotoaster.bygone_nether_fixes.ai.ShieldDefenseGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PiglinHunter.class)
public class PiglinHunterMixin<M extends Mob & IShieldedMob> {
    @Unique
    private GoalSelector instance;
    @Unique
    private M            mob;

    /* What an awful way to
    * - this.goalSelector.addGoal(1, new UseShieldGoal<>(this, Player.class));
    *   super.registerGoals();
    * + this.goalSelector.addGoal(1, new ShieldDefenseGoal<>(this, Player.class));
    */

    @ModifyArgs(method = "registerGoals", at = @At(value = "INVOKE", target = "Lcom/izofar/bygonenether/entity/ai/goal/ShieldGoal;<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;)V"))
    private void yoink_args(Args args) {
        this.mob = args.get(0);
    }

    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V"))
    private void cancel_addGoal(GoalSelector instance, int priority, Goal goal) {
        this.instance = instance;
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void custom_addGoal(CallbackInfo ci) {
        instance.addGoal(1, new ShieldDefenseGoal<>(mob, Player.class));
    }
}
