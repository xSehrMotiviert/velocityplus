package eu.prellberg.nick.velocityplus;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import eu.prellberg.nick.velocityplus.command.*;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Plugin(id = "velocityplus", name = "VelocityPlus", version = "1.1.1", authors = {"Nick Prellberg"}, url = "https://github.com/xsehrmotiviert/velocityplus")
public class VelocityPlus {

    private final ProxyServer server;
    private final Logger logger;
    private final String configPath;

    @Inject
    public VelocityPlus(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        this.configPath = "./plugins/VelocityPlus/config.toml";


        logger.info("[V+] Ready for duty!");
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {

        final var config = Path.of("plugins/VelocityPlus/config.toml");
        if (!Files.exists(config)) {
            try (final var configTemplate = getClass().getResourceAsStream("/config.toml")) {
                Files.createDirectories(config.getParent());
                assert configTemplate != null;
                Files.write(config, configTemplate.readAllBytes(), StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                logger.error("New IO Exception: ", e);
            }

        }

        logger.info("""


                 /$$    /$$          /$$                     /$$   /$$                       \s
                | $$   | $$         | $$                    |__/  | $$                  /$$  \s
                | $$   | $$ /$$$$$$ | $$  /$$$$$$   /$$$$$$$ /$$ /$$$$$$   /$$   /$$   | $$  \s
                |  $$ / $$//$$__  $$| $$ /$$__  $$ /$$_____/| $$|_  $$_/  | $$  | $$ /$$$$$$$$
                 \\  $$ $$/| $$$$$$$$| $$| $$  \\ $$| $$      | $$  | $$    | $$  | $$|__  $$__/
                  \\  $$$/ | $$_____/| $$| $$  | $$| $$      | $$  | $$ /$$| $$  | $$   | $$  \s
                   \\  $/  |  $$$$$$$| $$|  $$$$$$/|  $$$$$$$| $$  |  $$$$/|  $$$$$$$   |__/  \s
                    \\_/    \\_______/|__/ \\______/  \\_______/|__/   \\___/   \\____  $$         \s
                                                                           /$$  | $$         \s
                                                                          |  $$$$$$/         \s
                                                                           \\______/          \s
                Version: 1.1.1""");

        new SendCommand(server, this, logger);
        new KickallCommand(server, this, logger);
        new FindCommand(server, this, logger);
        new IPCommand(server, this, logger);
        new AlertCommand(server, this, logger);
        new GotoCommand(server, this,logger);

    }

    public Toml getConfig() {
        return new Toml().read(new File(this.configPath));
    }

}
