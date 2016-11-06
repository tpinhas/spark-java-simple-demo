package phonebook;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonPhoneRepository {
    private final static Logger log = Logger.getLogger(JsonPhoneRepository.class.getName());
    public static final String PHONEBOOK_JSON = "phonebook.json";

    static void save(Map<String, Object> phoneBook) throws IOException {
        try {
            FileWriter file = new FileWriter(PHONEBOOK_JSON);
            JSONArray jsonArray = new JSONArray();
            int counter = 0;
            for(Map.Entry entry: phoneBook.entrySet()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", entry.getKey());
                jsonObject.put("phone", entry.getValue());
                jsonArray.add(jsonObject);
                counter++;
            }
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();
            log.info("Saved " + counter + " contacts in phone book");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error saving phone book data ", e);
        }
    }

    static void load(Map<String, Object> phoneBook) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(PHONEBOOK_JSON));
            JSONArray jsonArray = (JSONArray) obj;
            int counter = 0;
            for (Object aJsonArray : jsonArray) {
                JSONObject jsonObject = (JSONObject) aJsonArray;
                phoneBook.put((String) jsonObject.get("name"), jsonObject.get("phone"));
                counter++;
            }
            log.info("Starting with " + counter + " contacts in phone book");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error loading phone book data ", e);
        }
    }

}
