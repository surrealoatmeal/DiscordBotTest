import Commands.CheguListener;


import Games.GameMessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

    public static void main(String[] args) {
        JDABuilder builder = JDABuilder.createDefault("MTIwMzgwMDIxMjg4MDA5NzQyMQ.Gg5vjG.8GzDzxmoZ-Og9LgdfBZhiSz5mT3OGGraD7p4PQ");
        JDA bot = builder.build();
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(new CheguListener());
        builder.addEventListeners(new GameMessageListener());


        try {
            builder.build().awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException("A fail has occurred while building the bot.");
        }
    }
}
