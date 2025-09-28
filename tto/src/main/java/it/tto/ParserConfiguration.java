package it.tto;




import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class ParserConfiguration {

    private static ParserConfiguration instance;
    private String lineDelimiter;
    private String propertyDelimiter;
    private String fieldValueDelimiter;
    private String objectSeparator;
    private Boolean trimValues = false;
    private Charset charset = StandardCharsets.UTF_8;
    private DateTimeFormatter formatter;

    private ParserConfiguration(){}


    public static ParserConfiguration load(String propFileLocation) throws IOException {
        if(instance == null){
            Properties properties = FileReader.readProperties(propFileLocation);
            instance = new ParserConfiguration();
            instance.setAttributesFromProperties(properties);
        }
        return instance ;
    }

    public static ParserConfiguration load(Properties properties){
        if(instance == null){
            instance = new ParserConfiguration();
            instance.setAttributesFromProperties(properties);
        }
        return instance ;
    };

    public static ParserConfiguration getInstance(){
        if(instance == null){
            instance = new ParserConfiguration();
        }
        return instance ;
    }

    public String getPropertyDelimiter() {
        return propertyDelimiter;
    }
    public String getLineDelimiter() {
        return lineDelimiter;
    }
    public String getFieldValueDelimiter() {
        return fieldValueDelimiter;
    }
    public Boolean getTrimValues() {return trimValues;}
    public String getObjectSeparator() {return objectSeparator;}
    public Charset getCharset() {return charset;}
    public DateTimeFormatter getFormatter() { return formatter; }

    private void setAttributesFromProperties(Properties properties) {
        instance.lineDelimiter = properties.get("tto.parser.line.delimiter").toString();
        instance.propertyDelimiter = properties.get("tto.parser.property.delimiter").toString();
        instance.fieldValueDelimiter = properties.get("tto.parser.field.delimiter").toString();
        instance.trimValues = Boolean.parseBoolean(properties.get("parser.field.trimValues").toString());
        instance.charset = handleCharsets(properties.get("tto.parser.charset").toString());
        instance.formatter = DateTimeFormatter.ofPattern(properties.get("tto.parser.dateTimeFormat").toString());
        instance.objectSeparator = properties.get("tto.parser.object.separator").toString();

    }

    private static Charset handleCharsets(String charsetProp){

        return switch (charsetProp.toLowerCase(Locale.ROOT)) {
            case "utf-8" -> StandardCharsets.UTF_8;
            case "utf-16" -> StandardCharsets.UTF_16;
            case "utf-16le" -> StandardCharsets.UTF_16LE;
            case "utf-16be" -> StandardCharsets.UTF_16BE;
            case "iso_8859_1" -> StandardCharsets.ISO_8859_1;
            case "us_ascii" -> StandardCharsets.US_ASCII;
            default -> throw new IllegalArgumentException("invalid charset");
        };

    }



}
