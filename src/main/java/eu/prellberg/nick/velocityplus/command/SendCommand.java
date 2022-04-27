package eu.prellberg.nick.velocityplus.command;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import eu.prellberg.nick.velocityplus.VelocityPlus;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.*;

public class SendCommand implements SimpleCommand {

    private final ProxyServer proxyServer;
    private final VelocityPlus velocityPlus;
    private final Logger logger;

    public SendCommand(ProxyServer proxyServer, VelocityPlus velocityPlus, Logger logger) {
        this.proxyServer = proxyServer;
        this.velocityPlus = velocityPlus;
        this.logger = logger;
        CommandManager manager = proxyServer.getCommandManager();
        manager.register(manager.metaBuilder("send").build(), this);
    }

    public boolean testIsOffline(String serverName) {
        Optional<RegisteredServer> toTest = proxyServer.getServer(serverName);
        if (toTest.isPresent()) {
            RegisteredServer server = toTest.get();
            try {
                server.ping().get();
                return false;
            } catch (Exception err) {
                return true;
            }
        }
        return true;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        List<String> listServers = new ArrayList<>(proxyServer
                .getAllServers()
                .stream()
                .map(server -> server.getServerInfo().getName())
                .toList());

        if (invocation.arguments().length == 0) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notEnoughArgs"))));
            return;
        }

        if (invocation.arguments().length == 1) {
            source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notEnoughArgs"))));
            return;
        }

        if (Objects.equals(invocation.arguments()[0], "all")) {
            if (!source.hasPermission("vplus.send.all")) {
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
                return;
            }
            if (listServers.contains(invocation.arguments()[1])) {
                Optional<RegisteredServer> toServer = proxyServer.getServer(invocation.arguments()[1]);
                if (toServer.isEmpty()) {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notFound"))));
                    return;
                }
                if (testIsOffline(toServer.get().getServerInfo().getName())) {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notAvailable"))));
                    return;
                }
                proxyServer
                        .getAllPlayers()
                        .stream()
                        .filter(player -> !Objects.equals(player.getCurrentServer().get().getServerInfo().getName(), invocation.arguments()[1]))
                        .forEach(player -> player.createConnectionRequest(toServer.get()).fireAndForget());
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.result").replaceAll("<<playercount>>", String.valueOf(proxyServer.getAllPlayers().stream().filter(p -> !Objects.equals(p.getCurrentServer().get().getServerInfo().getName(), invocation.arguments()[1])).toList().size())).replaceAll("<<server>>", toServer.get().getServerInfo().getName()))));
                return;
            }
        }

        if (Objects.equals(invocation.arguments()[0], "current")) {
            if (!source.hasPermission("vplus.send.current")) {
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
                return;
            }
            if (listServers.contains(invocation.arguments()[1])) {
                RegisteredServer fromServer = ((Player) source).getCurrentServer().get().getServer();
                Optional<RegisteredServer> toServer = proxyServer.getServer(invocation.arguments()[1]);
                if (toServer.isEmpty()) {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notFound"))));
                    return;
                }
                if (testIsOffline(toServer.get().getServerInfo().getName())) {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notAvailable"))));
                    return;
                }
                fromServer
                        .getPlayersConnected()
                        .stream()
                        .filter(player -> !Objects.equals(player.getCurrentServer().get().getServerInfo().getName(), invocation.arguments()[1]))
                        .forEach(player -> player.createConnectionRequest(toServer.get()).fireAndForget());
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.result").replaceAll("<<playercount>>", String.valueOf(proxyServer.getAllPlayers().stream().filter(p -> !Objects.equals(p.getCurrentServer().get().getServerInfo().getName(), invocation.arguments()[1])).toList().size())).replaceAll("<<server>>", toServer.get().getServerInfo().getName()))));
                return;
            }
        }

        if (listServers.contains(invocation.arguments()[0])) {
            if (!source.hasPermission("vplus.send.server")) {
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
                return;
            }
            if (listServers.contains(invocation.arguments()[1])) {
                RegisteredServer fromServer = ((Player) source).getCurrentServer().get().getServer();
                Optional<RegisteredServer> toServer = proxyServer.getServer(invocation.arguments()[1]);
                if (toServer.isEmpty()) {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notFound"))));
                    return;
                }
                if (testIsOffline(toServer.get().getServerInfo().getName())) {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notAvailable"))));
                    return;
                }
                fromServer
                        .getPlayersConnected()
                        .stream()
                        .filter(player -> !Objects.equals(player.getCurrentServer().get().getServerInfo().getName(), invocation.arguments()[1]))
                        .forEach(player -> player.createConnectionRequest(toServer.get()).fireAndForget());
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.result").replaceAll("<<playercount>>", String.valueOf(proxyServer.getAllPlayers().stream().filter(p -> !Objects.equals(p.getCurrentServer().get().getServerInfo().getName(), invocation.arguments()[1])).toList().size())).replaceAll("<<server>>", toServer.get().getServerInfo().getName()))));
                return;
            }
        }

        List<String> listUsers = new ArrayList<>(proxyServer
                .getAllPlayers()
                .stream()
                .map(Player::getUsername)
                .toList());
        if (listUsers.contains(invocation.arguments()[0])) {
            if (!source.hasPermission("vplus.send.user")) {
                source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("general.missingPermissions"))));
                return;
            }
            if (listServers.contains(invocation.arguments()[1])) {
                Optional<RegisteredServer> toServer = proxyServer.getServer(invocation.arguments()[1]);
                if (toServer.isEmpty()) {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notFound"))));
                    return;
                }
                if (testIsOffline(toServer.get().getServerInfo().getName())) {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.notAvailable"))));
                    return;
                }
                if (!Objects.equals(proxyServer.getPlayer(invocation.arguments()[0]).get().getCurrentServer().get().getServerInfo().getName(), toServer.get().getServerInfo().getName())) {
                    proxyServer.getPlayer(invocation.arguments()[0]).get().createConnectionRequest(toServer.get()).fireAndForget();
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.result").replaceAll("<<playercount>>", "1")).replaceAll("<<server>>", toServer.get().getServerInfo().getName())));
                } else {
                    source.sendMessage(Component.text(String.join(" ", velocityPlus.getConfig().getString("general.prefix"), velocityPlus.getConfig().getString("send.command.result").replaceAll("<<playercount>>", "0")).replaceAll("<<server>>", toServer.get().getServerInfo().getName())));
                }
            }
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] currentArgs = invocation.arguments();

        if (currentArgs.length == 0) {
            List<String> arg1suggests = new ArrayList<>();
            if (source.hasPermission("vplus.send.all")) arg1suggests.add("all");
            if (source.hasPermission("vplus.send.current")) arg1suggests.add("current");
            if (source.hasPermission("vplus.send.server")) arg1suggests.addAll(
                    proxyServer
                            .getAllServers()
                            .stream()
                            .map(server -> server.getServerInfo().getName())
                            .toList());
            if (source.hasPermission("vplus.send.user")) arg1suggests.addAll(
                    proxyServer
                            .getAllPlayers()
                            .stream()
                            .map(Player::getUsername)
                            .toList());

            return arg1suggests;
        }
        if (currentArgs.length == 1) {
            List<String> arg1suggests = new ArrayList<>();
            if (source.hasPermission("vplus.send.all")) arg1suggests.add("all");
            if (source.hasPermission("vplus.send.current")) arg1suggests.add("current");
            if (source.hasPermission("vplus.send.server")) arg1suggests.addAll(
                    proxyServer
                            .getAllServers()
                            .stream()
                            .map(server -> server.getServerInfo().getName())
                            .toList());
            if (source.hasPermission("vplus.send.user")) arg1suggests.addAll(
                    proxyServer
                            .getAllPlayers()
                            .stream()
                            .map(Player::getUsername)
                            .toList());

            return arg1suggests.stream().filter(s -> s.startsWith(invocation.arguments()[0])).toList();
        }

        if (currentArgs.length == 2) {
            if (source.hasPermission("vplus.send.all") || source.hasPermission("vplus.send.current") || source.hasPermission("vplus.send.server") || source.hasPermission("vplus.send.user")) {
                List<String> arg2suggests = new ArrayList<>(proxyServer
                        .getAllServers()
                        .stream()
                        .map(server -> server.getServerInfo().getName())
                        .toList());
                return arg2suggests.stream().filter(s -> s.startsWith(invocation.arguments()[1])).toList();
            }
        }

        return Collections.emptyList();
    }

}
