package eu.prellberg.nick.velocityplus.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class formatParser {

    public String parse(String string) {
        Pattern patternAmpersand = Pattern.compile("&[0-9a-fk-or]", Pattern.CASE_INSENSITIVE);
        Pattern patternParagraph = Pattern.compile("§[0-9a-fk-or]", Pattern.CASE_INSENSITIVE);

        Matcher matcherAmpersand = patternAmpersand.matcher(string);
        Matcher matcherParagraph = patternParagraph.matcher(string);

        boolean matchAFound = matcherAmpersand.find();
        boolean matchPFound = matcherParagraph.find();

        System.out.println("MATCHING");

        if (matchAFound) {
            System.out.println("AMPERSAND");
            string = string.replaceAll("&0", "<#000000>");
            string = string.replaceAll("&1", "<#0000aa>");
            string = string.replaceAll("&2", "<#00aa00>");
            string = string.replaceAll("&3", "<#00aaaa>");
            string = string.replaceAll("&4", "<#aa0000>");
            string = string.replaceAll("&5", "<#aa00aa>");
            string = string.replaceAll("&6", "<#ffaa00>");
            string = string.replaceAll("&7", "<#aaaaaa>");
            string = string.replaceAll("&8", "<#555555>");
            string = string.replaceAll("&9", "<#5555ff>");
            string = string.replaceAll("&a", "<#55ff55>");
            string = string.replaceAll("&b", "<#55ffff>");
            string = string.replaceAll("&c", "<#ff5555>");
            string = string.replaceAll("&d", "<#ff55ff>");
            string = string.replaceAll("&e", "<#ffff55>");
            string = string.replaceAll("&f", "<#ffffff>");
            string = string.replaceAll("&k", "<obf>");
            string = string.replaceAll("&l", "<b>");
            string = string.replaceAll("&m", "<st>");
            string = string.replaceAll("&n", "<u>");
            string = string.replaceAll("&o", "<i>");
            string = string.replaceAll("&r", "<reset>");
        }

        if (matchPFound) {
            System.out.println("PARAGRAPH");
            string = string.replaceAll("§0", "<#000000>");
            string = string.replaceAll("§1", "<#0000aa>");
            string = string.replaceAll("§2", "<#00aa00>");
            string = string.replaceAll("§3", "<#00aaaa>");
            string = string.replaceAll("§4", "<#aa0000>");
            string = string.replaceAll("§5", "<#aa00aa>");
            string = string.replaceAll("§6", "<#ffaa00>");
            string = string.replaceAll("§7", "<#aaaaaa>");
            string = string.replaceAll("§8", "<#555555>");
            string = string.replaceAll("§9", "<#5555ff>");
            string = string.replaceAll("§a", "<#55ff55>");
            string = string.replaceAll("§b", "<#55ffff>");
            string = string.replaceAll("§c", "<#ff5555>");
            string = string.replaceAll("§d", "<#ff55ff>");
            string = string.replaceAll("§e", "<#ffff55>");
            string = string.replaceAll("§f", "<#ffffff>");
            string = string.replaceAll("§k", "<obf>");
            string = string.replaceAll("§l", "<b>");
            string = string.replaceAll("§m", "<st>");
            string = string.replaceAll("§n", "<u>");
            string = string.replaceAll("§o", "<i>");
            string = string.replaceAll("§r", "<reset>");
        }
        return string;
    }
}
