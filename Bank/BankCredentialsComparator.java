package Bank;

import java.util.Comparator;

/**
 * Compares two Bank objects by their passcode (credentials).
 * Null passcodes are treated as empty strings.
 */
public class BankCredentialsComparator implements Comparator<Bank> {

    @Override
    public int compare(Bank b1, Bank b2) {
        String cred1 = b1.getPasscode();
        String cred2 = b2.getPasscode();

        if (cred1 == null) cred1 = "";
        if (cred2 == null) cred2 = "";

        return cred1.compareTo(cred2);
    }
}
