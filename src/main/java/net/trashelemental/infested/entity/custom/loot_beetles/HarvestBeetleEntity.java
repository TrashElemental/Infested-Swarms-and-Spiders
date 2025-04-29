package net.trashelemental.infested.entity.custom.loot_beetles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HarvestBeetleEntity extends LootBeetleEntity {


    public HarvestBeetleEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, ParticleTypes.POOF, SoundEvents.BEEHIVE_ENTER, 10);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("infested", "textures/entity/harvest_beetle.png");
    }

    @Override
    protected void registerGoals() {
        super.registerGoals(); {

            this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, (float) 10, 1, 1.2));
            this.goalSelector.addGoal(1, new PanicGoal(this, 1));
            this.goalSelector.addGoal(2, new FloatGoal(this));
            this.goalSelector.addGoal(3, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
            this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1));
            this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 26)
                .add(Attributes.ATTACK_KNOCKBACK, 0);
    }
}
