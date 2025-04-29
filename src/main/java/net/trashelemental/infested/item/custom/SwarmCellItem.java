package net.trashelemental.infested.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.magic.enchantments.custom.ConjuredSwarmEnchantment;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;
import net.trashelemental.infested.junkyard_lib.entity.method.SummonMethods;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class SwarmCellItem extends Item {

    private final int maxMinions;
    private final Supplier<EntityType<?>> entityTypeSupplier;
    private final int maxDurability;
    private final Item repairItem;

    private static final int CHECK_INTERVAL = 200;

    private static final String AUTO_MODE_KEY = "SwarmCellAutoMode";
    private static final String COUNTDOWN_TICKS_KEY = "SwarmCellCountdownTicks";

    public SwarmCellItem(Properties properties, int maxMinions, Supplier<EntityType<?>> entityTypeSupplier, int maxDurability, Item repairItem) {
        super(properties.durability(maxDurability));
        this.maxMinions = maxMinions;
        this.entityTypeSupplier = entityTypeSupplier;
        this.maxDurability = maxDurability;
        this.repairItem = repairItem;
    }

    public int getBaseMinions() {
        return maxMinions;
    }

    public int getMaxMinions(Player player) {
        int bonusMinions = 0;

        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.BEE_HELMET.get()) bonusMinions++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.BEE_CHESTPLATE.get()) bonusMinions++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.BEE_LEGGINGS.get()) bonusMinions++;
        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.BEE_BOOTS.get()) bonusMinions++;

        return maxMinions + bonusMinions;
    }


    public EntityType<?> getEntityType() {
        return entityTypeSupplier.get();
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == repairItem;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 20;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment instanceof ConjuredSwarmEnchantment;
    }

    private boolean isAutomaticMode(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean(AUTO_MODE_KEY);
    }

    //Hover text that tells you how many minions the item you're using can summon
    //Also displays special text when automatic mode is on
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        Component coloredPart = Component.literal("Max Minions: ").withStyle(ChatFormatting.BLUE);
        Component uncoloredPart = Component.literal(String.valueOf(maxMinions));

        tooltipComponents.add(Component.empty().append(coloredPart).append(uncoloredPart));


        if (isAutomaticMode(stack)) {
            tooltipComponents.add(Component.literal("Automatic Mode On").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }

    //When used on a minion entity, will despawn all nearby minions that you own
    private boolean used = false;

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        Level level = player.level();

        if (!level.isClientSide && interactionTarget instanceof TamableAnimal minion) {
            CompoundTag minionTag = minion.getPersistentData().getCompound("SwarmCellMinionTag");

            if (minionTag != null && minionTag.contains("Origin") && "SwarmCell".equals(minionTag.getString("Origin"))
                    && Objects.equals(minion.getOwnerUUID(), player.getUUID())) {

                for (Entity entity : level.getEntities(player, player.getBoundingBox().inflate(20))) {
                    if (entity instanceof TamableAnimal nearbyMinion) {
                        CompoundTag nearbyMinionTag = nearbyMinion.getPersistentData().getCompound("SwarmCellMinionTag");

                        if (nearbyMinionTag != null && nearbyMinionTag.contains("Origin")
                                && "SwarmCell".equals(nearbyMinionTag.getString("Origin"))
                                && Objects.equals(nearbyMinion.getOwnerUUID(), player.getUUID())) {
                            ParticleMethods.ParticlesAroundServerSide(level, ParticleTypes.POOF,
                                    nearbyMinion.getX(), nearbyMinion.getY(), nearbyMinion.getZ(), 3, 0.1);
                            nearbyMinion.discard();
                        }
                    }
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BEEHIVE_ENTER, SoundSource.PLAYERS, 1.0F, 1.0F);

                used = true;

                return InteractionResult.SUCCESS;
            }
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }

    //Use Behavior
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack item = player.getItemInHand(usedHand);

        //Sets the swarm cell to manual mode if a mode hasn't been set yet.
        if (!item.hasTag()) {
            item.setTag(new CompoundTag());
            item.getTag().putBoolean(AUTO_MODE_KEY, true);
        }

        //No further logic if the item already was used to despawn minions
        if (used) {
            used = false;
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        }

        //Switches the mode if the player is crouching.
        if (player.isCrouching() && !level.isClientSide) {
            boolean currentMode = isAutomaticMode(item);
            item.getTag().putBoolean(AUTO_MODE_KEY, !currentMode);

            String modeMessage = currentMode ? "Minions will not replenish automatically." : "Minions will replenish automatically.";
            player.displayClientMessage(Component.literal(modeMessage), true);

            //Reset the countdown ticks to 1.
            item.getTag().putInt(COUNTDOWN_TICKS_KEY, 1);

            return InteractionResultHolder.success(item);
        }

        if (!level.isClientSide) {

            int currentMinionCount = getCurrentMinionCount(level, player);
            int minionsToSummon = getMaxMinions(player) - currentMinionCount;

            //Summon as many minions as is needed to reach the cap of the swarm cell item you're using
            if (minionsToSummon > 0) {

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BEEHIVE_EXIT, SoundSource.PLAYERS, 1.0F, 1.0F);

                player.swing(InteractionHand.MAIN_HAND);

                for (int i = 0; i < minionsToSummon; i++) {
                    spawnMinion(level, player, item);
                }

                return InteractionResultHolder.success(player.getItemInHand(usedHand));

            } else {
                player.displayClientMessage(Component.literal("Max number of minions present!"), true);
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }

    //Tick Behavior for Automatic mode
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (entity instanceof Player player && isAutomaticMode(stack)) {
            int ticksSinceLastCheck = stack.hasTag() ? stack.getTag().getInt(COUNTDOWN_TICKS_KEY) : 0;
            ticksSinceLastCheck++;

            if (ticksSinceLastCheck >= CHECK_INTERVAL) {
                ticksSinceLastCheck = 0;

                if (!level.isClientSide) {
                    int currentMinionCount = getCurrentMinionCount(level, player);
                    if (currentMinionCount < getMaxMinions(player)) {
                        spawnMinion(level, player, stack);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.BEEHIVE_EXIT, SoundSource.PLAYERS, 0.5F, 1.0F);
                    }
                }
            }

            stack.getTag().putInt(COUNTDOWN_TICKS_KEY, ticksSinceLastCheck);
        }
    }

    //Helper Methods
    private void spawnMinion(Level level, Player player, ItemStack item) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        BlockPos spawnPos = player.blockPosition().below();
        Entity entity = getEntityType().create(serverLevel);

        if (!(entity instanceof TamableAnimal tamableMinion)) return;

        tamableMinion.setHealth(Math.min(4, tamableMinion.getMaxHealth()));

        if (tamableMinion instanceof MinionEntity minion) {
            SummonMethods.summonMinion(level, spawnPos, minion, 0, true, player);
        } else {
            SummonMethods.summonTamedAnimal(level, spawnPos, tamableMinion, player);
        }

        CompoundTag minionTag = new CompoundTag();
        minionTag.putString("Origin", "SwarmCell");
        tamableMinion.getPersistentData().put("SwarmCellMinionTag", minionTag);

        if (item.isEnchanted() && player.experienceLevel > 0) {
            player.giveExperiencePoints(-2);
        } else {
            item.hurtAndBreak(1, player, (e) -> e.broadcastBreakEvent(player.getUsedItemHand()));
        }
    }

    private int getCurrentMinionCount(Level level, Player player) {
        int count = 0;
        for (Entity entity : level.getEntities(player, player.getBoundingBox().inflate(20))) {
            if (entity instanceof TamableAnimal tamableMinion) {
                CompoundTag minionTag = tamableMinion.getPersistentData().getCompound("SwarmCellMinionTag");
                if (minionTag != null && minionTag.contains("Origin") && "SwarmCell".equals(minionTag.getString("Origin"))
                        && tamableMinion.getOwnerUUID() != null && tamableMinion.getOwnerUUID().equals(player.getUUID())) {
                    count++;
                }
            }
        }
        return count;
    }

}
