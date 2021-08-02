package com.qsided.txpbmod;

import com.qsided.txpbmod.config.Config;
import com.qsided.txpbmod.events.PotionEvents;
import com.qsided.txpbmod.lists.PotionList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("txpb")
public class TemporaryXPBoosters
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "txpb";


    public TemporaryXPBoosters() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        PotionList.EFFECTS.register(bus);
        PotionList.POTIONS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, "txpb-common.toml");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PotionEvents.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        if (Config.COMMON.allowBrewing.get()) {
            System.out.println("Registered brewing recipes! Code: oEwlkkAc");
            if (ModList.get().isLoaded("pmmo")) {
                event.enqueueWork( () -> {
                    BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.STRONG_SWIFTNESS)), Ingredient.fromStacks(new ItemStack(Items.AZURE_BLUET)), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.AGILITY_BOOSTER_POTION.get()));
                } );
            }
            event.enqueueWork( () -> {
                BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.THICK)), Ingredient.fromStacks(new ItemStack(Items.NETHER_STAR)), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), PotionList.DOUBLE_VANILLA_BOOSTER_POTION.get()));
            } );
        }
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }
}