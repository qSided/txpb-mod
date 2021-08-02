package com.qsided.txpbmod.events;

import com.qsided.txpbmod.TemporaryXPBoosters;
import com.qsided.txpbmod.config.Config;
import com.qsided.txpbmod.lists.PotionList;
import harmonised.pmmo.api.APIUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashMap;

public class PotionEvents {

    public static HashMap<String, Double> xpBooster = new HashMap<>();

    @SubscribeEvent
    public static void playerReceivesEffect(PotionEvent.PotionAddedEvent event) {
        System.out.println("TXPB: player received effect");
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (!player.world.isRemote) {
                System.out.println("TXPB: is serverplayer");
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                System.out.println("TXPB: has effect: " + player.isPotionActive(PotionList.AGILITY_BOOSTER_EFFECT.get()));
                for (RegistryObject<Effect> registryObject : PotionList.effects) {
                    for (String skillToBoost : PotionList.skills) {
                        System.out.println("TXPB: for loop working");
                        Effect currentEffect = registryObject.get();
                        addBooster(serverPlayer, player, skillToBoost, currentEffect, event);
                    }
                }
            }
        }

    @SubscribeEvent
    public static void playerProducesMobXP(LivingExperienceDropEvent event) {
        PlayerEntity player = event.getAttackingPlayer();
        if (player.isPotionActive(PotionList.DOUBLE_VANILLA_BOOSTER_EFFECT.get())) {
            event.setDroppedExperience(event.getOriginalExperience() * 4);
            System.out.println("TXPB: Multiplied mob xp");
        }
    }

    @SubscribeEvent
    public static void playerProducesBlockXP(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.isPotionActive(PotionList.DOUBLE_VANILLA_BOOSTER_EFFECT.get())) {
            event.setExpToDrop(event.getExpToDrop() * 4);
            System.out.println("TXPB: Multiplied block xp");
        }
    }

    @SubscribeEvent
    public static void playerLosesEffect(PotionEvent.PotionRemoveEvent event) {
        System.out.println("TXPB: player lost effect");
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (!player.world.isRemote) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                System.out.println("TXPB: is serverplayer");
                for (RegistryObject<Effect> registryObject : PotionList.effects) {
                    for (String skillToBoost : PotionList.skills) {
                        System.out.println("TXPB: for loop working");
                        Effect currentEffect = registryObject.get();
                        removeBooster(serverPlayer, player, skillToBoost, currentEffect, event);
                    }
                }
            }
        }

    public static void removeBooster(ServerPlayerEntity serverPlayer, PlayerEntity player, String skillToBoost, Effect effect, PotionEvent.PotionRemoveEvent event) {
        System.out.println("TXPB: processEffect fired");
        System.out.println("TXPB: found map: " + APIUtils.getXpBoostMap(player, TemporaryXPBoosters.MOD_ID + "." + skillToBoost));
        System.out.println("TXPB: does contain key = " + APIUtils.getXpBoostMap(player, TemporaryXPBoosters.MOD_ID + "." + skillToBoost).containsKey(skillToBoost));
        if (effect == event.getPotion()) {
            if (APIUtils.getXpBoostMap(player, TemporaryXPBoosters.MOD_ID + "." + skillToBoost).containsKey(skillToBoost)) {
                APIUtils.removePlayerXpBoost(serverPlayer, TemporaryXPBoosters.MOD_ID + "." + skillToBoost);
                System.out.println("TXPB: player xp boost removed");
            }
        }
    }

    public static void addBooster(ServerPlayerEntity serverPlayer, PlayerEntity player, String skillToBoost, Effect effect, PotionEvent.PotionAddedEvent event) {
        if (effect == event.getPotionEffect().getPotion()) {
            if (!APIUtils.getXpBoostMap(player, TemporaryXPBoosters.MOD_ID + "." + skillToBoost).containsKey(skillToBoost)) {
                xpBooster.put(skillToBoost, Config.COMMON.boosterAmount.get());
                APIUtils.setPlayerXpBoost(serverPlayer, TemporaryXPBoosters.MOD_ID + "." + skillToBoost, xpBooster);
                System.out.println("TXPB: player xp boost added");
                xpBooster.clear();
            }
        }
    }
}