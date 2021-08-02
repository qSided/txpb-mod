package com.qsided.txpbmod.lists;

import com.qsided.txpbmod.BoosterEffect;
import com.qsided.txpbmod.TemporaryXPBoosters;
import com.qsided.txpbmod.config.Config;
import harmonised.pmmo.skills.Skill;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class PotionList {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, TemporaryXPBoosters.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, TemporaryXPBoosters.MOD_ID);

    public static final RegistryObject<Effect> AGILITY_BOOSTER_EFFECT = EFFECTS.register("agility_booster", () -> new BoosterEffect(EffectType.BENEFICIAL, 0xd + Skill.getSkillColor(Skill.AGILITY.name)));
    public static final RegistryObject<Potion> AGILITY_BOOSTER_POTION = POTIONS.register("agility_booster", () -> new Potion(new EffectInstance(AGILITY_BOOSTER_EFFECT.get(), Config.COMMON.boosterDuration.get() * 20)));
    public static final RegistryObject<Effect> DOUBLE_VANILLA_BOOSTER_EFFECT = EFFECTS.register("vanilla_booster", () -> new BoosterEffect(EffectType.BENEFICIAL, 0xdf6ff00));
    public static final RegistryObject<Potion> DOUBLE_VANILLA_BOOSTER_POTION = POTIONS.register("vanilla_booster", () -> new Potion(new EffectInstance(DOUBLE_VANILLA_BOOSTER_EFFECT.get(), Config.COMMON.boosterDuration.get() * 20)));

    public static ArrayList<RegistryObject<Effect>> effects = new ArrayList<RegistryObject<Effect>>();
    static {
        effects.add(AGILITY_BOOSTER_EFFECT);

    }

    public static ArrayList<RegistryObject<Potion>> potions = new ArrayList<RegistryObject<Potion>>();
    static {
        potions.add(AGILITY_BOOSTER_POTION);

    }

    public static ArrayList<String> skills = new ArrayList<String>();
    static {
        skills.add(Skill.AGILITY.toString().toLowerCase());

    }
}
