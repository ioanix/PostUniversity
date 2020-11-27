package model;

public abstract class Entity<ID> {

    private ID id;

    public ID getId() {

        return id;
    }

    public void setId(ID id) {

        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
