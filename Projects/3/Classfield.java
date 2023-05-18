import java.lang.reflect.Field;

public class Classfield extends Classelement {

    private Field field;
    private Class<?> type;
    private String name;


    @Override
    public String toString() {
        return String.format("%s %s %s", modifier, field.getType().getSimpleName(), field.getName());
    }

    public Field getField() {
        return field;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Class<?> getType() {
        return type;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getModifier() {
        return modifier;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
