package body.categorySport;

import org.json.JSONObject;

public class CreateCategorySportBody {
    public JSONObject getBody(){
        JSONObject body = new JSONObject();
        body.put("name", "Kategori 1 Selvia");
        return body;
    }
}
