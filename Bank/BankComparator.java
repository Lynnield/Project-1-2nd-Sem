package Bank;

import java.util.Comparator;

/**
 * Compares two Bank objects by their bankName (alphabetically).
 */
public class BankComparator implements Comparator<Bank> {

    @Override
    public int compare(Bank b1, Bank b2) {
        // Compare names ignoring case.
        return b1.getBankName().compareToIgnoreCase(b2.getBankName());
    }
}
