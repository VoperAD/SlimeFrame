package me.voper.slimeframe.utils;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Lore {

    private static final DecimalFormat format = new DecimalFormat("###,###,###.##", DecimalFormatSymbols.getInstance(Locale.ROOT));

    @Nonnull
    public static String production(int production) {
        return "&8⇨ &b⚡ &7Production: &b" + production + 'x';
    }

    @Nonnull
    public static String speed(int speed) {
        return "&8⇨ &b⚡ &7Speed: &b" + speed + 'x';
    }

    @Nonnull
    public static String bonusPower(int power) {
        return "&8⇨ &e⚡ &7Bonus energy: " + format.format(Utils.energyPerTickToSeconds(power)) + " J/s";
    }

    @Nonnull
    public static String powerPerSecond(int power) {
        return power(Utils.energyPerTickToSeconds(power), "/s");
    }

    @Nonnull
    public static String powerBuffer(int power) {
        return power(power, " Buffer");
    }

    @Nonnull
    public static String power(int power, @Nonnull String suffix) {
        return "&8⇨ &e⚡ &7" + format.format(power) + " J" + suffix;
    }

    @Nonnull
    public static String usesLeft(int usesLeft) {
        return "&e" + usesLeft + ' ' + (usesLeft > 1 ? "Uses" : "Use") + " &7left";
    }

}