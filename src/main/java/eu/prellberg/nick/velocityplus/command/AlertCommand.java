package eu.prellberg.nick.velocityplus.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import eu.prellberg.nick.velocityplus.VelocityPlus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.slf4j.Logger;

import eu.prellberg.nick.velocityplus.utils.FormatParser;

public record AlertCommand(ProxyServer proxyServer, VelocityPlus velocityPlus, Logger logger) implements SimpleCommand {

    public AlertCommand(ProxyServer proxyServer, VelocityPlus velocityPlus, Logger logger) {
        this.proxyServer = proxyServer;
        this.velocityPlus = velocityPlus;
        this.logger = logger;
        CommandManager manager = proxyServer.getCommandManager();
        manager.register(manager.metaBuilder("alert").build(), this);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!source.hasPermission("vplus.alert")) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
            return;
        }

        if (invocation.arguments().length < 1) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("alert.command.missingMessage"))));
            return;
        }

        String text = String.join(" ", invocation.arguments());
        text = new FormatParser().parse(text);
        for (Player player : this.proxyServer.getAllPlayers()) {
            player.sendMessage(Component.join(JoinConfiguration.separator(Component.text(" ")), Component.text(velocityPlus.getConfig().getString("alert.command.prefix")), MiniMessage.miniMessage().deserialize(text)));
        }
    }
}
