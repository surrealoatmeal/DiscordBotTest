package Games;

import net.dv8tion.jda.api.entities.User;

public class Player {
    private final User id;
    private long money  = 1000;
    private String balance = money+"tl";


    public Player(User user){
        id = user;
    }

    //GETTERS AND SETTERS

    public User getId() {
        return id;
    }

    public long getMoney() {
        return money;
    }

    public String getBalance() {
        return balance;
    }

    public void setMoney(long money) {
        if(this.money-money <=0){
            this.money =0;
            return;
        }
        this.money = money;
    }
}
