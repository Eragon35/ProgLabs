//package Lab5;
//
//import Lab3.Humanoid;
//import Lab3.Palace;
//import com.google.gson.*;
//
//import java.lang.reflect.Type;
//
//public class CustomConverter implements JsonDeserializer<Humanoid> {
//
//
//    public Humanoid deserialize(JsonElement json, Type type,JsonDeserializationContext context) throws JsonParseException {
//        JsonObject object = json.getAsJsonObject();
//        String name = new String(object.get("name").toString());
//        Palace place = Palace.valueOf(object.get("palace").toString());
//        return new Humanoid(name, place);
//    }
//}