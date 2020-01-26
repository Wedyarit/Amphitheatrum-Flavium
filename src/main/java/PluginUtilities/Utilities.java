package PluginUtilities;

import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utilities {

    public static int getRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static DyeColor getRandomColor() {
        ArrayList<DyeColor> colors = new ArrayList<DyeColor>();

        for (DyeColor color : DyeColor.values())
            colors.add(color);

        int randomColorIndex = Utilities.getRandom(0, colors.size() - 1);

        return colors.get(randomColorIndex);
    }
}
