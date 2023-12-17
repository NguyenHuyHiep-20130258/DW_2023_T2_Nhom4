package org.example.Entity;

public class DataFileConfig {
    private int id;
    private String name;
    private String description;
    private String source_path;
    private String location;
    private int flag;

    public DataFileConfig(int id, String name, String description, String source_path, String location, int flag) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.source_path = source_path;
        this.location = location;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource_path() {
        return source_path;
    }

    public void setSource_path(String source_path) {
        this.source_path = source_path;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "DataFileConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", source_path='" + source_path + '\'' +
                ", location='" + location + '\'' +
                ", flag=" + flag +
                '}';
    }
}
