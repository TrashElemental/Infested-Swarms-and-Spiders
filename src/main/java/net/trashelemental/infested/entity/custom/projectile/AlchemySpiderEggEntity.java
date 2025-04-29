package net.trashelemental.infested.entity.custom.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.spider_alchemy.AlchemySpiderEntity;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.entity.method.SummonMethods;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

public class AlchemySpiderEggEntity extends ThrowableItemProjectile {

    private EntityType<?> entity;
    private AlchemySpiderEntity.ElementType element;

    public AlchemySpiderEggEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AlchemySpiderEggEntity(Level pLevel) {
        super(ModEntities.ALCHEMY_SPIDER_EGG.get(), pLevel);
    }

    public AlchemySpiderEggEntity(Level level, LivingEntity shooter) {
        super(ModEntities.ALCHEMY_SPIDER_EGG.get(), shooter, level);
    }

    public AlchemySpiderEggEntity(Level level, LivingEntity shooter, EntityType<?> spawnEntityType, AlchemySpiderEntity.ElementType elementType) {
        super(ModEntities.ALCHEMY_SPIDER_EGG.get(), shooter, level);
        this.entity = spawnEntityType;
        this.element = elementType;
    }

    public void setSpawnEntityType(EntityType<?> type) {
        this.entity = type;
    }

    public void setElementType(AlchemySpiderEntity.ElementType elementType) {
        this.element = elementType;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.JUMPING_FIRE_SPIDER_EGG.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide) {
            BlockPos pos = result.getBlockPos();
            spawnSpider(pos, false);

            if (this.getOwner() instanceof Player player) {
                int bonusChance = getBonusSpawnChance(player);
                if (level().getRandom().nextInt(100) < bonusChance) {
                    spawnSpider(pos, true);
                }
            }

            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide) {
            BlockPos pos = result.getEntity().blockPosition();
            spawnSpider(pos, false);

            if (this.getOwner() instanceof Player player) {
                int bonusChance = getBonusSpawnChance(player);
                if (level().getRandom().nextInt(100) < bonusChance) {
                    spawnSpider(pos, true);
                }
            }

            this.discard();
        }
    }

    private void spawnSpider(BlockPos pos, Boolean isBonus) {
        if (entity == null || element == null) return;
        if (!(this.getOwner() instanceof Player player)) return;

        Entity entityToSpawn = entity.create(this.level());
        if (!(entityToSpawn instanceof AlchemySpiderEntity spider)) return;

        spider.setElement(element);
        spider.setIsBonus(isBonus);
        spider.moveTo(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, this.getYRot(), 0.0F);

        if (level() instanceof ServerLevel serverLevel) {
            SummonMethods.summonMinion(serverLevel, pos, spider, 0, true, player);
        }

        spawnVFX(spider);
    }

    private void spawnVFX(LivingEntity entity) {
        ParticleMethods.ParticlesAroundServerSide(this.level(), ParticleTypes.POOF,
                entity.getX(), entity.getY(), entity.getZ(), 5, 0.5);

        this.level().gameEvent(this, GameEvent.ENTITY_PLACE, entity.getOnPos());
        this.level().playSound(null, entity.getOnPos(),
                SoundEvents.SNIFFER_EGG_HATCH, SoundSource.PLAYERS, 0.5F, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (entity != null) {
            tag.putString("SpawnEntityType", EntityType.getKey(entity).toString());
        }
        if (element != null) {
            tag.putString("ElementType", element.name());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("SpawnEntityType")) {
            entity = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(tag.getString("SpawnEntityType")));
        }
        if (tag.contains("ElementType")) {
            element = AlchemySpiderEntity.ElementType.valueOf(tag.getString("ElementType"));
        }
    }

    public int getBonusSpawnChance(Player player) {
        int bonusSpawnChance = 0;

        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SPIDER_HELMET.get()) bonusSpawnChance++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.SPIDER_CHESTPLATE.get()) bonusSpawnChance++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.SPIDER_LEGGINGS.get()) bonusSpawnChance++;
        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.SPIDER_BOOTS.get()) bonusSpawnChance++;

        return (bonusSpawnChance * 20);
    }
}
