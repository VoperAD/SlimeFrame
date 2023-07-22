package me.voper.slimeframe.slimefun.groups;

import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.guide.SurvivalSlimefunGuide;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.voper.slimeframe.utils.Colors;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

@ParametersAreNonnullByDefault
public class TutorialsGroup extends FlexItemGroup {

    private static final int GUIDE_BACK = 1;

    private static final int RELICS_TUTORIAL = 9;
    private static final int GENERATORS_TUTORIAL = 10;
    private static final int SPECIAL_ORES_TUTORIAL = 11;

    private static final int[] FOOTER = new int[]{
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    private boolean visibility = true;

    protected TutorialsGroup(NamespacedKey key, ItemStack item) {
        super(key, item);
        Groups.MAIN_GROUP.addSubGroup(this);
    }

    @Override
    public boolean isVisible(Player player, PlayerProfile playerProfile, SlimefunGuideMode slimefunGuideMode) {
        return visibility;
    }

    @Override
    public void open(Player player, PlayerProfile playerProfile, SlimefunGuideMode slimefunGuideMode) {
        this.openGuide(player, playerProfile, slimefunGuideMode, 1);
    }

    private void openGuide(Player player, PlayerProfile profile, SlimefunGuideMode mode, int page) {
        GuideHistory history = profile.getGuideHistory();
        if (mode == SlimefunGuideMode.SURVIVAL_MODE) {
            history.add(this, page);
        }

        ChestMenu menu = new ChestMenu(Colors.CRAYOLA_BLUE + "SlimeFrame Tutorials");
        SurvivalSlimefunGuide guide = (SurvivalSlimefunGuide) Slimefun.getRegistry().getSlimefunGuide(mode);

        menu.setEmptySlotsClickable(false);
        menu.addMenuOpeningHandler(pl -> pl.playSound(pl.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1));
        guide.createHeader(player, profile, menu);

        for (int slot : FOOTER) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), (player1, i1, itemStack, clickAction) -> false);
        }

        // Back
        menu.replaceExistingItem(GUIDE_BACK, ChestMenuUtils.getBackButton(player, Slimefun.getLocalization().getMessage("guide.back.guide")));
        menu.addMenuClickHandler(GUIDE_BACK, (player1, slot, itemStack, clickAction) -> {
            SlimefunGuide.openItemGroup(profile, Groups.MAIN_GROUP, mode, 1);
            return false;
        });

        // Relics Tutorial
        menu.replaceExistingItem(RELICS_TUTORIAL, new CustomItemStack(Material.ENCHANTED_BOOK, Colors.BRONZE + "Tutorial das Relíquias"));
        menu.addMenuClickHandler(RELICS_TUTORIAL, ((player1, i, itemStack, clickAction) -> {
            player1.getInventory().addItem(getRelicsTutorial());
            return false;
        }));

        // Generators Tutorial
        menu.replaceExistingItem(GENERATORS_TUTORIAL, new CustomItemStack(Material.ENCHANTED_BOOK, Colors.BRONZE + "Tutorial dos Geradores"));
        menu.addMenuClickHandler(GENERATORS_TUTORIAL, ((player1, i, itemStack, clickAction) -> {
            player1.getInventory().addItem(getGeneratorsTutorial());
            return false;
        }));
        menu.open(player);

        // Special Ores Farm
        menu.replaceExistingItem(SPECIAL_ORES_TUTORIAL, new CustomItemStack(Material.ENCHANTED_BOOK, Colors.BRONZE + "Farm dos novos minérios"));
        menu.addMenuClickHandler(SPECIAL_ORES_TUTORIAL, ((player1, i, itemStack, clickAction) -> {
            player1.getInventory().addItem(getSpecialOresTutorial());
            return false;
        }));
        menu.open(player);
    }

    @Nonnull
    private ItemStack getRelicsTutorial() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) book.getItemMeta();
        itemMeta.setAuthor("Voper");
        itemMeta.setTitle(ChatColor.BLUE + "Tutorial das Relíquias");

        ArrayList<BaseComponent[]> components = new ArrayList<>();
        components.add(new ComponentBuilder("Para que servem as relíquias?\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Servem para conseguir componentes Prime para fazer máquinas de tier Prime. Cada relíquia tem 3 recompensas Bronze, 2 recompensas Prata e 1 recompensa Dourada.").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("Como conseguir relíquias?\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Você pode conferir na aba de relíquias, mas basicamente existem quatro tipos de relíquias: Lith, Meso, Neo e Axi. A Lith se consegue pescando, a Meso matando mobs, a Neo quebrando blocos e a Axi colocando blocos.").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("Como abrir relíquias?\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Coloque UMA relíquia na sua mão esquerda e mate endermans até que o número de reactants da sua relíquia seja 10. Após isso, apenas clique com o botão direito com a relíquia na mão e você receberá sua recompensa.").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("Jogadores Bedrock\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Caso você seja um jogador bredrock, a relíquia deve estar no primeiro slot da sua hotbar para conseguir os reactants.").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("Traços do Void\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Os traços do void são usados para refinar relíquias e aumentar suas chances de conseguir itens mais raros. Você ganha traços cada vez que abre uma relíquia. Para ver sua quantidade de traços use /sframe traces.").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("Como refinar relíquias?\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Para refinar uma relíquia use o comando /sframe refine <refinamento>. A chance de conseguir itens raros aumenta dependendo do refinamento.").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("Refinamentos\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("EXCEPTIONAL - requere 25 traços do void\n\nFLAWLESS - requere 50 traços do void\n\nRADIANT - requere 100 traços do void").underlined(false).color(ChatColor.BLACK)
                .create());

        itemMeta.spigot().setPages(components);
        book.setItemMeta(itemMeta);
        return book;
    }

    @Nonnull
    private ItemStack getGeneratorsTutorial() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) book.getItemMeta();
        itemMeta.setAuthor("Voper");
        itemMeta.setTitle(ChatColor.BLUE + "Tutorial dos Geradores");

        ArrayList<BaseComponent[]> components = new ArrayList<>();
        components.add(new ComponentBuilder("Geradores acumulativos\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Alguns dos geradores de energia do SlimeFrame possuem um atributo a mais: ").color(ChatColor.BLACK).color(ChatColor.BLACK).underlined(false)
                .append("Bonus Energy.").bold(true)
                .create());

        components.add(new ComponentBuilder("Bonus Energy\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("O gerador irá checar se os blocos que estão ao seu lado, o de cima e o de baixo são também geradores acumulativos.").color(ChatColor.BLACK).underlined(false)
                .append("Para cada gerador acumulativo encontrado, a energia bônus será somada a energia total gerada.")
                .create());

        components.add(new ComponentBuilder("Exemplo\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Se um gerador tem 1000J de energia bônus e 2000J de energia padrão, se todos os blocos ao seu redor forem também geradores acumulativos, a energia total gerada será de 8000J (6 * 1000J + 2000J)").color(ChatColor.BLACK).underlined(false)
                .create());

        itemMeta.spigot().setPages(components);
        book.setItemMeta(itemMeta);
        return book;
    }

    @Nonnull
    private ItemStack getSpecialOresTutorial() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) book.getItemMeta();
        itemMeta.setAuthor("Voper");
        itemMeta.setTitle(ChatColor.BLUE + "Farm dos novos minérios");

        ArrayList<BaseComponent[]> components = new ArrayList<>();
        components.add(new ComponentBuilder("Novos Minérios\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Na aba de recursos podem ser encontrados novos \"minérios\", os quais são obtidos usando as novas picaretas (Nosam Pickaxe). ").color(ChatColor.BLACK).underlined(false)
                .append("Atualmente existe uma única maneira de criar uma farm desses recursos.")
                .create());

        components.add(new ComponentBuilder("A Auto Trader\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Para fazer uma farm desses novos minérios você precisará construir uma nova máquina: a ").color(ChatColor.BLACK).underlined(false)
                .append("Auto Trader").bold(true)
                .append(". Essa máquina é capaz de trocar automaticamente com villagers.").bold(false)
                .create());

        components.add(new ComponentBuilder("Os Contratos\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Para que a Auto Trader funcione, você precisará colocar um contrato válido dentro dela. ").color(ChatColor.BLACK).underlined(false)
                .append("Esses contratos são conseguidos a partir do ")
                .append("Merchant Soul Contract.").bold(true)
                .create());

        components.add(new ComponentBuilder("Trocando com Villagers\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Quando você conseguir os itens anteriores, terá que ").color(ChatColor.BLACK).underlined(false)
                .append("conseguir um villager que dê um dos recursos novos do addon. ")
                .append("A chance é baixa e a profissão do villager não importa, pode ser qualquer uma.")
                .create());

        components.add(new ComponentBuilder("Último Passo\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("Quando você conseguir um villager que ofereça a troca necessária, ").color(ChatColor.BLACK).underlined(false)
                .append("clique nele com o Merchant Contract na mão, usando o botão direito. ")
                .append("Atenção: ").color(ChatColor.DARK_RED)
                .append("isso irá matá-lo!").color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("Com o contrato válido e a Auto Trader em mãos, basta inseri-lo")
                .append("na máquina e selecionar a troca dentro dela. Após isso é só abastecer a máquina")
                .append("com os recursos necessários!")
                .create());

        itemMeta.spigot().setPages(components);
        book.setItemMeta(itemMeta);
        return book;
    }

}