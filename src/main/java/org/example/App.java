package org.example;


import javax.script.ScriptException;
import java.util.*;
import java.util.stream.Collectors;


public class App {

    public static void main( String[] args ) throws ScriptException {
      List<Map<String,String>> listMap = new ArrayList<>();
      Map<String,String> mapOne = new HashMap<>();
      mapOne.put("name1","value11");
      mapOne.put("name2","value12");
      listMap.add(mapOne);
      Map<String,String> mapTwo = new HashMap<>();
      mapOne.put("name1","value21");
      mapOne.put("name2","value22");
      listMap.add(mapTwo);
      List<String> listName = listMap.stream().map(e->(String)e.get("name1")).collect(Collectors.toList());
      System.out.println(listName.toString());

      System.out.println(Arrays.asList(null,null));
    }

}
