package Games;

import FileManagement.PLayerData;
import net.dv8tion.jda.api.entities.User;
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
    public <T extends MessageReceivedEvent> DiceRoll(User user){
        super(user);
        pLayerData = new PLayerData(user, "21");
        player = getPlayer();
    }

    public void rollDices(long bet){

        int dice1 = new Random().nextInt(1,6);
        int dice2 = new Random().nextInt(1,6);
        MessageChannel channel = event.getChannel();
        String output = "Ilk zarin degeri: "+dice1 +"\n2. zarin degeri: " + dice2;
        long balance = getMoney()-bet;

        if(dice1 == dice2){
            switch (dice1){
                case 6->{setPlayerMoney(balance+bet*6);
                output+= "\nKazandiniz! Kazanilan deger: " +bet*6;
                }
                case 5->{setPlayerMoney(balance+bet*5);
                    output+= "\nKazandiniz! Kazanilan deger: " +bet*5;
                }
                case 4->{setPlayerMoney(balance+bet*4);
                    output+= "\nKazandiniz! Kazanilan deger: " +bet*4;
                }
                case 3->{setPlayerMoney(balance+bet*3);
                    output+= "\nKazandiniz! Kazanilan deger: " +bet*3;
                }
                case 2->{setPlayerMoney(balance+bet*2);
                    output+= "\nKazandiniz! Kazanilan deger: " +bet*2;
                }
                case 1->{setPlayerMoney(balance+bet*1);
                    output+= "\nKazandiniz! Kazanilan deger: " +bet;
                }
            }
        }else if(dice1==3&& dice2==1){
            setPlayerMoney(balance+bet*15);
            output+= "\nJACKPOT 31!!!!  Kazanilan deger:  " +bet*15;
        }else {
            setPlayerMoney(balance);
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
