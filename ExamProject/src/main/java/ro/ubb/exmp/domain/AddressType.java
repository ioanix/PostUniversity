package ro.ubb.exmp.domain;

public enum AddressType {

    HOME("Home"), WORK("Work");

    private String type;

    AddressType(String type) {

        this.type = type;
    }

    public String getAddressType() {

        return this.type;
    }
}
