package Games;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        //get messages
        Message m = event.getMessage();
        String s = m.getContentRaw();
        invokeDiceRoll(event);

    }
    public void invokeDiceRoll(MessageReceivedEvent event){
        Message m = event.getMessage();
        String s = m.getContentRaw();
        //optimizasyon sikintiisi, her mesajda kontrol ediyor
        MessageChannel channel = event.getChannel();
        DiceRoll dr = new DiceRoll(event);
        if(s.toLowerCase().contains("!zar at")){ //zar at
            channel.sendMessage("Mevcut bakiyeniz:"+ dr.getPlayerData().getBalance()+
                    "\nOynayacaginiz para miktarini '!yatir [oynayacaginiz mikar]' seklinde belirtebilirsiniz." ).queue();

        }
        if(s.toLowerCase().contains("!yatir ")){
            StringBuilder sb = new StringBuilder(s.toLowerCase());
            int index = sb.lastIndexOf("!yatir ");
            long amount = Long.parseLong(sb.substring(index).replaceAll("[^0,9]",""));
            long balance = dr.getMoney();
            if(balance- amount <=0){
                channel.sendMessage("Yetersiz bakiye.").queue();
            }else{
                dr.rollDices(amount);
            }
        }
    }
}
