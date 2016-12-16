/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sun.corba.se.impl.orb.ORBVersionImpl;
import java.awt.geom.Point2D;
import java.lang.reflect.Type;
import model.entity.*;

/**
 *
 * @author Jim_Laptop
 */
public class CellSerializer implements JsonSerializer<Cell>
{

    /**
     *
     * @param t
     * @param type
     * @param jsc
     * @return
     */
    @Override
    public JsonElement serialize(Cell t, Type type, JsonSerializationContext jsc)
    {
        JsonObject cellObject = new JsonObject();
        
        cellObject.addProperty("type", t.getClass().getSimpleName());
        
        JsonElement pointElement = jsc.serialize(t.oldPosition);
        cellObject.add("point", pointElement);
        return cellObject;
    }

    
}
