package net.trashelemental.infested.entity.custom.spider_alchemy;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.trashelemental.infested.entity.ai.NonTeleportingFollowOwnerGoal;

public class CloakedSpiderEntity extends AlchemySpiderEntity {
    public CloakedSpiderEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false, false) {
            @Override
            public boolean canUse() { return super.canUse() && !isTame(); }
        });
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Monster.class, false, false));

        this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(8, new LeapAtTargetGoal(this, (float) 0.5));
        this.goalSelector.addGoal(9, new NonTeleportingFollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(12, new FloatGoal(this));
        this.goalSelector.addGoal(13, new LookAtPlayerGoal(this, Player.class, (float) 6));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 0);
    }

    /**
     * The Cloaked Spider has more health than other spiders, but does not explode. Instead,
     * it deals its elemental effect on each hit.
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);

        if (flag && entity instanceof LivingEntity livingEntity) {
            this.doElementEffects(livingEntity);
        }

        return flag;
    }
}
