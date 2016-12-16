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
import java.lang.reflect.Type;
import model.World;

/**
 *
 * @author Jim_Laptop
 */
public class WorldSerializer implements JsonSerializer<World>
{

    /**
     *
     * @param world
     * @param type
     * @param context
     * @return
     */
    @Override
    public JsonElement serialize(World world, Type type, JsonSerializationContext context)
    {
        JsonObject worldObject = new JsonObject();
        worldObject.addProperty("columns", world.columns);
        worldObject.addProperty("rows", world.rows);
        
        JsonElement cellsObject = context.serialize(world.alCells);
        worldObject.add("cells", cellsObject );
        return worldObject;
    }
    
}
