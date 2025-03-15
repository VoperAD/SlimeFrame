package me.voper.slimeframe.implementation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.cryptomorin.xseries.XMaterial;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.misc.AlloyIngot;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.core.attributes.FreezingItem;
import me.voper.slimeframe.core.managers.SettingsManager;
import me.voper.slimeframe.implementation.groups.Groups;
import me.voper.slimeframe.implementation.items.components.PrimeComponents;
import me.voper.slimeframe.implementation.items.components.UtilsComponents;
import me.voper.slimeframe.implementation.items.gear.CryogenicArmorPiece;
import me.voper.slimeframe.implementation.items.generators.CumulativeGenerator;
import me.voper.slimeframe.implementation.items.machines.*;
import me.voper.slimeframe.implementation.items.multiblocks.Foundry;
import me.voper.slimeframe.implementation.items.relics.Relic;
import me.voper.slimeframe.implementation.items.resources.*;
import me.voper.slimeframe.implementation.items.tools.*;

import net.md_5.bungee.api.ChatColor;

public final class SFrameItems {

    public static final Set<SlimefunItem> ALLOYS = new HashSet<>();
    public static final HashMap<ItemStack, SlimefunItemStack> ALLOY_PLATES_MAP = new HashMap<>();

    private final SlimeFrame plugin;
    private final SettingsManager settingsManager = SlimeFrame.getSettingsManager();

    public SFrameItems(@Nonnull SlimeFrame plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        this.registerRelics();
        this.registerMultiBlocks();
        this.registerResources();
        this.registerMachines();
        this.registerGenerators();
        this.registerGear();
        this.registerUtilsAndTools();
        this.registerPrimeComponents();
        this.registerFreezingItems();
    }

    private void registerRelics() {
        Relic.create(SFrameStacks.LITH_A1).register(plugin);
        Relic.create(SFrameStacks.LITH_T1).register(plugin);
        Relic.create(SFrameStacks.LITH_O1).register(plugin);
        Relic.create(SFrameStacks.LITH_C1).register(plugin);

        Relic.create(SFrameStacks.MESO_B1).register(plugin);
        Relic.create(SFrameStacks.MESO_W1).register(plugin);
        Relic.create(SFrameStacks.MESO_S1).register(plugin);
        Relic.create(SFrameStacks.MESO_C1).register(plugin);

        Relic.create(SFrameStacks.NEO_A1).register(plugin);
        Relic.create(SFrameStacks.NEO_C1).register(plugin);
        Relic.create(SFrameStacks.NEO_D1).register(plugin);
        Relic.create(SFrameStacks.NEO_P1).register(plugin);

        Relic.create(SFrameStacks.AXI_F1).register(plugin);
        Relic.create(SFrameStacks.AXI_N1).register(plugin);
        Relic.create(SFrameStacks.AXI_G1).register(plugin);
        Relic.create(SFrameStacks.AXI_T1).register(plugin);
    }

    private void registerResources() {
        new SpecialOre(SFrameStacks.PYROL, Material.END_STONE, SpecialOre.Rarity.COMMON).register(plugin);
        new SpecialOre(SFrameStacks.TRAVORIDE, Material.STONE, SpecialOre.Rarity.COMMON).register(plugin);
        new SpecialOre(SFrameStacks.ADRAMALIUM, Material.NETHERRACK, SpecialOre.Rarity.COMMON).register(plugin);
        new SpecialOre(SFrameStacks.FERROS, new Material[]{Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE}, SpecialOre.Rarity.UNCOMMON).register(plugin);
        new SpecialOre(SFrameStacks.VENEROL, new Material[]{Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE}, SpecialOre.Rarity.UNCOMMON).register(plugin);
        new SpecialOre(SFrameStacks.NAMALON, Material.NETHER_QUARTZ_ORE, SpecialOre.Rarity.UNCOMMON).register(plugin);
        new SpecialOre(SFrameStacks.AURON, new Material[]{Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE}, SpecialOre.Rarity.RARE).register(plugin);
        new SpecialOre(SFrameStacks.HESPERON, new Material[]{Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE}, SpecialOre.Rarity.RARE).register(plugin);
        new SpecialOre(SFrameStacks.THAUMICA, Material.NETHER_GOLD_ORE, SpecialOre.Rarity.RARE).register(plugin);

        registerAlloy(SFrameStacks.PYROTIC_ALLOY, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.PYROL, 2).item(), SlimefunItems.REINFORCED_ALLOY_INGOT.item(), SFrameStacks.RUBEDO.item(), SFrameStacks.CRYOTIC.item()});
        registerAlloy(SFrameStacks.ADRAMAL_ALLOY, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.ADRAMALIUM, 2).item(), new SlimefunItemStack(SFrameStacks.TRAVORIDE, 2).item(), SFrameStacks.PLASTIDS.item(), SFrameStacks.NEURODES.item()});
        registerAlloy(SFrameStacks.TRAVOCYTE_ALLOY, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.TRAVORIDE, 2).item(), SFrameStacks.PLASTIDS.item(), new SlimefunItemStack(SFrameStacks.SALVAGE, 16).item()});
        registerAlloy(SFrameStacks.FERSTEEL_ALLOY, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.FERROS, 2).item(), SFrameStacks.RUBEDO.item(), SFrameStacks.PLASTIDS.item()});
        registerAlloy(SFrameStacks.VENERDO_ALLOY, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.VENEROL, 2).item(), SFrameStacks.RUBEDO.item(), SFrameStacks.GALLIUM.item()});
        registerAlloy(SFrameStacks.DEVOLVED_NAMALON, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.NAMALON, 2).item(), new SlimefunItemStack(SFrameStacks.FERROS, 2).item(), SFrameStacks.RUBEDO.item(), SFrameStacks.NEURODES.item()});
        registerAlloy(SFrameStacks.AUROXIUM_ALLOY, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.AURON, 2).item(), SFrameStacks.MORPHICS.item(), SlimefunItems.BLISTERING_INGOT_3.item()});
        registerAlloy(SFrameStacks.HESPAZYM_ALLOY, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.HESPERON, 2).item(), SFrameStacks.PLASTIDS.item(), SFrameStacks.MORPHICS.item(), SFrameStacks.CRYOTIC.item()});
        registerAlloy(SFrameStacks.THAUMIC_DISTILLATE, new ItemStack[]{
                new SlimefunItemStack(SFrameStacks.THAUMICA, 2).item(), new SlimefunItemStack(SFrameStacks.VENEROL, 2).item(), SFrameStacks.GALLIUM.item(), SlimefunItems.REINFORCED_ALLOY_INGOT.item()});

        ALLOYS.stream()
                .sorted(Comparator.comparing(SlimefunItem::getId))
                .forEach(this::registerAlloyPlate);

        ItemStack[] plates = ALLOY_PLATES_MAP.values()
                .stream()
                .sorted(Comparator.comparing(SlimefunItemStack::getItemId))
                .map(SlimefunItemStack::item)
                .toList()
                .toArray(new ItemStack[9]);

        new Resource(SFrameStacks.CONDENSED_PLATE, plates).register(plugin);

        new Cryotic().register(plugin);
        new CoolantCanister().register(plugin);
        new Resource(SFrameStacks.DILUTED_THERMIA, ThermiaExtractor.RECIPE_TYPE, new ItemStack[]{
                null, null, null, new SlimefunItemStack(SFrameStacks.COOLANT_CANISTER, 5).item()
        }).register(plugin);

        new Resource(SFrameStacks.TELLURIUM_FRAGMENT, TelluriumFragmentsSynthesizer.RECIPE_TYPE).register(plugin);
        new Resource(SFrameStacks.TELLURIUM, SFrameStacks.TELLURIUM_FRAGMENT.item()).register(plugin);

        Resource.createRadioactive(SFrameStacks.BOOSTED_TELLURIUM, Radioactivity.VERY_DEADLY, new ItemStack[]{
                SFrameStacks.ARGON_CRYSTAL.item(), SFrameStacks.CRYOTIC.item(), SFrameStacks.DILUTED_THERMIA.item(),
                SFrameStacks.CRYOTIC.item(), SFrameStacks.TELLURIUM.item(), SFrameStacks.CRYOTIC.item(),
                SFrameStacks.DILUTED_THERMIA.item(), SFrameStacks.CRYOTIC.item(), SFrameStacks.ARGON_CRYSTAL.item()
        }).register(plugin);

        Resource.createRadioactive(SFrameStacks.RUBEDO, Radioactivity.VERY_DEADLY, new ItemStack[]{
                XMaterial.REDSTONE_BLOCK.parseItem(), SlimefunItems.POWER_CRYSTAL.item(), XMaterial.REDSTONE_BLOCK.parseItem(),
                SlimefunItems.POWER_CRYSTAL.item(), SlimefunItems.PLUTONIUM.item(), SlimefunItems.POWER_CRYSTAL.item(),
                XMaterial.REDSTONE_BLOCK.parseItem(), SlimefunItems.POWER_CRYSTAL.item(), XMaterial.REDSTONE_BLOCK.parseItem()
        }).register(plugin);

        Resource.createRadioactive(SFrameStacks.ARGON_CRYSTAL, Radioactivity.VERY_HIGH, new ItemStack[]{
                XMaterial.AMETHYST_SHARD.parseItem(), SlimefunItems.BOOSTED_URANIUM.item(), SFrameStacks.TELLURIUM.item(),
                SlimefunItems.BOOSTED_URANIUM.item(), XMaterial.END_CRYSTAL.parseItem(), SlimefunItems.BOOSTED_URANIUM.item(),
                SFrameStacks.TELLURIUM.item(), SlimefunItems.BOOSTED_URANIUM.item(), XMaterial.AMETHYST_SHARD.parseItem()
        }).register(plugin);

        new Resource(SFrameStacks.CUBIC_DIODES, new ItemStack[]{
                SlimefunItems.SILICON.item(), SFrameStacks.MORPHICS.item(), SlimefunItems.SILICON.item(),
                SFrameStacks.GALLIUM.item(), SlimefunItems.NETHER_ICE.item(), SFrameStacks.GALLIUM.item(),
                SlimefunItems.SILICON.item(), SFrameStacks.MORPHICS.item(), SlimefunItems.SILICON.item()
        }).register(plugin);

        new Resource(SFrameStacks.PLASTIDS, new ItemStack[]{
                new ItemStack(Material.SPIDER_EYE), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.FERMENTED_SPIDER_EYE),
                new ItemStack(Material.CRIMSON_FUNGUS), new ItemStack(Material.POISONOUS_POTATO), new ItemStack(Material.WARPED_FUNGUS),
                new ItemStack(Material.FERMENTED_SPIDER_EYE), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.SPIDER_EYE)
        }).register(plugin);

        new Resource(SFrameStacks.CONTROL_MODULE, new ItemStack[]{
                SFrameStacks.FERSTEEL_ALLOY.item(), SFrameStacks.TRAVOCYTE_ALLOY.item(), SFrameStacks.FERSTEEL_ALLOY.item(),
                SFrameStacks.CUBIC_DIODES.item(), SlimefunItems.PROGRAMMABLE_ANDROID.item(), SFrameStacks.CUBIC_DIODES.item(),
                SFrameStacks.FERSTEEL_ALLOY.item(), SFrameStacks.TRAVOCYTE_ALLOY.item(), SFrameStacks.FERSTEEL_ALLOY.item()
        }).register(plugin);

        new Resource(SFrameStacks.GALLIUM, Recycler.RECIPE_TYPE, new ItemStack[]{
                null, null, null, null, new SlimefunItemStack(SlimefunItems.ADVANCED_CIRCUIT_BOARD, 8).item()
        }).register(plugin);

        new Resource(SFrameStacks.SALVAGE, Recycler.RECIPE_TYPE, new ItemStack[]{
                null, null, null, null, CustomItemStack.create(Material.FURNACE, ChatColor.AQUA + "Any Machine or Generator")
        }, new SlimefunItemStack(SFrameStacks.SALVAGE, 8).item()).register(plugin);

        new Resource(SFrameStacks.PRISMATIC_ENERGIZED_CORE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(), SFrameStacks.ARGON_CRYSTAL.item(), getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(),
                SFrameStacks.DILUTED_THERMIA.item(), SFrameStacks.CONDENSED_PLATE.item(), SFrameStacks.DILUTED_THERMIA.item(),
                getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(), SFrameStacks.ARGON_CRYSTAL.item(), getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item()
        }).register(plugin);

        new MobDropItem(Groups.RESOURCES, SFrameStacks.MORPHICS)
                .setMobChanceMap(settingsManager.getDropChanceMap(SettingsManager.ConfigField.MORPHICS_CHANCE))
                .register(plugin);

        new MobDropItem(Groups.RESOURCES, SFrameStacks.NEURAL_SENSORS)
                .setMobChanceMap(settingsManager.getDropChanceMap(SettingsManager.ConfigField.NEURAL_SENSORS))
                .register(plugin);

        new MobDropItem(Groups.RESOURCES, SFrameStacks.NEURODES)
                .setMobChanceMap(settingsManager.getDropChanceMap(SettingsManager.ConfigField.NEURODES_CHANCE))
                .register(plugin);

        new MobDropItem(Groups.RESOURCES, SFrameStacks.OROKIN_CELL)
                .setMobChanceMap(settingsManager.getDropChanceMap(SettingsManager.ConfigField.OROKIN_CELL_CHANCE))
                .register(plugin);

    }

    private void registerMultiBlocks() {
        new Foundry().register(plugin);
    }

    private void registerMachines() {
        new ArtificialMangrove(Groups.MACHINES, SFrameStacks.ARTIFICIAL_MANGROVE, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.FERSTEEL_ALLOY.item(), SFrameStacks.ADRAMAL_ALLOY.item(), SFrameStacks.FERSTEEL_ALLOY.item(),
                SFrameStacks.TRAVOCYTE_ALLOY.item(), XMaterial.MUD.parseItem(), SFrameStacks.TRAVOCYTE_ALLOY.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setEnergyPerTick(128).register(plugin);

        new ArtificialMangrove(Groups.MACHINES, SFrameStacks.ADV_ARTIFICIAL_MANGROVE, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.HESPAZYM_ALLOY.item(), SFrameStacks.HESPAZYM_ALLOY.item(), SFrameStacks.HESPAZYM_ALLOY.item(),
                getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(), SFrameStacks.ARTIFICIAL_MANGROVE.item(), getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SFrameStacks.CONTROL_MODULE.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setProcessingSpeed(5).setEnergyPerTick(256).register(plugin);

        new ArtificialMangrove(Groups.MACHINES, SFrameStacks.PRIME_ARTIFICIAL_MANGROVE, Foundry.RECIPE_TYPE, new ItemStack[]{
                null, SFrameStacks.OROKIN_CELL.item(), null,
                getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(), SFrameStacks.ADV_ARTIFICIAL_MANGROVE.item(), getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_ARTIFICIAL_MANGROVE).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_ARTIFICIAL_MANGROVE).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_ARTIFICIAL_MANGROVE).item()
        }).setProcessingSpeed(30).setEnergyPerTick(512).register(plugin);

        new AutoTrader(Groups.MACHINES, SFrameStacks.AUTO_TRADER, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.THAUMIC_DISTILLATE).item(), SlimefunItems.VILLAGER_RUNE.item(), getAlloyPlate(SFrameStacks.THAUMIC_DISTILLATE).item(),
                getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(), XMaterial.CARTOGRAPHY_TABLE.parseItem(), getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(),
                SFrameStacks.DILUTED_THERMIA.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.DILUTED_THERMIA.item()
        }).setEnergyPerTick(1024).register(plugin);

        new BasaltGenerator(Groups.MACHINES, SFrameStacks.BASALT_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.FREEZER_2.item(), SlimefunItems.FREEZER_2.item(), SlimefunItems.FREEZER_2.item(),
                SlimefunItems.ELECTRIFIED_CRUCIBLE_2.item(), XMaterial.BASALT.parseItem(), SlimefunItems.ELECTRIFIED_CRUCIBLE_2.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setProduction(1).setEnergyPerTick(128).register(plugin);

        new BasaltGenerator(Groups.MACHINES, SFrameStacks.ADV_BASALT_GEN, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(), getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(), getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(),
                SFrameStacks.CRYOTIC.item(), SFrameStacks.BASALT_GENERATOR.item(), SFrameStacks.CRYOTIC.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SFrameStacks.CONTROL_MODULE.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setProduction(4).setEnergyPerTick(256).register(plugin);

        new BasaltGenerator(Groups.MACHINES, SFrameStacks.PRIME_BASALT_GEN, Foundry.RECIPE_TYPE, new ItemStack[]{
                null, SFrameStacks.OROKIN_CELL.item(), null,
                getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(), SFrameStacks.ADV_BASALT_GEN.item(), getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_BASALT_GEN).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_BASALT_GEN).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_BASALT_GEN).item()
        }).setProduction(64).setEnergyPerTick(512).register(plugin);

        new ChunkEater(SFrameStacks.CHUNK_EATER, new ItemStack[]{
                SFrameStacks.ARGON_CRYSTAL.item(), SlimefunItems.ENERGIZED_CAPACITOR.item(), SFrameStacks.ARGON_CRYSTAL.item(),
                SFrameStacks.PYROTIC_ALLOY.item(), XMaterial.SCULK.parseItem(), SFrameStacks.PYROTIC_ALLOY.item(),
                SlimefunItems.HEATING_COIL.item(), SFrameStacks.CONTROL_MODULE.item(), SlimefunItems.HEATING_COIL.item()
        }).setEnergyPerTick(1024).register(plugin);

        new ChunkEater(SFrameStacks.ADV_CHUNK_EATER, new ItemStack[]{
                getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(), SlimefunItems.ENERGIZED_CAPACITOR.item(), getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(),
                getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(), SFrameStacks.CHUNK_EATER.item(), getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(),
                SlimefunItems.PROGRAMMABLE_ANDROID_MINER.item(), SFrameStacks.CONTROL_MODULE.item(), SlimefunItems.PROGRAMMABLE_ANDROID_MINER.item()
        }).setCollectDestroyedBlocks().setEnergyPerTick(2048).register(plugin);

        new ChunkEater(SFrameStacks.PRIME_CHUNK_EATER, new ItemStack[]{
                SFrameStacks.RUBEDO.item(), SFrameStacks.OROKIN_CELL.item(), SFrameStacks.RUBEDO.item(),
                SFrameStacks.BOOSTED_TELLURIUM.item(), SFrameStacks.ADV_CHUNK_EATER.item(), SFrameStacks.BOOSTED_TELLURIUM.item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_CHUNK_EATER).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_CHUNK_EATER).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_CHUNK_EATER).item()
        }).setLayerMode().setCollectDestroyedBlocks().setEnergyPerTick(4096).register(plugin);

        new ConcreteGenerator(Groups.MACHINES, SFrameStacks.CONCRETE_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.FERSTEEL_ALLOY.item(), SlimefunItems.RAINBOW_CONCRETE.item(), SFrameStacks.FERSTEEL_ALLOY.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), XMaterial.BRICKS.parseItem(), SlimefunItems.ELECTRIC_MOTOR.item(),
                SFrameStacks.CUBIC_DIODES.item(), SlimefunItems.BIG_CAPACITOR.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setEnergyPerTick(128).register(plugin);

        new ConcreteGenerator(Groups.MACHINES, SFrameStacks.ADV_CONCRETE_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.FERSTEEL_ALLOY.item(), SFrameStacks.FERSTEEL_ALLOY.item(), SFrameStacks.FERSTEEL_ALLOY.item(),
                getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(), SFrameStacks.CONCRETE_GENERATOR.item(), getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProcessingSpeed(5).setEnergyPerTick(256).register(plugin);

        new ConcreteGenerator(Groups.MACHINES, SFrameStacks.PRIME_CONCRETE_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                null, SFrameStacks.OROKIN_CELL.item(), null,
                getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(), SFrameStacks.ADV_CONCRETE_GENERATOR.item(), getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_CONCRETE_GENERATOR).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_CONCRETE_GENERATOR).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_CONCRETE_GENERATOR).item()
        }).setProcessingSpeed(30).setEnergyPerTick(512).register(plugin);

        new CryoticExtractor(Groups.MACHINES, SFrameStacks.CRYOTIC_EXTRACTOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.FERSTEEL_ALLOY.item(), SFrameStacks.RUBEDO.item(), SFrameStacks.FERSTEEL_ALLOY.item(),
                SFrameStacks.TRAVOCYTE_ALLOY.item(), XMaterial.BEACON.parseItem(), SFrameStacks.TRAVOCYTE_ALLOY.item(),
                SlimefunItems.HEATING_COIL.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.HEATING_COIL.item()
        }).setEnergyPerTick(512).register(plugin);

        new CryoticExtractor(Groups.MACHINES, SFrameStacks.ADV_CRYOTIC_EXTRACTOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(), getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(), getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(),
                SFrameStacks.TELLURIUM.item(), SFrameStacks.CRYOTIC_EXTRACTOR.item(), SFrameStacks.TELLURIUM.item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProcessingSpeed(4).setEnergyPerTick(1024).register(plugin);

        new CryoticExtractor(Groups.MACHINES, SFrameStacks.PRIME_CRYOTIC_EXTRACTOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.OROKIN_CELL.item(), SFrameStacks.OROKIN_CELL.item(), SFrameStacks.OROKIN_CELL.item(),
                SFrameStacks.GALLIUM.item(), SFrameStacks.ADV_CRYOTIC_EXTRACTOR.item(), SFrameStacks.GALLIUM.item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_CRYOTIC_EXTRACTOR).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_CRYOTIC_EXTRACTOR).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_CRYOTIC_EXTRACTOR).item()
        }).setProcessingSpeed(20).setEnergyPerTick(2048).register(plugin);

        new DustGenerator(Groups.MACHINES, SFrameStacks.DUST_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.ELECTRIC_DUST_WASHER_3.item(), SlimefunItems.ELECTRIC_GOLD_PAN_3.item(), SlimefunItems.ELECTRIC_ORE_GRINDER_3.item(),
                SFrameStacks.BOOSTED_TELLURIUM.item(), XMaterial.FURNACE.parseItem(), SFrameStacks.BOOSTED_TELLURIUM.item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setEnergyPerTick(256).register(plugin);

        new DustGenerator(Groups.MACHINES, SFrameStacks.ADV_DUST_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.THAUMIC_DISTILLATE.item(), SFrameStacks.THAUMIC_DISTILLATE.item(), SFrameStacks.THAUMIC_DISTILLATE.item(),
                getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(), SFrameStacks.DUST_GENERATOR.item(), getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProduction(4).setProcessingSpeed(2).setEnergyPerTick(512).register(plugin);

        new DustGenerator(Groups.MACHINES, SFrameStacks.PRIME_DUST_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.CONDENSED_PLATE.item(), SFrameStacks.OROKIN_CELL.item(), SFrameStacks.CONDENSED_PLATE.item(),
                SFrameStacks.CONDENSED_PLATE.item(), SFrameStacks.ADV_DUST_GENERATOR.item(), SFrameStacks.CONDENSED_PLATE.item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_DUST_GENERATOR).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_DUST_GENERATOR).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_DUST_GENERATOR).item()
        }).setProduction(32).setProcessingSpeed(4).setEnergyPerTick(1024).register(plugin);

        new FlowerGenerator(Groups.MACHINES, SFrameStacks.FLOWER_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.PYROTIC_ALLOY.item(), SFrameStacks.PYROTIC_ALLOY.item(), SFrameStacks.PYROTIC_ALLOY.item(),
                SFrameStacks.ADRAMAL_ALLOY.item(), SFrameStacks.NEURODES.item(), SFrameStacks.ADRAMAL_ALLOY.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setEnergyPerTick(128).register(plugin);

        new FlowerGenerator(Groups.MACHINES, SFrameStacks.ADV_FLOWER_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), SFrameStacks.BOOSTED_TELLURIUM.item(), SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                SlimefunItems.REINFORCED_PLATE.item(), SFrameStacks.FLOWER_GENERATOR.item(), SlimefunItems.REINFORCED_PLATE.item(),
                SlimefunItems.PROGRAMMABLE_ANDROID_FARMER.item(), SFrameStacks.CONTROL_MODULE.item(), SlimefunItems.PROGRAMMABLE_ANDROID_FARMER.item()
        }).setProduction(8).setEnergyPerTick(256).register(plugin);

        new FlowerGenerator(Groups.MACHINES, SFrameStacks.PRIME_FLOWER_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                null, SFrameStacks.OROKIN_CELL.item(), null,
                getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(), SFrameStacks.ADV_FLOWER_GENERATOR.item(), getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_FLOWER_GENERATOR).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_FLOWER_GENERATOR).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_FLOWER_GENERATOR).item()
        }).setProduction(32).setEnergyPerTick(512).register(plugin);

        new GlassGenerator(Groups.MACHINES, SFrameStacks.GLASS_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.AUROXIUM_ALLOY.item(), SlimefunItems.RAINBOW_GLASS.item(), SFrameStacks.AUROXIUM_ALLOY.item(),
                SlimefunItems.GOLD_24K.item(), XMaterial.GLASS.parseItem(), SlimefunItems.GOLD_24K.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setEnergyPerTick(128).register(plugin);

        new GlassGenerator(Groups.MACHINES, SFrameStacks.ADV_GLASS_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.RUBEDO.item(), SFrameStacks.RUBEDO.item(), SFrameStacks.RUBEDO.item(),
                getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(), SFrameStacks.GLASS_GENERATOR.item(), getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProduction(2).setProcessingSpeed(5).setEnergyPerTick(256).register(plugin);

        new GlassGenerator(Groups.MACHINES, SFrameStacks.PRIME_GLASS_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(), SFrameStacks.OROKIN_CELL.item(), getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(),
                getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(), SFrameStacks.ADV_GLASS_GENERATOR.item(), getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_GLASS_GENERATOR).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_GLASS_GENERATOR).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_GLASS_GENERATOR).item()
        }).setProduction(6).setProcessingSpeed(30).setEnergyPerTick(512).register(plugin);

        new Putrifier(SFrameStacks.PUTRIFIER, new ItemStack[]{
                SFrameStacks.PYROTIC_ALLOY.item(), SFrameStacks.BOOSTED_TELLURIUM.item(), SFrameStacks.PYROTIC_ALLOY.item(),
                XMaterial.SOUL_SOIL.parseItem(), XMaterial.SOUL_SAND.parseItem(), XMaterial.SOUL_SOIL.parseItem(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setEnergyPerTick(128).register(plugin);

        new Putrifier(SFrameStacks.ADV_PUTRIFIER, new ItemStack[]{
                SFrameStacks.RUBEDO.item(), SFrameStacks.BOOSTED_TELLURIUM.item(), SFrameStacks.RUBEDO.item(),
                getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(), SFrameStacks.PUTRIFIER.item(), getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProduction(5).setProcessingSpeed(5).setEnergyPerTick(256).register(plugin);

        new Putrifier(SFrameStacks.PRIME_PUTRIFIER, new ItemStack[]{
                SFrameStacks.BOOSTED_TELLURIUM.item(), SFrameStacks.OROKIN_CELL.item(), SFrameStacks.BOOSTED_TELLURIUM.item(),
                getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(), SFrameStacks.ADV_PUTRIFIER.item(), getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_PUTRIFIER).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_PUTRIFIER).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_PUTRIFIER).item()
        }).setProduction(15).setProcessingSpeed(30).setEnergyPerTick(512).register(plugin);

        new Recycler(Groups.MACHINES, SFrameStacks.RECYCLER, Foundry.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), XMaterial.PISTON.parseItem(), SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), SlimefunItems.ELECTRIC_FURNACE_3.item(), SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                SlimefunItems.HEATING_COIL.item(), SlimefunItems.CARBONADO_EDGED_CAPACITOR.item(), SlimefunItems.HEATING_COIL.item()
        }).setEnergyPerTick(512).register(plugin);

        new SulfateProducer(SFrameStacks.SULFATE_PRODUCER, new ItemStack[]{
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), SlimefunItems.BOOSTED_URANIUM.item(), SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), XMaterial.PISTON.parseItem(), SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.CARBONADO_EDGED_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setEnergyPerTick(128).register(plugin);

        new SulfateProducer(SFrameStacks.ADV_SULFATE_PRODUCER, new ItemStack[]{
                SFrameStacks.THAUMIC_DISTILLATE.item(), SFrameStacks.THAUMIC_DISTILLATE.item(), SFrameStacks.THAUMIC_DISTILLATE.item(),
                SFrameStacks.RUBEDO.item(), SFrameStacks.SULFATE_PRODUCER.item(), SFrameStacks.RUBEDO.item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProduction(2).setProcessingSpeed(5).setEnergyPerTick(256).register(plugin);

        new SulfateProducer(SFrameStacks.PRIME_SULFATE_PRODUCER, new ItemStack[]{
                getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(), SFrameStacks.OROKIN_CELL.item(), getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(),
                getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(), SFrameStacks.ADV_SULFATE_PRODUCER.item(), getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_SULFATE_PRODUCER).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_SULFATE_PRODUCER).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_SULFATE_PRODUCER).item()
        }).setProduction(8).setProcessingSpeed(30).setEnergyPerTick(512).register(plugin);

        new TelluriumFragmentsSynthesizer(SFrameStacks.TELLURIUM_FRAGMENTS_SYNTHESIZER, new ItemStack[]{
                SFrameStacks.HESPAZYM_ALLOY.item(), SlimefunItems.ENERGIZED_CAPACITOR.item(), SFrameStacks.HESPAZYM_ALLOY.item(),
                SFrameStacks.DILUTED_THERMIA.item(), XMaterial.NETHER_WART_BLOCK.parseItem(), SFrameStacks.DILUTED_THERMIA.item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setEnergyPerTick(512).register(plugin);

        new TerracottaGenerator(SFrameStacks.TERRACOTTA_GENERATOR, new ItemStack[]{
                SFrameStacks.ADRAMAL_ALLOY.item(), SlimefunItems.RAINBOW_GLAZED_TERRACOTTA.item(), SFrameStacks.ADRAMAL_ALLOY.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), XMaterial.TERRACOTTA.parseItem(), SlimefunItems.ELECTRIC_MOTOR.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.MEDIUM_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setEnergyPerTick(128).register(plugin);

        new TerracottaGenerator(SFrameStacks.ADV_TERRACOTTA_GEN, new ItemStack[]{
                SlimefunItems.REINFORCED_PLATE.item(), SlimefunItems.REINFORCED_PLATE.item(), SlimefunItems.REINFORCED_PLATE.item(),
                getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(), SFrameStacks.TERRACOTTA_GENERATOR.item(), getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProcessingSpeed(5).setEnergyPerTick(256).register(plugin);

        new TerracottaGenerator(SFrameStacks.PRIME_TERRACOTTA_GEN, new ItemStack[]{
                SFrameStacks.RUBEDO.item(), SFrameStacks.OROKIN_CELL.item(), SFrameStacks.RUBEDO.item(),
                getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(), SFrameStacks.ADV_TERRACOTTA_GEN.item(), getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_TERRACOTTA_GEN).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_TERRACOTTA_GEN).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_TERRACOTTA_GEN).item()
        }).setProcessingSpeed(30).setEnergyPerTick(512).register(plugin);

        new ThermiaExtractor(Groups.MACHINES, SFrameStacks.THERMIA_EXTRACTOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.REINFORCED_ALLOY_INGOT.item(), SlimefunItems.FLUID_PUMP.item(), SlimefunItems.REINFORCED_ALLOY_INGOT.item(),
                SFrameStacks.CRYOTIC.item(), XMaterial.LODESTONE.parseItem(), SFrameStacks.CRYOTIC.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SFrameStacks.CONTROL_MODULE.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setEnergyPerTick(512).register(plugin);

        new TreePeeler(Groups.MACHINES, SFrameStacks.TREE_PEELER, Foundry.RECIPE_TYPE, new ItemStack[]{
                null, SlimefunItems.HEATING_COIL.item(), null,
                SlimefunItems.CARBONADO.item(), XMaterial.STONECUTTER.parseItem(), SlimefunItems.CARBONADO.item(),
                SlimefunItems.PROGRAMMABLE_ANDROID_WOODCUTTER.item(), SlimefunItems.CARBONADO_EDGED_CAPACITOR.item(), SlimefunItems.PROGRAMMABLE_ANDROID_WOODCUTTER.item()
        }).setEnergyPerTick(128).register(plugin);

        new TreePeeler(Groups.MACHINES, SFrameStacks.ADV_TREE_PEELER, Foundry.RECIPE_TYPE, new ItemStack[]{
                null, SlimefunItems.HEATING_COIL.item(), null,
                SFrameStacks.PYROTIC_ALLOY.item(), SFrameStacks.TREE_PEELER.item(), SFrameStacks.PYROTIC_ALLOY.item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProcessingSpeed(5).setEnergyPerTick(256).register(plugin);

        new TreePeeler(Groups.MACHINES, SFrameStacks.PRIME_TREE_PEELER, Foundry.RECIPE_TYPE, new ItemStack[]{
                null, SFrameStacks.OROKIN_CELL.item(), null,
                getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(), SFrameStacks.ADV_TREE_PEELER.item(), getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_TREE_PEELER).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_TREE_PEELER).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_TREE_PEELER).item()
        }).setProcessingSpeed(30).setEnergyPerTick(550).register(plugin);

        new WoolGenerator(Groups.MACHINES, SFrameStacks.WOOL_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.FERSTEEL_ALLOY.item(), SlimefunItems.RAINBOW_WOOL.item(), SFrameStacks.FERSTEEL_ALLOY.item(),
                SFrameStacks.DEVOLVED_NAMALON.item(), XMaterial.WHITE_WOOL.parseItem(), SFrameStacks.DEVOLVED_NAMALON.item(),
                SlimefunItems.ELECTRIC_MOTOR.item(), SlimefunItems.BIG_CAPACITOR.item(), SlimefunItems.ELECTRIC_MOTOR.item()
        }).setEnergyPerTick(128).register(plugin);

        new WoolGenerator(Groups.MACHINES, SFrameStacks.ADV_WOOL_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.VENERDO_ALLOY.item(), SFrameStacks.ARGON_CRYSTAL.item(), SFrameStacks.VENERDO_ALLOY.item(),
                SFrameStacks.VENERDO_ALLOY.item(), SFrameStacks.WOOL_GENERATOR.item(), SFrameStacks.VENERDO_ALLOY.item(),
                SFrameStacks.CUBIC_DIODES.item(), SFrameStacks.CONTROL_MODULE.item(), SFrameStacks.CUBIC_DIODES.item()
        }).setProcessingSpeed(5).setEnergyPerTick(256).register(plugin);

        new WoolGenerator(Groups.MACHINES, SFrameStacks.PRIME_WOOL_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                null, SFrameStacks.OROKIN_CELL.item(), null,
                getAlloyPlate(SFrameStacks.THAUMIC_DISTILLATE).item(), SFrameStacks.ADV_WOOL_GENERATOR.item(), getAlloyPlate(SFrameStacks.THAUMIC_DISTILLATE).item(),
                PrimeComponents.createControlUnit(SFrameStacks.PRIME_WOOL_GENERATOR).item(), PrimeComponents.createCoreModule(SFrameStacks.PRIME_WOOL_GENERATOR).item(), PrimeComponents.createPowerCell(SFrameStacks.PRIME_WOOL_GENERATOR).item()
        }).setProcessingSpeed(30).setEnergyPerTick(512).register(plugin);

    }

    private void registerGenerators() {
        new CumulativeGenerator(SFrameStacks.GRAVITECH_ENERCELL, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.AUROXIUM_ALLOY.item(), SlimefunItems.SOLAR_GENERATOR_4.item(), SFrameStacks.AUROXIUM_ALLOY.item(),
                SFrameStacks.THAUMIC_DISTILLATE.item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), SFrameStacks.THAUMIC_DISTILLATE.item(),
                SFrameStacks.AUROXIUM_ALLOY.item(), SlimefunItems.SOLAR_GENERATOR_4.item(), SFrameStacks.AUROXIUM_ALLOY.item()
        }).setEnergyGenerated(2000).register(plugin);

        new CumulativeGenerator(SFrameStacks.ARCANE_FLUX_DYNAMO, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(), SFrameStacks.GRAVITECH_ENERCELL.item(), getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(),
                getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(),
                getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(), SFrameStacks.GRAVITECH_ENERCELL.item(), getAlloyPlate(SFrameStacks.PYROTIC_ALLOY).item(),
        }).setBonusEnergy(1500).setEnergyGenerated(2000).register(plugin);

        new CumulativeGenerator(SFrameStacks.SPECTRA_REACTOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(), SFrameStacks.ARCANE_FLUX_DYNAMO.item(), getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(),
                getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(),
                getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item(), SFrameStacks.ARCANE_FLUX_DYNAMO.item(), getAlloyPlate(SFrameStacks.ADRAMAL_ALLOY).item()
        }).setBonusEnergy(3000).setEnergyGenerated(4000).register(plugin);

        new CumulativeGenerator(SFrameStacks.PRISMA_POWER_CORE, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(), SFrameStacks.SPECTRA_REACTOR.item(), getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(),
                getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(),
                getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item(), SFrameStacks.SPECTRA_REACTOR.item(), getAlloyPlate(SFrameStacks.TRAVOCYTE_ALLOY).item()
        }).setBonusEnergy(6000).setEnergyGenerated(8000).register(plugin);

        new CumulativeGenerator(SFrameStacks.VOIDLIGHT_FUSION_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(), SFrameStacks.PRISMA_POWER_CORE.item(), getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(),
                getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(),
                getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item(), SFrameStacks.PRISMA_POWER_CORE.item(), getAlloyPlate(SFrameStacks.FERSTEEL_ALLOY).item()
        }).setBonusEnergy(12000).setEnergyGenerated(16000).register(plugin);

        new CumulativeGenerator(SFrameStacks.AXIOM_ENERGENESIS_ENGINE, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(), SFrameStacks.VOIDLIGHT_FUSION_GENERATOR.item(), getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(),
                getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(),
                getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item(), SFrameStacks.VOIDLIGHT_FUSION_GENERATOR.item(), getAlloyPlate(SFrameStacks.VENERDO_ALLOY).item()
        }).setBonusEnergy(24000).setEnergyGenerated(32000).register(plugin);

        new CumulativeGenerator(SFrameStacks.CHRONOS_INFINITY_DYNAMO, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(), SFrameStacks.AXIOM_ENERGENESIS_ENGINE.item(), getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(),
                getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(),
                getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item(), SFrameStacks.AXIOM_ENERGENESIS_ENGINE.item(), getAlloyPlate(SFrameStacks.DEVOLVED_NAMALON).item()
        }).setBonusEnergy(48000).setEnergyGenerated(64000).register(plugin);

        new CumulativeGenerator(SFrameStacks.PRIMORDIAL_ETERNACORE_REACTOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(), SFrameStacks.CHRONOS_INFINITY_DYNAMO.item(), getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(),
                getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(),
                getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item(), SFrameStacks.CHRONOS_INFINITY_DYNAMO.item(), getAlloyPlate(SFrameStacks.AUROXIUM_ALLOY).item()
        }).setBonusEnergy(96000).setEnergyGenerated(128000).register(plugin);

        new CumulativeGenerator(SFrameStacks.VOIDFORGE_CELESTIUM_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(), SFrameStacks.PRIMORDIAL_ETERNACORE_REACTOR.item(), getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(),
                getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(), SFrameStacks.PRISMATIC_ENERGIZED_CORE.item(), getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(),
                getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item(), SFrameStacks.PRIMORDIAL_ETERNACORE_REACTOR.item(), getAlloyPlate(SFrameStacks.HESPAZYM_ALLOY).item()
        }).setBonusEnergy(192000).setEnergyGenerated(256000).register(plugin);

        new CumulativeGenerator(SFrameStacks.ASTRAL_PRIME_GENERATOR, Foundry.RECIPE_TYPE, new ItemStack[]{
                SFrameStacks.CONDENSED_PLATE.item(), SFrameStacks.VOIDFORGE_CELESTIUM_GENERATOR.item(), SFrameStacks.CONDENSED_PLATE.item(),
                PrimeComponents.createControlUnit(SFrameStacks.ASTRAL_PRIME_GENERATOR).item(), PrimeComponents.createCoreModule(SFrameStacks.ASTRAL_PRIME_GENERATOR).item(), PrimeComponents.createPowerCell(SFrameStacks.ASTRAL_PRIME_GENERATOR).item(),
                SFrameStacks.CONDENSED_PLATE.item(), SFrameStacks.VOIDFORGE_CELESTIUM_GENERATOR.item(), SFrameStacks.CONDENSED_PLATE.item()
        }).setBonusEnergy(1_000_000).setEnergyGenerated(1_000_000).register(plugin);
    }

    private void registerGear() {
        new CryogenicArmorPiece(SFrameStacks.CRYO_HELMET, new ItemStack[]{
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(),
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.SCUBA_HELMET.item(), SlimefunItems.REINFORCED_CLOTH.item(),
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item()
        }, new PotionEffect[]{
                new PotionEffect(PotionEffectType.WATER_BREATHING, 300, 1)}
        ).register(plugin);

        new CryogenicArmorPiece(SFrameStacks.CRYO_CHESTPLATE, new ItemStack[]{
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(),
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.HAZMAT_CHESTPLATE.item(), SlimefunItems.REINFORCED_CLOTH.item(),
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item()
        }, new PotionEffect[]{
                new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 300, 1)}
        ).register(plugin);

        new CryogenicArmorPiece(SFrameStacks.CRYO_LEGGINGS, new ItemStack[]{
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(),
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.HAZMAT_LEGGINGS.item(), SlimefunItems.REINFORCED_CLOTH.item(),
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item()
        }).register(plugin);

        new CryogenicArmorPiece(SFrameStacks.CRYO_BOOTS, new ItemStack[]{
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(),
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.HAZMAT_BOOTS.item(), SlimefunItems.REINFORCED_CLOTH.item(),
                SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item(), SlimefunItems.REINFORCED_CLOTH.item()
        }).register(plugin);
    }

    private void registerUtilsAndTools() {

        new NosamPick(SFrameStacks.NOSAM_PICK, Foundry.RECIPE_TYPE, new ItemStack[]{
                SlimefunItems.POWER_CRYSTAL.item(), SlimefunItems.LAVA_CRYSTAL.item(), SlimefunItems.POWER_CRYSTAL.item(),
                null, SlimefunItems.REINFORCED_ALLOY_INGOT.item(), null,
                null, SlimefunItems.REINFORCED_ALLOY_INGOT.item(), null
        }, 30, 15, 5).register(plugin);

        new NosamPick(SFrameStacks.FOCUSED_NOSAM_PICK, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                SFrameStacks.DILUTED_THERMIA.item(), SFrameStacks.NEURODES.item(), SFrameStacks.DILUTED_THERMIA.item(),
                SlimefunItems.EARTH_RUNE.item(), SFrameStacks.NOSAM_PICK.item(), SlimefunItems.EARTH_RUNE.item(),
                SFrameStacks.DILUTED_THERMIA.item(), SFrameStacks.NEURODES.item(), SFrameStacks.DILUTED_THERMIA.item()
        }, 40, 20, 10).register(plugin);

        new NosamPick(SFrameStacks.PRIME_NOSAM_PICK, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                SFrameStacks.NEURAL_SENSORS.item(), SFrameStacks.OROKIN_CELL.item(), SFrameStacks.NEURAL_SENSORS.item(),
                UtilsComponents.createVoidShard(SFrameStacks.PRIME_NOSAM_PICK).item(), SFrameStacks.FOCUSED_NOSAM_PICK.item(), UtilsComponents.createTemporal(SFrameStacks.PRIME_NOSAM_PICK).item(),
                SFrameStacks.NEURAL_SENSORS.item(), UtilsComponents.createNeuralNexus(SFrameStacks.PRIME_NOSAM_PICK).item(), SFrameStacks.NEURAL_SENSORS.item()
        }, 60, 35, 25).register(plugin);

        new MerchantSoulContract(Groups.UTILS_AND_TOOLS, SFrameStacks.MERCHANT_SOUL_CONTRACT, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                SFrameStacks.CRYOTIC.item(), SFrameStacks.NEURAL_SENSORS.item(), SFrameStacks.CRYOTIC.item(),
                SlimefunItems.SOULBOUND_RUNE.item(), SlimefunItems.VILLAGER_RUNE.item(), SlimefunItems.SOULBOUND_RUNE.item(),
                SFrameStacks.CRYOTIC.item(), SFrameStacks.NEURAL_SENSORS.item(), SFrameStacks.CRYOTIC.item()
        }).register(plugin);

        new OrokinWand(Groups.UTILS_AND_TOOLS, SFrameStacks.OROKIN_WAND, RecipeType.MAGIC_WORKBENCH, new ItemStack[]{
                null, null, SFrameStacks.OROKIN_CELL.item(),
                null, XMaterial.STICK.parseItem(), null,
                XMaterial.STICK.parseItem(), null, null
        }).setMaxUseCount(64).register(plugin);

        new OrokinWand(Groups.UTILS_AND_TOOLS, SFrameStacks.PRIME_OROKIN_WAND, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                SlimefunItems.BLANK_RUNE.item(), SFrameStacks.OROKIN_CELL.item(), SlimefunItems.BLANK_RUNE.item(),
                UtilsComponents.createVoidShard(SFrameStacks.PRIME_OROKIN_WAND).item(), SFrameStacks.OROKIN_WAND.item(), UtilsComponents.createTemporal(SFrameStacks.PRIME_OROKIN_WAND).item(),
                SlimefunItems.BLANK_RUNE.item(), UtilsComponents.createNeuralNexus(SFrameStacks.PRIME_OROKIN_WAND).item(), SlimefunItems.BLANK_RUNE.item()
        }).setMaxUseCount(1024).register(plugin);

        new InputConfigurator(Groups.UTILS_AND_TOOLS, SFrameStacks.INPUT_CONFIGURATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, null, SlimefunItems.CARGO_MANAGER.item(),
                null, XMaterial.BLAZE_ROD.parseItem(), null,
                SFrameStacks.OROKIN_WAND.item(), null, null
        }).register(plugin);

        new SelectorConfigurator(Groups.UTILS_AND_TOOLS, SFrameStacks.SELECTOR_CONFIGURATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, null, SFrameStacks.TELLURIUM.item(),
                null, XMaterial.BLAZE_ROD.parseItem(), null,
                SFrameStacks.INPUT_CONFIGURATOR.item(), null, null
        }).register(plugin);

        new ItemProjector(SFrameStacks.ITEM_PROJECTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, SFrameStacks.NEURAL_SENSORS.item(), null,
                SlimefunItems.POWER_CRYSTAL.item(), new SlimefunItemStack(SFrameStacks.SALVAGE, 8).item(), SlimefunItems.POWER_CRYSTAL.item(),
                SlimefunItems.HOLOGRAM_PROJECTOR.item(), SlimefunItems.HOLOGRAM_PROJECTOR.item(), SlimefunItems.HOLOGRAM_PROJECTOR.item()
        }).register(plugin);

        new EnergyCentral(Groups.UTILS_AND_TOOLS, SFrameStacks.ENERGY_CENTRAL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.DAMASCUS_STEEL_INGOT.item(), SlimefunItems.ENERGY_REGULATOR.item(), SlimefunItems.DAMASCUS_STEEL_INGOT.item(),
                SlimefunItems.DAMASCUS_STEEL_INGOT.item(), XMaterial.BEACON.parseItem(), SlimefunItems.DAMASCUS_STEEL_INGOT.item(),
                SlimefunItems.DAMASCUS_STEEL_INGOT.item(), SFrameStacks.CONTROL_MODULE.item(), SlimefunItems.DAMASCUS_STEEL_INGOT.item()
        }).register(plugin);

    }

    private void registerPrimeComponents() {
        new PrimeComponents(SFrameStacks.PRIME_TREE_PEELER);
        new PrimeComponents(SFrameStacks.PRIME_FLOWER_GENERATOR);
        new PrimeComponents(SFrameStacks.PRIME_WOOL_GENERATOR);
        new PrimeComponents(SFrameStacks.PRIME_ARTIFICIAL_MANGROVE);
        new PrimeComponents(SFrameStacks.PRIME_BASALT_GEN);
        new PrimeComponents(SFrameStacks.PRIME_CRYOTIC_EXTRACTOR);
        new PrimeComponents(SFrameStacks.PRIME_CONCRETE_GENERATOR);
        new PrimeComponents(SFrameStacks.ASTRAL_PRIME_GENERATOR);
        new PrimeComponents(SFrameStacks.PRIME_SULFATE_PRODUCER);
        new PrimeComponents(SFrameStacks.PRIME_DUST_GENERATOR);
        new PrimeComponents(SFrameStacks.PRIME_CHUNK_EATER);
        new PrimeComponents(SFrameStacks.PRIME_GLASS_GENERATOR);
        new PrimeComponents(SFrameStacks.PRIME_PUTRIFIER);
        new PrimeComponents(SFrameStacks.PRIME_TERRACOTTA_GEN);
        PrimeComponents.registerAll(plugin);

        new UtilsComponents(SFrameStacks.PRIME_NOSAM_PICK);
        new UtilsComponents(SFrameStacks.PRIME_OROKIN_WAND);
        UtilsComponents.registerAll(plugin);
    }

    private void registerFreezingItems() {
        for (SlimefunItem item : Slimefun.getRegistry().getAllSlimefunItems()) {
            if (!(item instanceof FreezingItem)) continue;
            SlimeFrame.getFreezingItems().add(item);
        }
    }

    private void registerAlloy(@Nonnull SlimefunItemStack itemStack, ItemStack[] recipe) {
        AlloyIngot alloyIngot = new AlloyIngot(Groups.RESOURCES, itemStack, recipe);
        alloyIngot.register(plugin);
        ALLOYS.add(alloyIngot);
    }

    private void registerAlloyPlate(@Nonnull SlimefunItem item) {
        ItemStack itemStack = item.getItem();

        this.registerAlloyPlate(itemStack, item.getId(), new ItemStack[]{
                itemStack, itemStack, itemStack,
                itemStack, SFrameStacks.ARGON_CRYSTAL.item(), itemStack,
                itemStack, itemStack, itemStack
        });
    }

    private void registerAlloyPlate(@Nonnull ItemStack itemStack, String itemId, ItemStack[] recipe) {
        var meta = itemStack.getItemMeta();
        if (meta == null) {
            plugin.getLogger().severe("Error registering alloy plate for '" + itemId + "': Meta is null");
            return;
        }

        SlimefunItemStack alloyPlate = SFrameTheme.sfStackFromTheme(
                itemId + "_PLATE",
                Material.PAPER,
                SFrameStacks.RESOURCES_THEME,
                meta.getDisplayName() + " Plate",
                "A plate made of " + meta.getDisplayName()
        );

        SlimefunItem slimefunItem = new SlimefunItem(Groups.RESOURCES, alloyPlate, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        ALLOY_PLATES_MAP.put(itemStack, alloyPlate);
        slimefunItem.register(plugin);
    }

    private SlimefunItemStack getAlloyPlate(SlimefunItemStack itemStack) {
        return ALLOY_PLATES_MAP.get(itemStack.item());
    }

}
