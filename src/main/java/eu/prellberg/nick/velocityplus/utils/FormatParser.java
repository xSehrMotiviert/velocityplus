package eu.prellberg.nick.velocityplus.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatParser {

    Pattern patternAmpersand = Pattern.compile("&[0-9a-fk-or]", Pattern.CASE_INSENSITIVE);
    Pattern patternParagraph = Pattern.compile("§[0-9a-fk-or]", Pattern.CASE_INSENSITIVE);

    public String parse(String string) {
        Matcher matcherAmpersand = patternAmpersand.matcher(string);
        Matcher matcherParagraph = patternParagraph.matcher(string);

        boolean matchAFound = matcherAmpersand.find();
        boolean matchPFound = matcherParagraph.find();

        if (matchAFound) {
            string = string.replaceAll("&0", "<black>");
            string = string.replaceAll("&1", "<dark_blue>");
            string = string.replaceAll("&2", "<dark_green>");
            string = string.replaceAll("&3", "<dark_aqua>");
            string = string.replaceAll("&4", "<dark_red>");
            string = string.replaceAll("&5", "<dark_purple>");
            string = string.replaceAll("&6", "<gold>");
            string = string.replaceAll("&7", "<gray>");
            string = string.replaceAll("&8", "<dark_gray>");
            string = string.replaceAll("&9", "<blue>");
            string = string.replaceAll("&a", "<green>");
            string = string.replaceAll("&b", "<aqua>");
            string = string.replaceAll("&c", "<red>");
            string = string.replaceAll("&d", "<light_purple>");
            string = string.replaceAll("&e", "<yellow>");
            string = string.replaceAll("&f", "<white>");
            string = string.replaceAll("&k", "<obf>");
            string = string.replaceAll("&l", "<b>");
            string = string.replaceAll("&m", "<st>");
            string = string.replaceAll("&n", "<u>");
            string = string.replaceAll("&o", "<i>");
            string = string.replaceAll("&r", "<reset>");
        }

        if (matchPFound) {
            string = string.replaceAll("§0", "<black>");
            string = string.replaceAll("§1", "<dark_blue>");
            string = string.replaceAll("§2", "<dark_green>");
            string = string.replaceAll("§3", "<dark_aqua>");
            string = string.replaceAll("§4", "<dark_red>");
            string = string.replaceAll("§5", "<dark_purple>");
            string = string.replaceAll("§6", "<gold>");
            string = string.replaceAll("§7", "<gray>");
            string = string.replaceAll("§8", "<dark_gray>");
            string = string.replaceAll("§9", "<blue>");
            string = string.replaceAll("§a", "<green>");
            string = string.replaceAll("§b", "<aqua>");
            string = string.replaceAll("§c", "<red>");
            string = string.replaceAll("§d", "<light_purple>");
            string = string.replaceAll("§e", "<yellow>");
            string = string.replaceAll("§f", "<white>");
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