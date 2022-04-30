package eu.prellberg.nick.velocityplus.utils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class FormatParser {

    HashMap<String, String> legacyCodes = new HashMap<>() {{
        put("0", "<#000000>");
        put("1", "<#0000aa>");
        put("2", "<#00aa00>");
        put("3", "<#00aaaa>");
        put("4", "<#0000aa>");
        put("5", "<#aa00aa>");
        put("6", "<#ffaa00>");
        put("7", "<#aaaaaa>");
        put("8", "<#555555>");
        put("9", "<#5555ff>");
        put("a", "<#55ff55>");
        put("b", "<#55ffff>");
        put("c", "<#ff5555>");
        put("d", "<#ff55ff>");
        put("e", "<#ffff55>");
        put("f", "<#ffffff>");
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
            newString.set(newString.get().replaceAll("&" + legacyCode, miniMessage));
            newString.set(newString.get().replaceAll("§" + legacyCode, miniMessage));
        });
        
        return newString.get();
    }
}
