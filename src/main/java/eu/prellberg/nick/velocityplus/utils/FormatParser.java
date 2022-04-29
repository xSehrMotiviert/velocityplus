package eu.prellberg.nick.velocityplus.utils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class FormatParser {

    HashMap<String, String> legacyCodes = new HashMap<>() {{
        put("0", "<black>");
        put("1", "<dark_blue>");
        put("2", "<dark_green>");
        put("3", "<dark_aqua>");
        put("4", "<dark_red>");
        put("5", "<dark_purple>");
        put("6", "<gold>");
        put("7", "<gray>");
        put("8", "<dark_gray>");
        put("9", "<blue>");
        put("a", "<green>");
        put("b", "<aqua>");
        put("c", "<red>");
        put("d", "<light_purple>");
        put("e", "<yellow>");
        put("f", "<white>");
        put("k", "<obf>");
        put("l", "<b>");
        put("m", "<st>");
        put("n", "<u>");
        put("o", "<i>");
        put("r", "<reset>");
    }};

    public String parse(String string) {
        AtomicReference<String> newString = new AtomicReference<>(string);

        legacyCodes.forEach((legacyCode, miniMessage) -> {
            newString.set(string.replaceAll("&" + legacyCode, miniMessage));
            newString.set(string.replaceAll("§" + legacyCode, miniMessage));
        });

        return newString.get();
    }
}