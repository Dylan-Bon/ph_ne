package Obj;

public class Customer extends Person {
    private final int ID;
    private String surname;

    /**
     * @param name first name of Customer.
     * @param surname last name of Customer.
     * @param contactNumber 11 digit contact number.
     * @param firstLineAddress first line of address.
     * @param secondLineAddress second line of address.
     * @param postcode the postcode of the address
     * @param ID unique ID of the Customer
     */
    Customer(String name, String surname, String contactNumber, String firstLineAddress, String secondLineAddress, String postcode, int ID){
        this.ID = ID;
        setName(name);
        setSurname(surname);
        setContactNumber(contactNumber);
        setPostcode(postcode);
        setFirstLineAddress(firstLineAddress);
        setSecondLineAddress(secondLineAddress);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getID(){
        return ID;
    }

    public String toString(){
        return String.format("Name: %s %s\nContact Num: %s\nAddress: %s\n%s\n%s\nID: %d",getName(),getSurname(),getContactNumber(),getFirstLineAddress(),getSecondLineAddress(),getPostcode(),getID());
    }
}
