package ar.edu.utn.frba.dds.simeal.models.usuario;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.regex.Pattern;

@Converter(autoApply = true)
public class PatternConverter implements AttributeConverter<Pattern, String> {

    // Converts Pattern to String to store in the DB
    @Override
    public String convertToDatabaseColumn(Pattern pattern) {
        if (pattern == null) {
            return null;
        }
        return pattern.pattern();  // Get the regular expression as String
    }

    // Converts String (from the DB) back to Pattern
    @Override
    public Pattern convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Pattern.compile(dbData);  // Compile the pattern from the String
    }
}

