package it.tto;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.NoSuchObjectException;
import java.util.*;


public class ObjectParser<T>{

    private ParserConfiguration parserConfiguration;
    private final Class<T> type;

    public ObjectParser(Class<T> type){
        this.type = type;
    }

    public void setParserConfiguration(ParserConfiguration conf){
        parserConfiguration = conf;
    }


    public List<T> parseFromFile(String filePath) throws IOException {

        byte [] file = FileReader.readFileAsBytes(filePath);

        List<T> objectList = new ArrayList<>();

        if(Objects.isNull(parserConfiguration))
            throw new NoSuchObjectException("parser configuration for text files is missing!");

        String content = new String(file, parserConfiguration.getCharset());
        String[] objectsAsString = content.split("\\R");

        for (String object : objectsAsString) {
            Map<String, String> objectAttributes = buildObjectAttributes(object);

            T buildedObject = buildObject(objectAttributes);
            objectList.add(buildedObject);

            System.out.println(objectAttributes);
        }
        return objectList;
    }

    private Map<String, String> buildObjectAttributes(String object) {
        String[] fields = object.split(parserConfiguration.getPropertyDelimiter());
        Map<String, String> objectAttributes = new HashMap<>();
        for(String field : fields){

            String attributeName = field.substring(0, field.indexOf(parserConfiguration.getFieldValueDelimiter()));
            String attributeValue = field.substring(field.indexOf(parserConfiguration.getFieldValueDelimiter()) +1);
            objectAttributes.put(attributeName,attributeValue);
        }
        return objectAttributes;
    }

    private T buildObject(Map<String,String> objectAttributes){
        try {
            T instance = type.getDeclaredConstructor().newInstance();

            objectAttributes.forEach((attr, value) -> {
                try {

                    var field = type.getDeclaredField(attr);
                    field.setAccessible(true);


                    switch (field.getType().getSimpleName()) {
                        case "int", "Integer" -> field.set(instance, Integer.parseInt(value));
                        case "long", "Long" -> field.set(instance, Long.parseLong(value));
                        case "double", "Double" -> field.set(instance, Double.parseDouble(value));
                        case "float", "Float" -> field.set(instance, Float.parseFloat(value));
                        case "short", "Short" -> field.set(instance, Short.parseShort(value));
                        case "byte", "Byte" -> field.set(instance, Byte.parseByte(value));
                        case "boolean", "Boolean" -> field.set(instance, Boolean.parseBoolean(value));
                        case "char", "Character" -> field.set(instance, value.charAt(0));
                        case "String" -> field.set(instance, value);
                        case "BigDecimal" -> field.set(instance, new BigDecimal(value));
                        case "BigInteger" -> field.set(instance, new BigInteger(value));
                        default -> throw new IllegalArgumentException("Unsupported field type: " + field.getType().getSimpleName());
                    }


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error during object creation", e);
        }
    }
}
