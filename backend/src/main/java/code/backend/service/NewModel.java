package code.backend.service;

public class NewModel {
    String id;
    String name;
    double n1;
    double n2;
    double n3;

    public NewModel(String id, String name, double n1, double n2, double n3) {
        this.id = id;
        this.name = name;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getN1() {
        return this.n1;
    }

    public void setN1(double n1) {
        this.n1 = n1;
    }

    public double getN2() {
        return this.n2;
    }

    public void setN2(double n2) {
        this.n2 = n2;
    }

    public double getN3() {
        return this.n3;
    }

    public void setN3(double n3) {
        this.n3 = n3;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", n1='" + getN1() + "'" +
            ", n2='" + getN2() + "'" +
            ", n3='" + getN3() + "'" +
            "}";
    }

}
