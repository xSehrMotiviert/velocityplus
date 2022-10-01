package eu.prellberg.nick.velocityplus.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatParser {

    Pattern patternAmpersand = Pattern.compile("&[0-9a-fk-or]", Pattern.CASE_INSENSITIVE);

    public String parse(String strToParse) {

        /// You can safely search for the ampersand character, as it is not a valid character in Minecraft
        if(strToParse.contains("§")) {
            strToParse = strToParse.replaceAll("§", "&");
        }

        Matcher matcherAmpersand = patternAmpersand.matcher(strToParse);
        boolean matchAFound = matcherAmpersand.find();

        if (matchAFound) {
            strToParse = strToParse.replaceAll("&0", "<black>");
            strToParse = strToParse.replaceAll("&1", "<dark_blue>");
            strToParse = strToParse.replaceAll("&2", "<dark_green>");
            strToParse = strToParse.replaceAll("&3", "<dark_aqua>");
            strToParse = strToParse.replaceAll("&4", "<dark_red>");
            strToParse = strToParse.replaceAll("&5", "<dark_purple>");
            strToParse = strToParse.replaceAll("&6", "<gold>");
            strToParse = strToParse.replaceAll("&7", "<gray>");
            strToParse = strToParse.replaceAll("&8", "<dark_gray>");
            strToParse = strToParse.replaceAll("&9", "<blue>");
            strToParse = strToParse.replaceAll("&a", "<green>");
            strToParse = strToParse.replaceAll("&b", "<aqua>");
            strToParse = strToParse.replaceAll("&c", "<red>");
            strToParse = strToParse.replaceAll("&d", "<light_purple>");
            strToParse = strToParse.replaceAll("&e", "<yellow>");
            strToParse = strToParse.replaceAll("&f", "<white>");
            strToParse = strToParse.replaceAll("&k", "<obf>");
            strToParse = strToParse.replaceAll("&l", "<b>");
            strToParse = strToParse.replaceAll("&m", "<st>");
            strToParse = strToParse.replaceAll("&n", "<u>");
            strToParse = strToParse.replaceAll("&o", "<i>");
            strToParse = strToParse.replaceAll("&r", "<reset>");
        }

        return strToParse;
    }
}