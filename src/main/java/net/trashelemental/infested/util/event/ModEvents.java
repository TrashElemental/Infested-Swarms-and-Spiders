package net.trashelemental.infested.util.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.GrubEntity;
import net.trashelemental.infested.entity.custom.minions.AttackBeeEntity;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;
import net.trashelemental.infested.entity.custom.minions.SpiderMinionEntity;
import net.trashelemental.infested.entity.custom.minions.TamedSilverfishEntity;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.magic.effects.ModMobEffects;
import net.trashelemental.infested.util.BlockEntityMapping;
import net.trashelemental.infested.util.EntitySpawnInfo;

import javax.annotation.Nullable;
import java.util.Random;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class ModEvents {


    //Right-click block events
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        ItemStack tool = event.getItemStack();

        if (blockState.is(Blocks.BEEHIVE) || blockState.is(Blocks.BEE_NEST)) {
            int honeyLevel = blockState.getValue(BeehiveBlock.HONEY_LEVEL);

            if (tool.is(Items.SHEARS) && honeyLevel >= 5) {

                Random random = new Random();
                if (random.nextFloat() < 0.40f) {
                    ItemStack customItem = new ItemStack(ModItems.BEE_EGGS.get());
                    BlockPos blockPos = event.getPos();
                    Level level = event.getLevel();

                    if (!level.isClientSide) {
                        ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, customItem);
                        level.addFreshEntity(itemEntity);
                    }
                }
            }
        }
    }


    //On Attack events
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() != null && event.getSource().getEntity() instanceof LivingEntity sourceEntity) {
            execute(event, event.getEntity().level(), event.getEntity(), sourceEntity);
        }
    }

    private static void execute(@Nullable Event event, Level world, Entity entity, LivingEntity sourceEntity) {
        if (entity == null || sourceEntity == null) return;

        //Attacking while an entity has the Ambush effect deals extra damage and applies slowness
        //It also applies Ambush Cooldown to the attacker mob.
        if (sourceEntity.hasEffect(ModMobEffects.AMBUSH.get())) {

            sourceEntity.removeEffect(MobEffects.INVISIBILITY);
            sourceEntity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            sourceEntity.removeEffect(ModMobEffects.AMBUSH.get());
            entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(
                    Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 6);

            if (entity instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide()) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
            }

            sourceEntity.addEffect(new MobEffectInstance(ModMobEffects.AMBUSH_COOLDOWN.get(), 300, 0, false, false));
        }

        //When a Grub attacks a mob, it will de-spawn and inflict the damaged mob with Parasitic Infection
        if (sourceEntity instanceof GrubEntity grubEntity && entity instanceof LivingEntity livingEntity) {
                if (!world.isClientSide) {

                    grubEntity.discard();
                    world.levelEvent(2001, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()),
                            Block.getId(Blocks.CRIMSON_STEM.defaultBlockState()));
                    world.playSound(null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()),
                            SoundEvents.BEEHIVE_ENTER, SoundSource.NEUTRAL, 1, 1);
                    if (!livingEntity.hasEffect(ModMobEffects.PARASITIC_INFECTION.get())) {
                        livingEntity.addEffect(new MobEffectInstance(ModMobEffects.PARASITIC_INFECTION.get(), 2400, 1));
                    }
                }
        }
    }

    //Right-click events
    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != event.getEntity().getUsedItemHand()) {
            return;
        }

        Entity targetEntity = event.getTarget();
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();

        //Right-clicking a tamed Arthropod with a Raw Grub will heal it for 10 health.
        if (itemStack.getItem() == ModItems.RAW_GRUB.get() && targetEntity instanceof LivingEntity livingEntity) {
            if (
                    (livingEntity.getMobType() == MobType.ARTHROPOD ||
                    isCommonArthropodName(livingEntity.getName().getString())) &&
                    livingEntity instanceof TamableAnimal tamableAnimal &&
                    tamableAnimal.isOwnedBy(player))
            {
                if (!event.getLevel().isClientSide) {

                    livingEntity.heal(10.0F);

                    ServerLevel serverLevel = (ServerLevel) event.getLevel();
                    double x = livingEntity.getX();
                    double y = livingEntity.getY() + livingEntity.getEyeHeight();
                    double z = livingEntity.getZ();
                    for (int i = 0; i < 10; i++) {
                        double offsetX = (serverLevel.random.nextDouble() - 0.5) * 2.0;
                        double offsetY = (serverLevel.random.nextDouble() - 0.5) * 2.0;
                        double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 2.0;
                        serverLevel.sendParticles(
                                ParticleTypes.HAPPY_VILLAGER,
                                x + offsetX, y + offsetY, z + offsetZ,
                                0, 0, 0, 0, 1.0);
                    }

                    playEatSound(event.getLevel(), livingEntity);

                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    private static void playEatSound(Level level, LivingEntity entity) {
        BlockPos pos = entity.blockPosition();

        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F);
        infested.queueServerWork(5, () -> level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F));
        infested.queueServerWork(10, () -> level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F));

    }


    //Block break events
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event != null && event.getState() != null) {
            execute(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z) {

        //Jewel Beetle Spawning
        if (world instanceof ServerLevel serverLevel) {
            BlockState state = world.getBlockState(BlockPos.containing(x, y, z));
            Block block = state.getBlock();
            EntitySpawnInfo spawnInfo = BlockEntityMapping.BLOCK_ENTITY_MAP.get(block);

            if (spawnInfo != null) {
                double chance = spawnInfo.spawnChance;
                if (Math.random() < chance) {
                    Entity entity = spawnInfo.entityType.create(serverLevel);
                    if (entity != null) {
                        entity.moveTo(x + 0.5, y, z + 0.5, 0, 0);
                        serverLevel.addFreshEntity(entity);
                    }
                }
            }

            //Silverfish Eggs have a 50% chance to drop from infested variants of blocks.
            if (state.is(BlockTags.create(new ResourceLocation("infested:infested_blocks")))) {
                if (Math.random() < 0.5) {
                    ItemEntity entityToSpawn = new ItemEntity(serverLevel, x, y, z, new ItemStack(ModItems.SILVERFISH_EGGS.get()));
                    entityToSpawn.setPickUpDelay(10);
                    serverLevel.addFreshEntity(entityToSpawn);
                }
            }

            //Bee Eggs have a 50% chance to drop from bee nests and beehives.
            if (state.is(Blocks.BEEHIVE) || state.is(Blocks.BEE_NEST)) {
                if (Math.random() < 0.5) {
                    ItemEntity entityToSpawn = new ItemEntity(serverLevel, x, y, z, new ItemStack(ModItems.BEE_EGGS.get()));
                    entityToSpawn.setPickUpDelay(10);
                    serverLevel.addFreshEntity(entityToSpawn);
                }
            }
        }
    }


        //Entity Death Events
        @SubscribeEvent
        public static void onEntityDeath(LivingDeathEvent event) {
            if (event != null && event.getEntity() != null) {
                execute(event, event.getEntity().level(), event.getEntity());
            }
        }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        if (entity == null || world == null) {
            return;
        }


        //Minions won't send death messages (it gets spammy)
        if (entity instanceof TamedSilverfishEntity silverfish) {
            if (silverfish.isTame()) {
                silverfish.setOwnerUUID(null);
            }
        }

        if (entity instanceof SpiderMinionEntity spider) {
            if (spider.isTame()) {
                spider.setOwnerUUID(null);
            }
        }

        if (entity instanceof BeeMinionEntity bee) {
            if (bee.isTame()) {
                bee.setOwnerUUID(null);
            }
        }

        //Spiders have a 5% chance to drop a Spider Egg
        if (entity instanceof Spider) {
            if (Math.random() >= 0.95) {
                if (world instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
                            new ItemStack(ModItems.SPIDER_EGG.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }

        //Cave Spiders have a 20% chance to drop a Spider Egg
        if (entity instanceof CaveSpider) {
            if (Math.random() >= 0.8) {
                if (world instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
                            new ItemStack(ModItems.SPIDER_EGG.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }

        //Silverfish have a 10% chance to drop Silverfish Eggs
        if (entity instanceof Silverfish) {
            if (Math.random() >= 0.9) {
                if (world instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
                            new ItemStack(ModItems.SILVERFISH_EGGS.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }

        //Bees have a 10% chance to drop Bee Eggs
        if (entity instanceof Bee && !(entity instanceof AttackBeeEntity)) {
            if (Math.random() >= 0.9) {
                if (world instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (entity.getX()), (entity.getY()), (entity.getZ()),
                            new ItemStack(ModItems.BEE_EGGS.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
            }
        }

        //An entity that dies with the Parasitic Infection effect will spawn a Crimson Beetle
        if (entity instanceof LivingEntity livingEntity &&
                livingEntity.hasEffect(ModMobEffects.PARASITIC_INFECTION.get())) {

            infested.queueServerWork(20, () -> {
                if (world instanceof ServerLevel _level) {
                    _level.sendParticles(ParticleTypes.POOF, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
                    _level.playSound(null, new BlockPos((int) livingEntity.getX(), (int) livingEntity.getY(), (int) livingEntity.getZ()),
                            SoundEvents.SNIFFER_EGG_HATCH, SoundSource.NEUTRAL, 1, 2);

                    EntityType<?> crimsonBeetle = ModEntities.CRIMSON_BEETLE.get();
                    Entity beetleEntity = crimsonBeetle.create(_level);
                    if (beetleEntity != null) {
                        beetleEntity.moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), _level.getRandom().nextFloat() * 360F, 0);
                        _level.addFreshEntity(beetleEntity);
                    }
                }
            });

        }

    }

    //Helper methods and stuff

    //some common bug names because y'all don't know how to tag your gosh darn arthropods
    private static boolean isCommonArthropodName(String name) {
        String lowerCaseName = name.toLowerCase();
        return lowerCaseName.contains("spider") ||
                lowerCaseName.contains("beetle") ||
                lowerCaseName.contains("bee") ||
                lowerCaseName.contains("grasshopper") ||
                lowerCaseName.contains("dragonfly") ||
                lowerCaseName.contains("bug") ||
                lowerCaseName.contains("insect") ||
                lowerCaseName.contains("swarmer");
    }



}
