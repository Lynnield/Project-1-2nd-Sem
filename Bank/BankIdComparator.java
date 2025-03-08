package Bank;

import java.util.Comparator;

/**
 * Compares two Bank objects by their bankId.
 * If bankId is a String, it is compared lexically (ignoring case).
 */
public class BankIdComparator implements Comparator<Bank> {

    @Override
    public int compare(Bank b1, Bank b2) {
        return b1.getBankId().compareToIgnoreCase(b2.getBankId());
    }
}
