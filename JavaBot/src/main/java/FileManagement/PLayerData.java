package FileManagement;

import Games.Player;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.io.*;
import java.nio.file.Paths;

public class PLayerData {
    private Player playerData = null;
    private File logFilePath;

    public PLayerData(User user, String gameName){
        File logDirectory = new File(Paths.get("").toAbsolutePath()+File.separator +"PlayerDatas"
                +File.separator+gameName); //Creates a log file inside the PlayerData directory named after the gameName
        logFilePath = new File(logDirectory.getAbsolutePath()+File.separatorChar+user.getId()+".xml");


        if(logFilePath.exists()){ // Import logDirectory file
            ObjectInputStream importLogs = null;
            try {
                importLogs = new ObjectInputStream(new FileInputStream(logFilePath));
                try {
                    playerData =((Player) importLogs.readObject());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Error while importing Object data!");
                }
            } catch (IOException e) {
                System.out.println("Something wrong happened while importing logs!");
                throw new RuntimeException(e);
            }
            finally {
                try {
                    importLogs.close();
                } catch (IOException e) {
                    System.out.println("File import failed!");
                    throw new RuntimeException(e);
                }
            }
        }

        else { //Create new logDirectory file
            BufferedWriter logs = null;
            try {
                logDirectory.mkdirs();
                logs = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilePath))); //create log file for user
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    logs.close();
                } catch (IOException e) {
                    System.out.println("Log file could not be created!");
                    throw new RuntimeException(e);
                }
            }
            playerData = new Player(user);
            logData(playerData); //save default player for user
        }

    }

    private void logData(Player player){
        ObjectOutputStream logger = null;
        try {
            logger = new ObjectOutputStream(new FileOutputStream(logFilePath, false));
            //RandomAccessFile ramLog = new RandomAccessFile(logFilePath, "rsw");
            //String objectData =ramLog.readUTF();
            logger.writeObject(player);
            logger.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not export user data!");
        }finally {
            try {
                logger.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close ObjectOutputStream 'logger'!");
            }
        }

    }

    public Player getPLayerData(User user){
        if(playerData!=null){
            return playerData;
        }
        throw new NullPointerException("PLayer data is null!");
    }




    //GETTERS AND SETTERS

}
