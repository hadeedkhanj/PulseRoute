package storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


// this class will handle saving and loading lists of serializable objects
public class DataManager {
    
    // using a generic method to save any type of serializable list to a file
    public static <T> void saveData(List<T> dataList, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(dataList);
            System.out.println("[STORAGE] Successfully saved " + dataList.size() + " item(s) to " + filePath);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save data to " + filePath);
            e.printStackTrace();
        }
    }

    // using a generic method to load any serializable list from a file
    @SuppressWarnings("unchecked")            //suppressing compiler warning messages and telling it that i know what im doing
    public static <T> List<T> loadData(String filePath) {
        File file = new File(filePath);

        // returning an empty list if file does not exist
        if (!file.exists()) {
            System.out.println("[STORAGE] No existing file found at " + filePath + ", starting with an empty list");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<T> loadedList = (List<T>) ois.readObject();
            System.out.println("[STORAGE] Successfully loaded " + loadedList.size() + " item(s) from " + filePath);
            return loadedList;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[ERROR] Failed to load data from " + filePath + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
