package consumer;

import converter.JsonConverter;
import data.Constants;
import messages.DirectoryMessage;
import sender.DatabaseSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * Created by nicob on 02.11.2016.
 * class for checking the directory for updates
 */

public class DirectoryListener implements Runnable {
    private static DirectoryListener instance;
    private String filePath;

    private DirectoryListener(String filePath) {
        this.filePath = filePath;
    }

    public static DirectoryListener getDirectoryListener(String filePath){
        if (instance == null){
            instance = new DirectoryListener(filePath);
        }
        return instance;
    }

    private static void checkDirectory(Path path) {
        try {
            //check if given path is a folder
            boolean isFolder = (boolean) Files.getAttribute(path, "basic:isDirectory", LinkOption.NOFOLLOW_LINKS);

            if (!isFolder){
                throw new IllegalArgumentException("Path: " + path + " is not a folder!");
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        FileSystem fileSystem = path.getFileSystem();

        try (WatchService watchService = fileSystem.newWatchService()){
            path.register(watchService, ENTRY_CREATE); //wenn andere Events (ENTRY_MODIFY/ENTRY_DELETE gewuenscht sind, diese per Komma anhaengen

            WatchKey watchKey = null;

            while (true) {
                watchKey = watchService.take();

                WatchEvent.Kind kind = null;
                //all events in the given folder are noticed
                for (WatchEvent watchEvent : watchKey.pollEvents()) {
                    kind = watchEvent.kind();
                    if (OVERFLOW == kind) {
                        //empty if-block; only file-create is important
                    }
                    else if (ENTRY_CREATE == kind){
                        Path filePath = ((WatchEvent<Path>) watchEvent).context();
                        String file = path + fileSystem.getSeparator() + filePath.toString();
                        BufferedReader data = new BufferedReader(new FileReader(file));
                        String jsonString = data.readLine();
                        while (jsonString != null){
                            DirectoryMessage message = JsonConverter.getInstance().getDirectoryMessage(jsonString);
                            message.setOrderNumber(Consumer.getCURRENT_ORDER_NUMBER());

                            if (!Constants.TESTING) {
                                DatabaseSender.getDatabaseSender().insertMessage(message);
                            }
                            System.out.println(message);

                            jsonString = data.readLine();
                        }
                        data.close();
                    }
                }

                if (!watchKey.reset()){
                    break;
                }
            }
        } catch (IOException | InterruptedException ioEx) {
            ioEx.printStackTrace();
        }
    }

    @Override
    public void run() {
        File directory = new File(filePath);
        checkDirectory(directory.toPath());
    }
}