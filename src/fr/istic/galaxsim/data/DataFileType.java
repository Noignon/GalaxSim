package fr.istic.galaxsim.data;

import java.util.ArrayList;

public enum DataFileType {

    AMAS("amas et galaxies"),
    GALAXIES("galaxies individuelles");

    private String description;

    DataFileType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ArrayList<String> getDescriptions() {
        DataFileType[] types = DataFileType.values();
        ArrayList<String> descriptions = new ArrayList<String>(types.length);

        for(DataFileType type : types) {
            descriptions.add(type.getDescription());
        }

        return descriptions;
    }

    public static DataFileType getTypeFromDescription(String description) {
        for(DataFileType type : DataFileType.values()) {
            if(type.getDescription().equals(description)) {
                return type;
            }
        }

        return null;
    }

}
