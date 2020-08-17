package local.nix.csv.mapper.mapper;
import local.nix.csv.mapper.annotation.Column;
import local.nix.csv.mapper.data.PersonalData;
import local.nix.csv.mapper.parser.CsvTable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvMapper {

    private static List<PersonalData> personalDataList;

    public static List<PersonalData> getMappedObjects(String fileName) throws Exception {
        mapToObjects(fileName);
        return personalDataList;
    }

    private static void mapToObjects(String fileName) throws Exception {
        personalDataList = new ArrayList<>();
        Path source = Paths.get(CsvMapper.class.getClassLoader().getResource(fileName).toURI());
        CsvTable table = CsvTable.fromFile(source).orElseThrow();
        List<String> headers = table.getHeaders();
        for (int i = 0; i < table.height(); i++) {
            PersonalData currentObject = PersonalData.class.getConstructor().newInstance();
            List<String> valuesOfFieldsOfCurrentObject = table.getRow(i);
            for (int j = 0; j < headers.size(); j++) {
                Field currentField = getFieldByAnnotationValue(headers.get(j));
                currentField.setAccessible(true);
                String valueOfCurrentField = valuesOfFieldsOfCurrentObject.get(j);
                Class typeOfCurrentField = currentField.getType();
                currentField.set(currentObject, convertStringToTypeOfField(valueOfCurrentField, typeOfCurrentField));

            }

            personalDataList.add(currentObject);
        }

    }

    private static Field getFieldByAnnotationValue(String annotationValue) {
        Field[] fieldsOfPersonalDataClass = PersonalData.class.getDeclaredFields();
        Field result = null;
        for (Field field : fieldsOfPersonalDataClass) {
            if (field.isAnnotationPresent(Column.class)) {
                if (annotationValue.equals(field.getAnnotation(Column.class).value())) {
                    result = field;
                }
            }
        }

        if (result == null) {
            throw new IllegalArgumentException("AnnotationValue " + annotationValue + " is not valid");
        }

        return result;
    }

    private static <T> T convertStringToTypeOfField(String s, Class<T> type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        if (type.isEnum()) {
            return (T) Enum.valueOf((Class<Enum>) type, s.toUpperCase());
        }

        if (type.isPrimitive()) {
            switch (type.getSimpleName()) {
                case "byte":
                    return (T) Byte.class.getConstructor(String.class).newInstance(s);
                case "short":
                    return (T) Short.class.getConstructor(String.class).newInstance(s);

                case "int":
                    return (T) Integer.class.getConstructor(String.class).newInstance(s);

                case "long":
                    return (T) Long.class.getConstructor(String.class).newInstance(s);

                case "float":
                    return (T) Float.class.getConstructor(String.class).newInstance(s);

                case "double":
                    return (T) Double.class.getConstructor(String.class).newInstance(s);

                case "boolean":
                    return (T) Boolean.class.getConstructor(String.class).newInstance(s);

                case "char":
                    return (T) Character.class.getConstructor(String.class).newInstance(s);

            }
        } else {
            switch (type.getSimpleName()) {
                case "Byte":
                case "Short":
                case "Integer":
                case "Long":
                case "Float":
                case "Double":
                case "Boolean":
                case "Character":
                case "String":
                    return type.getConstructor(String.class).newInstance(s);

            }

        }
        return null;
    }
}
