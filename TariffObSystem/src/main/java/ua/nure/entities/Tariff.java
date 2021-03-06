package ua.nure.entities;

/**
 * Created by Александр Доротенко on 07.11.2016.
 */
public class Tariff {

    public Tariff(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Operator operator;
    private int id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
