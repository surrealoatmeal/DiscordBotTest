package Games;

import FileManagement.PLayerData;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class DiceRoll extends Game{
    private PLayerData pLayerData;
    private Player player;

    public <T extends MessageReceivedEvent> DiceRoll(T event) {
        super(event);
        pLayerData = new PLayerData(event.getAuthor());
        player = getPlayer();
    }
    public <T extends MessageReceivedEvent> DiceRoll(User user){
        super(user);
        pLayerData = new PLayerData(user);
        player = getPlayer();
    }

    public void rollDices(long bet){

        int dice1 = new Random().nextInt(1,6);
        int dice2 = new Random().nextInt(1,6);
        MessageChannel channel = event.getChannel();
        String[] emojiArray = new String[]{":zero:",":one:",":two:",":three:",":four:",":five:",":six:"};
        String output = "Ilk zarin degeri: "+emojiArray[dice1] +"\n2. zarin degeri: " + emojiArray[dice2];
        long balance = getMoney()-bet;
        byte hasWon = 0;

        if(dice1 == dice2){
            switch (dice1){
                case 6->{setPlayerMoney(balance+bet*6);
                output+= "\nKazanilan deger: **" +bet*6+"**";
                }
                case 5->{setPlayerMoney(balance+bet*5);
                    output+= "\nKazanilan deger: **" +bet*5+"**";
                }
                case 4->{setPlayerMoney(balance+bet*4);
                    output+= "\nKazanilan deger: **" +bet*4+"**";
                }
                case 3->{setPlayerMoney(balance+bet*3);
                    output+= "\nKazanilan deger: **" +bet*3+"**";
                }
                case 2->{setPlayerMoney(balance+bet*2);
                    output+= "\nKazanilan deger: **" +bet*2+"**";
                }
                case 1->{setPlayerMoney(balance+bet*1);
                    output+= "\nKazanilan deger: **" +bet+"**";
                }
            }
        }else if(dice1==3&& dice2==1){
            setPlayerMoney(balance+bet*15);
            output+= "\nJACKPOT 31!!!!  Kazanilan deger:  **" +bet*15+"**";
            hasWon++;
        }else {
            setPlayerMoney(balance);
            output+= "\nKaybedilen miktar: **" +bet+"**";
            hasWon--;
        }
        EmbedBuilder eb = new EmbedBuilder();
        if(hasWon<0){
            eb.setTitle("**KAYBETTINIZ**").setColor(Color.RED);
        }else if(hasWon>0){
            eb.setTitle("**JACKPOT**").setColor(Color.YELLOW);
        }else {
            eb.setTitle("**KAZANDINIZ**").setColor(Color.GREEN);
        }

        balance = getPlayer().getMoney();
        eb.setDescription(output +"\n\nYeni bakiyeniz: **" +balance+"**\n");
        eb.setAuthor(player.getName());
        channel.sendMessageEmbeds(eb.build()).queue();
        eb.clear();
        pLayerData.logData(player);
    }

    public long getBalance(){
        return player.getMoney();
    }

}
