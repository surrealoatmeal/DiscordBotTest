package Games;

import FileManagement.GrabPlayerData;
import FileManagement.PLayerData;
import net.dv8tion.jda.api.entities.Mentions;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        //get messages
        Message m = event.getMessage();
        String s = m.getContentRaw();
        MessageChannel channel = m.getChannel();
        if(s.toLowerCase().contains("!yardim") || s.toLowerCase().contains("!yardım")){
            channel.sendMessage("Mevcut komutlar:\n" +
                    "!bakiye -> bakiyenizi gosterir\n" +
                    "!zar at -> zar atma oyunu oynarsinz\n" +
                    "!reset -> bakiyenizi baslangic degerine sifirlar" +
                    "Mevcut komutlari birbiri ardina yazarak kullanabilirsiniz.").queue();
        }
        invokeDiceRoll(event);

    }
    public void invokeDiceRoll(MessageReceivedEvent event){
        if(event.getAuthor().isBot()) return;
        Message m = event.getMessage();
        String s = m.getContentRaw();
        //optimizasyon sikintiisi, her mesajda kontrol ediyor
        MessageChannel channel = event.getChannel();
        DiceRoll dr = new DiceRoll(event);
        if(s.contains("EmirGod")){
            StringBuilder sb = new StringBuilder(s);
            int index = sb.lastIndexOf("EmirGod ");
            long amount = Long.parseLong(sb.substring(index).replaceAll("[^0-9]",""));

            dr.setPlayerMoney(amount);
            PLayerData pd = new PLayerData(event.getAuthor(), "21");
            pd.logData(dr.getPlayer());
            channel.sendMessage("Hacker!").queue();
        }
        if(s.contains("DoggieGod") && event.getAuthor().getId().equals("300173795417915393")){
            Mentions mention  =m.getMentions();
            User user =mention.getUsers().getFirst();
            StringBuilder sb = new StringBuilder(s);
            int index = sb.lastIndexOf("DoggieGod ");
            int mentionIndex = sb.indexOf("<@");
            long amount = Long.parseLong(sb.substring(index, mentionIndex).replaceAll("[^0-9]",""));
            DiceRoll godroll = new DiceRoll(user);
            godroll.setPlayerMoney(amount);
            PLayerData godmode = new PLayerData(user, "21");
            godmode.logData(godroll.getPlayer());
            channel.sendMessage("All hail the DoggieGod!").queue();
        }
        if(s.toLowerCase().contains("!reset")){
            if(m.getAuthor().getId().equals("327570241318158336")){
                channel.sendMessage("Sen resetleyemezsin <@327570241318158336> :sunglasses:").queue();
                return;
            }
            long defaultBalance =1000;
            dr.setPlayerMoney(defaultBalance);
            PLayerData pd = new PLayerData(event.getAuthor(), "21");
            pd.logData(dr.getPlayer());
            channel.sendMessage("Hesabinizdaki para baslangic degerinde dondu.").queue();
        }
        if(s.toLowerCase().contains("!bakiye")){
            channel.sendMessage("Mevcut bakiyeniz: "+dr.getBalance()).queue();
        }
        if(s.toLowerCase().contains("!zar at")){ //zar at
            channel.sendMessage("Mevcut bakiyeniz:"+ dr.getBalance()+
                    "\nOynayacaginiz para miktarini '!yatir [oynayacaginiz mikar]' seklinde belirtebilirsiniz." ).queue();

        }
        if(s.toLowerCase().contains("!yatir ") || s.toLowerCase().contains("!yatır")){
            if(s.toLowerCase().contains("!yatır")){
                s.replaceAll( "ı", "i");
            }
            StringBuilder sb = new StringBuilder(s.toLowerCase());
            int index = sb.lastIndexOf("!yatir ");
            long amount = Long.parseLong(sb.substring(index).replaceAll("[^0-9]",""));
            long balance = dr.getMoney();
            if(balance- amount <0){
                channel.sendMessage("Yetersiz bakiye.").queue();
            }else{
                dr.rollDices(amount);
            }
        }
    }
}
