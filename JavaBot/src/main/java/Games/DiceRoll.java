package Games;

import FileManagement.PLayerData;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class DiceRoll extends Game{
    private PLayerData pLayerData;
    private Player player;

    public <T extends MessageReceivedEvent> DiceRoll(T event) {
        super(event);
        pLayerData = new PLayerData(event.getAuthor(), "21");
        player = getPlayer();
    }

    public void rollDices(long bet){

        int dice1 = new Random().nextInt(1,6);
        int dice2 = new Random().nextInt(1,6);
        MessageChannel channel = event.getChannel();
        String output = "Ilk zarin degeri: "+dice1 +"\n2. zarin degeri: " + dice2;
        long balance = getMoney();
        if(dice1==6 && dice2==6){
            setPlayerMoney(balance+bet*3);
            output+= "\nKazandiniz!" +bet;

        }else if(dice1 >=4 && dice2 >= 4){
            setPlayerMoney(balance+ (long)(bet*1.25));
            output+= "\nIki zarin degeri de 4'ten buyuk oldugu icin oynadiginiz miktarin %125'i ("+((long)bet*1.25)+") hesabiniza yatirildi.";
        }else {
            setPlayerMoney(balance-bet);
            output+= "\nKaybettiniz! Kaybedilen miktar:" +bet;
        }
        balance = getPlayer().getMoney();
        channel.sendMessage(output +"\nYeni bakiyeniz: " +balance).queue();
        pLayerData.logData(player);
    }

    public long getBalance(){
        return player.getMoney();
    }

}
