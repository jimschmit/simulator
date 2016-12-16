/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import model.World;
import model.entity.Cell;

/**
 *
 * @author Jim_Laptop
 */
public class WorldDeserializer implements JsonDeserializer<World>
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
    public World deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        World world = null;
        JsonObject worldObject = json.getAsJsonObject();
        
        int columns = worldObject.get("columns").getAsInt();
        int rows = worldObject.get("rows").getAsInt();
        JsonArray cellArr = worldObject.get("cells").getAsJsonArray();
        ArrayList<Cell> alCells = new ArrayList<>();
        for (JsonElement cellArr1 : cellArr)
        {
            alCells.add(context.deserialize(cellArr1, Cell.class));
        }
        
        world = new World(columns, rows);
        world.alCells = alCells;
        return world;
    }
    
}
