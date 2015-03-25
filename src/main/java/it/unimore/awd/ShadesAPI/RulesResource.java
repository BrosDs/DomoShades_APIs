package it.unimore.awd.ShadesAPI;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimore.awd.ShadesAPI.Classes.Rules;
import it.unimore.awd.ShadesAPI.Classes.Window;
import org.restlet.resource.Delete;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.util.List;

public class RulesResource extends ServerResource{


    /**
     * /rule?       owner=owner_email_address&
     *              home=home_id_number&
     *              id=floor_number&
     *              room_id=room_id_on_this_floor&
     *              window_id=window_id_in_this_room&
     *              rule_name=rule_name&
     *              rule_priority=[1-99]&
     *              rule_start=hh:mm&
     *              rule_end=hh:mm&
     *              rule_closed=[0-100]
     **/
    @Put
    public String putRule(){
        try {
            Window w = WindowResource.getWindow(getKeyValue("owner"),Long.parseLong(getKeyValue("home")),Long.parseLong(getKeyValue("id")),Long.parseLong(getKeyValue("room_id")),Long.parseLong(getKeyValue("window_id")));
            Integer rulePriority = Integer.parseInt(getKeyValue("rule_priority"));
            if(!intervallContains(1,99,rulePriority))
                throw new error("Priority not in range");

            Integer ruleClosed = Integer.parseInt(getKeyValue("rule_closed"));
            if(!intervallContains(0,100,ruleClosed))
                throw new error("Closed percentage not in range");


            Rules newRule = new Rules(getKeyValue("rule_name"),rulePriority,getKeyValue("rule_start"),getKeyValue("rule_end"),ruleClosed);
            List<Rules> newRuleList = w.getRulesLists();
            newRuleList.add(newRule);
            w.setRulesLists(newRuleList);

            return new WindowResource().putWindow();
        } catch (error error){
            return error.toString();
        }
    }

    @Delete
    public String deleteRule(){
        try {
            Window w = WindowResource.getWindow(getKeyValue("owner"),Long.parseLong(getKeyValue("home")),Long.parseLong(getKeyValue("id")),Long.parseLong(getKeyValue("room_id")),Long.parseLong(getKeyValue("window_id")));
            Integer rulePriority = Integer.parseInt(getKeyValue("rule_priority"));
            if(!intervallContains(1,99,rulePriority))
                throw new error("Priority not in range");

            Integer ruleClosed = Integer.parseInt(getKeyValue("rule_closed"));
            if(!intervallContains(0,100,ruleClosed))
                throw new error("Closed percentage not in range");


            Rules newRule = new Rules(getKeyValue("rule_name"),rulePriority,getKeyValue("rule_start"),getKeyValue("rule_end"),ruleClosed);
            List<Rules> newRuleList = w.getRulesLists();
            newRuleList.remove(newRule);
            w.setRulesLists(newRuleList);

            return new WindowResource().putWindow();
        } catch (error error){
            return error.toString();
        }
    }

    /** Private methods **/
    private String getRule(Rules r){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(r);
    }

    private String getKeyValue(String key_name) throws error {
        if(getQueryValue(key_name)!=null) {
            String key_val = getQueryValue(key_name);
            if (key_val.isEmpty())
                throw new error("No key value found");
            return key_val;
        }else
            throw new error("Key name not found");
    }

    private static boolean intervallContains(int low, int high, int n) {
        return n >= low && n <= high;
    }
}
