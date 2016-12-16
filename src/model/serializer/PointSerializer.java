/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.awt.Point;
import java.lang.reflect.Type;

/**
 *
 * @author Jim_Laptop
 */
public class PointSerializer implements JsonSerializer<Point>
{

    /**
     *
     * @param t
     * @param type
     * @param context
     * @return
     */
    @Override
    public JsonElement serialize(Point t, Type type, JsonSerializationContext context)
    {
        JsonObject pointObject = new JsonObject();
        pointObject.addProperty("x", (int)t.getX());
        pointObject.addProperty("y", (int)t.getY());
        return pointObject;
    }
    
}
