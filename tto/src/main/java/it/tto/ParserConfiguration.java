package it.tto;




import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

public class ParserConfiguration {

    public static ParserConfiguration instance;
    private String lineDelimiter;
    private String propertyDelimiter;
    private String fieldValueDelimiter;
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

    public void setPropertyDelimiter(String propertyDelimiter) {
        this.propertyDelimiter = propertyDelimiter;
    }

    public String getLineDelimiter() {
        return lineDelimiter;
    }

    public void setLineDelimiter(String lineDelimiter) {
        this.lineDelimiter = lineDelimiter;
    }

    public String getFieldValueDelimiter() {
        return fieldValueDelimiter;
    }

    public void setFieldValueDelimiter(String fieldValueDelimiter) {
        this.fieldValueDelimiter = fieldValueDelimiter;
    }

    public Boolean getTrimValues() {return trimValues;}

    public void setTrimValues(Boolean trimValues) {this.trimValues = trimValues;
    }

    public Charset getCharset() {return charset;}

    public void setCharset(Charset charset) {this.charset = charset;}

    private void setAttributesFromProperties(Properties properties) {
        instance.lineDelimiter = properties.get("tto.parser.line.delimiter").toString();
        instance.propertyDelimiter = properties.get("tto.parser.field.delimiter").toString();
        instance.fieldValueDelimiter = properties.get("tto.parser.field.valuedelimiter").toString();
        instance.trimValues = Boolean.parseBoolean(properties.get("parser.field.trimValues").toString());
        instance.charset = handleCharsets(properties.get("tto.parser.charset").toString());

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
