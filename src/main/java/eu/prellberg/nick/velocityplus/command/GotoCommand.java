package eu.prellberg.nick.velocityplus.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import eu.prellberg.nick.velocityplus.VelocityPlus;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public record GotoCommand(ProxyServer proxyServer, VelocityPlus velocityPlus, Logger logger) implements SimpleCommand {

    public GotoCommand(ProxyServer proxyServer, VelocityPlus velocityPlus, Logger logger) {
        this.proxyServer = proxyServer;
        this.velocityPlus = velocityPlus;
        this.logger = logger;
        CommandManager manager = proxyServer.getCommandManager();
        manager.register(manager.metaBuilder("goto").build(), this);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!source.hasPermission("vplus.goto")) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
            return;
        }

        if (invocation.arguments().length < 1) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("goto.command.missingUser"))));
            return;
        }

        Optional<Player> playerFound = proxyServer.getPlayer(invocation.arguments()[0]);
        if (playerFound.isEmpty()) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("goto.command.userOffline"))));
            return;
        }
        Optional<ServerConnection> server = playerFound.get().getCurrentServer();

        Player sourcePlayer = (Player) source;

        if (Objects.equals(server.get().getServerInfo().getName(), sourcePlayer.getCurrentServer().get().getServerInfo().getName())) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("goto.command.sameServer"))));
            return;
        }

        sourcePlayer.createConnectionRequest(server.get().getServer()).fireAndForget();
        source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("goto.command.result").replaceAll("<<user>>", playerFound.get().getUsername()).replaceAll("<<server>>", server.get().getServerInfo().getName()))));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!source.hasPermission("vplus.goto")) {
            return Collections.emptyList();
        }
        if (invocation.arguments().length > 0) {
            return proxyServer.getAllPlayers()
                    .stream()
                    .map(Player::getUsername)
                    .filter(s -> s.startsWith(String.join("", invocation.arguments())))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
