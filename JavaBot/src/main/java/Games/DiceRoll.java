package Games;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class DiceRoll extends Game{

    public <T extends MessageReceivedEvent> DiceRoll(T event) {
        super(event);
    }

    public void rollDices(long bet){

        int dice1 = new Random().nextInt(1,6);
        int dice2 = new Random().nextInt(1,6);
        MessageChannel channel = event.getChannel();
        String output = "Ilk zarin degeri: "+dice1 +"\n2. zarin degeri: " + dice2;
        if(dice1==6 && dice2==6){
            long balance = getMoney();
            getPlayerData().setMoney(balance+bet*3);
            output+= "\nKazandiniz!";

        }else if(dice1 >=3 && dice2 >= 3){
            getPlayerData().setMoney(getMoney()+ (long)(bet*1.25));
        }else if(dice1+dice2>=5){
            output+= "\nUcuz atlattiniz! Bakiyenizde bir dedgisiklik olmadi.";
            channel.sendMessage(output).queue();
            return;
        }else {
            getPlayerData().setMoney(getMoney()-bet);
            output+= "\nKaybettiniz!";
        }
        channel.sendMessage(output +"\nYeni bakiyeniz: " +getPlayerData().getBalance()).queue();
    }


}
