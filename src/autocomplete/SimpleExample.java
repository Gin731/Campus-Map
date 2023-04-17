package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleExample {
    public static void main(String[] args) {
        List<CharSequence> terms = new ArrayList<>();
        terms.add("d");
        terms.add("b");
        terms.add("abc");
        terms.add("ab");
        terms.add("bdh");
        terms.add("bde");

        System.out.println("Before: " + terms);
        Collections.sort(terms, CharSequence::compare);
        System.out.println(" After: " + terms);

        System.out.println();

        CharSequence prefix = "bd";
        System.out.println("prefix:  " + prefix);
        int i = Collections.binarySearch(terms, prefix, CharSequence::compare);
        System.out.println("     i: " + i);
        if (i >= 0) {
            // i >= 0 when an exact match for the prefix was found
            System.out.println(" start:  " + i);
        } else {
            // i < 0 when the prefix does not exactly match any of the terms
            System.out.println(" start:  " + -(i + 1));
        }
    }
}
