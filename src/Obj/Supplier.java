package Obj;

public class Supplier extends Person {
    private final int ID;

    /**
     * @param name the name of the Supplier.
     * @param contactNumber 11 digit contact number.
     * @param firstLineAddress first line of address.
     * @param secondLineAddress second line of address.
     * @param postcode postcode.
     * @param ID unique id number for supplier.
     */
    Supplier(String name, String contactNumber, String firstLineAddress, String secondLineAddress, String postcode, int ID){
        this.ID = ID;
        setName(name);
        setContactNumber(contactNumber);
        setPostcode(postcode);
        setFirstLineAddress(firstLineAddress);
        setSecondLineAddress(secondLineAddress);
    }

    public int getID(){
        return ID;
    }

    public String toString(){
        return String.format("Name: %s\nContact Num: %s\nAddress: %s\n%s\n%s\nID: %d",getName(),getContactNumber(),getFirstLineAddress(),getSecondLineAddress(),getPostcode(),getID());
    }
}