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
import model.entity.AntiVirus;
import model.entity.Cell;
import model.entity.Computer;
import model.entity.Virus;

/**
 *
 * @author Jim_Laptop
 */
public class CellDeserializer implements JsonDeserializer<Cell>
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
    public Cell deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject cellObject = json.getAsJsonObject();
        
        Point oldPoint = context.deserialize(cellObject.get("point"), Point.class);
        
        switch(cellObject.get("type").getAsString()){
            case "Virus": return new Virus(oldPoint);
            case "AntiVirus": return new AntiVirus(oldPoint);
            case "Computer": return new Computer(oldPoint);
            default: return null;
        }
    }
    
}
