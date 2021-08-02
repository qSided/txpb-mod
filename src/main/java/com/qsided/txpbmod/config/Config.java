package com.qsided.txpbmod.config;

import com.qsided.txpbmod.TemporaryXPBoosters;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = TemporaryXPBoosters.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static class Common
    {
        public final ForgeConfigSpec.BooleanValue allowBrewing;
        public final ForgeConfigSpec.IntValue boosterDuration;
        public final ForgeConfigSpec.DoubleValue boosterAmount;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("TemporaryXPBoosters' Config")
                    .push("txpb");

            allowBrewing = builder
                    .comment("Should brewing xp booster potions be allowed?")
                    .translation("txpb.configgui.allowBrewing")
                    .worldRestart()
                    .define("allowBrewing", true);
            boosterDuration = builder
                    .comment("How long should boosters last? (In seconds)")
                    .translation("txpb.configgui.boosterDuration")
                    .worldRestart()
                    .defineInRange("boosterDuration", 300, 1, 9999999);
            boosterAmount = builder
                    .comment("What percentage should boosters increase skill XP?")
                    .translation("txpb.configgui.boosterAmount")
                    .worldRestart()
                    .defineInRange("boosterAmount", 25.0, 1, 9999999);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {

    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading event) {

    }
}