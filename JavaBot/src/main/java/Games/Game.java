package Games;

import FileManagement.GrabPlayerData;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Game {
    private Player playerData = null;

    public MessageReceivedEvent event;

    public Game(){
        //initialize at main and pass the JDABuilder in it
    }
    public <T extends MessageReceivedEvent> Game(T event){
        GrabPlayerData dataGrabber = new GrabPlayerData(event);
        playerData = dataGrabber.userGameData(event);
        this.event= event;

    }

    public long getMoney(){
        return playerData.getMoney();
    }
    public String balanceToString(){
        return playerData.getBalance();
    }

    public void setPlayerMoney(long amount){
        playerData.setMoney(amount);
    }

    public Player getPlayerData() {
        return playerData;
    }



}
