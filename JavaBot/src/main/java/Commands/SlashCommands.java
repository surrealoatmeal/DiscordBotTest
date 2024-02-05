package Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class SlashCommands {
    public void enableCommands(JDA bot){
        bot.updateCommands().addCommands(
                Commands.slash("21", "21 oyunu.")
        );
    }
}
