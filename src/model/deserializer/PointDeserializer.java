/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.awt.Point;
import java.lang.reflect.Type;

/**
 *
 * @author Jim_Laptop
 */
public class PointDeserializer implements JsonDeserializer<Point>
{

    /**
     *
     * @param json
     * @param type
     * @param context
     * @return
     * @throws JsonParseException
     */
    @Override
    public Point deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        int x = jsonObject.get("x").getAsInt();
        int y = jsonObject.get("y").getAsInt();
        
        return new Point(x, y);
    }
    
}
