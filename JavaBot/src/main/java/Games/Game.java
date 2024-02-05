package Games;

import FileManagement.GrabPlayerData;
import FileManagement.PLayerData;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Game {
    private Player player = null;


    public MessageReceivedEvent event =null;


    public Game(){
        //initialize at main and pass the JDABuilder in it
    }
    public <T extends MessageReceivedEvent> Game(T event){
        GrabPlayerData dataGrabber = new GrabPlayerData(event);
        player = dataGrabber.userGameData(event);
        this.event= event;


    }
    public Game(User user){
        GrabPlayerData dataGrabber = new GrabPlayerData(user);
        player = dataGrabber.userGameData(user);

    }

    public long getMoney(){
        return player.getMoney();
    }
    public String balanceToString(){
        return player.getBalance();
    }

    public void setPlayerMoney(long amount){
        player.setMoney(amount);
    }

    public Player getPlayer() {
        return player;
    }



}
