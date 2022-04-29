package eu.prellberg.nick.velocityplus.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import eu.prellberg.nick.velocityplus.VelocityPlus;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public record KickallCommand(ProxyServer proxyServer, VelocityPlus velocityPlus, Logger logger) implements SimpleCommand {

    public KickallCommand(ProxyServer proxyServer, VelocityPlus velocityPlus, Logger logger) {
        this.proxyServer = proxyServer;
        this.velocityPlus = velocityPlus;
        this.logger = logger;
        CommandManager manager = proxyServer.getCommandManager();
        manager.register(manager.metaBuilder("kickall").build(), this);
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

//        if(source instanceof ConsoleCommandSource) {return;}

        if (invocation.arguments().length < 1 || Objects.equals(invocation.arguments()[0], "--force")) {
            if (!source.hasPermission("vplus.kickall.all")) {
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
                return;
            }
            if (invocation.arguments().length == 1 && !source.hasPermission("vplus.kickall.force")) {
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
                return;
            }
            proxyServer.getAllPlayers()
                    .forEach(player -> {
                        if (source == player) return;

                        if (player.hasPermission("vplus.kickall.bypass") && !source.hasPermission("vplus.kickall.force") && invocation.arguments().length == 0)
                            return;

                        if (player.hasPermission("vplus.kickall.bypass") && source.hasPermission("vplus.kickall.force") && invocation.arguments().length == 0)
                            return;

                        if (player.hasPermission("vplus.kickall.bypass") && source.hasPermission("vplus.kickall.force") && !Objects.equals(invocation.arguments()[0], "--force"))
                            return;

                        if (player.hasPermission("vplus.kickall.bypass") && !source.hasPermission("vplus.kickall.force") && Objects.equals(invocation.arguments()[0], "--force"))
                            return;

                        player.disconnect(
                                Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("kickall.command.kicked")))
                        );
                    });
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("kickall.command.success"))));
            return;
        }

        Optional<RegisteredServer> server = proxyServer.getServer(invocation.arguments()[0].toLowerCase(Locale.ROOT));

        if (!source.hasPermission("vplus.kickall." + invocation.arguments()[0].toLowerCase(Locale.ROOT))) {
            if (!source.hasPermission("vplus.kickall.all")) {
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
                return;
            }
        }
        if (invocation.arguments().length == 2 && Objects.equals(invocation.arguments()[1], "--force") && !source.hasPermission("vplus.kickall.force")) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
            return;
        }

        if (server.isEmpty()) {
            invocation.source().sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("kickall.command.notFound"))));
            return;
        }

        server.get().getPlayersConnected()
                .forEach(player -> {

                    if (source == player) return;

                    if (player.hasPermission("vplus.kickall.bypass") && !source.hasPermission("vplus.kickall.force") && invocation.arguments().length == 1)
                        return;

                    if (player.hasPermission("vplus.kickall.bypass") && source.hasPermission("vplus.kickall.force") && invocation.arguments().length == 1)
                        return;

                    if (player.hasPermission("vplus.kickall.bypass") && source.hasPermission("vplus.kickall.force") && invocation.arguments().length >= 2 && !Objects.equals(invocation.arguments()[1], "--force"))
                        return;

                    if (player.hasPermission("vplus.kickall.bypass") && !source.hasPermission("vplus.kickall.force") && invocation.arguments().length >= 2 && Objects.equals(invocation.arguments()[1], "--force"))
                        return;

                    player.disconnect(
                            Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("kickall.command.kicked")))
                    );
                });
        source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("kickall.command.success"))));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        CommandSource source = invocation.source();
        if (source.hasPermission("vplus.kickall.all")) {
            if (invocation.arguments().length > 0) {
                return proxyServer.getAllServers()
                        .stream()
                        .map(server -> server.getServerInfo().getName().toLowerCase(Locale.ROOT))
                        .filter(s -> s.startsWith(String.join("", invocation.arguments())))
                        .collect(Collectors.toList());
            }
            return proxyServer.getAllServers()
                    .stream()
                    .map(server -> server.getServerInfo().getName().toLowerCase(Locale.ROOT))
                    .collect(Collectors.toList());
        } else {
            if (invocation.arguments().length > 0) {
                return proxyServer.getAllServers()
                        .stream()
                        .map(server -> server.getServerInfo().getName().toLowerCase(Locale.ROOT))
                        .filter(s -> source.hasPermission("vplus.kickall." + s.toLowerCase(Locale.ROOT)) && s.startsWith(String.join("", invocation.arguments())))
                        .collect(Collectors.toList());
            }
            return proxyServer.getAllServers()
                    .stream()
                    .map(server -> server.getServerInfo().getName().toLowerCase(Locale.ROOT))
                    .filter(s -> source.hasPermission("vplus.kickall." + s.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
    }
}
