package persistence;


import org.json.JSONObject;


/*
 * The code within this package (Writable, JsonWriter, JsonReader) all take inspiration from:
 *
 *    Project Name: JsonSerializationDemo
 *    Author: Paul Carter
 *    Date: last updated Oct 16, 2021
 *    Code version: 20210307
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *
 */

// Interface that stores the toJson() method to implement
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
