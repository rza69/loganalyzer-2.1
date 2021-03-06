import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private String absolutePath = "C:\\Users\\Mateusz\\logfile.txt";

    public static void main(String[] args) {

        if(args == null || (args != null && args.length == 0)){
            System.out.println("Please enter the absolute path to the json file in the system parameter...");
            return;
        }

        String strLine;
        Map<String, Log> logMap = new HashMap();
        Map<String, Event> eventMap = new HashMap<>();
        BufferedReader br = readFile(args[0]);

        try {
            while ((strLine = br.readLine()) != null) {
                createLog(eventMap, logMap, createEvent(strLine));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Event logs after analysis = " + logMap);

        DateSource ds = new DateSource();
        if(!ds.logTableExist()){
            ds.createLogTable();
        }
        ds.insertValue(logMap);
        ds.destroy();

    }

    private static BufferedReader readFile(String absolutePath){
        BufferedReader br = null;

        try {
            File file = new File(absolutePath);
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return br;
    }

    private static void createLog(Map<String, Event> eventMap, Map<String, Log> logMap, Event event){
        if (eventMap.get(event.getId()) == null) {
            eventMap.put(event.getId(), event);
        } else {
            Event event2 = eventMap.get(event.getId());
            Long duration;
            if ("STARTED".equals(event.getState())){
                duration = event2.getTimestamp() - event.getTimestamp();
            } else {
                duration = event.getTimestamp() - event2.getTimestamp();
            }
            Log log = new Log(event.getId(), duration,
                    event.getType() != null ? event.getType() : event2.getType(),
                    event.getHost() != null ? event.getHost() : event2.getHost(), duration > 4);
            logMap.put(event.getId(), log);
        }
    }

    private static Event createEvent(String jsonLine){
        JSONParser jsonParser = new JSONParser();
        JSONObject parse = null;
        try {
            parse = (JSONObject) jsonParser.parse(jsonLine);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String id = (String)parse.get("id");
        String state = (String)parse.get("state");
        String type = (String)parse.get("type");
        String host = (String)parse.get("host");
        Long timestamp = (Long)parse.get("timestamp");
        return new Event(id, state, type, host, timestamp);
    }
}
