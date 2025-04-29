package net.trashelemental.infested.entity.custom.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.spider_alchemy.AlchemySpiderEntity;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;
import net.trashelemental.infested.junkyard_lib.entity.method.SummonMethods;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

import javax.annotation.Nullable;

public class SpiderEggSacEntity extends ThrowableItemProjectile {

    public SpiderEggSacEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SpiderEggSacEntity(Level pLevel) {
        super(ModEntities.SPIDER_EGG_SAC.get(), pLevel);
    }

    public SpiderEggSacEntity(Level level, LivingEntity shooter) {
        super(ModEntities.SPIDER_EGG_SAC.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SPIDER_EGG_SAC.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide) {
            Entity target = result.getEntity();
            if (!(this.getOwner() instanceof Player player)) return;

            DamageSource source = this.damageSources().playerAttack(player);

            target.hurt(source, 1.0F);

            BlockPos pos = this.blockPosition();
            spawnMinions(level(), pos, target);
            spawnVFX(pos);
            this.discard();
        }
    }

    private void spawnMinions(Level level, BlockPos pos, @Nullable Entity target) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!(this.getOwner() instanceof Player player)) return;

        for (int i = 0; i < 3; i++) {
            Entity entity = ModEntities.SPIDER_MINION.get().create(serverLevel);
            if (entity instanceof MinionEntity minion) {
                SummonMethods.summonMinion(level, pos, minion, 300, false, player);

                if (target instanceof LivingEntity livingTarget && shouldTarget(target)) {
                    minion.setTarget(livingTarget);
                }
            }
        }
    }

    private boolean shouldTarget(Entity target) {
        Entity owner = this.getOwner();
        if (owner == null) return true;
        if (!(owner instanceof Player player)) return true;
        if (target == owner) return false;
        if (target instanceof TamableAnimal tamable && tamable.isOwnedBy(player)) return false;

        if (target.isAlliedTo(owner)) return false;

        return true;
    }

    private void spawnVFX(BlockPos pos) {
        ParticleMethods.ParticlesAroundServerSide(this.level(), ParticleTypes.POOF,
                pos.getX(), pos.getY(), pos.getZ(), 5, 0.5);

        this.level().gameEvent(this, GameEvent.ENTITY_PLACE, pos);
        this.level().playSound(null, pos,
                SoundEvents.SNIFFER_EGG_HATCH, SoundSource.PLAYERS, 0.3F, 1.3F);
    }
}
