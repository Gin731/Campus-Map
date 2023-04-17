package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {

        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.terms.addAll(terms);
        Collections.sort(this.terms, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matchTerms = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return matchTerms;
        }

        int startIndex = Collections.binarySearch(terms, prefix, CharSequence::compare);
        if (startIndex < 0) {
            startIndex = -(startIndex + 1);
        }
        for(int i = startIndex; i < terms.size(); i++){
            CharSequence term = terms.get(i);
            if(isPrefixOf(prefix, term)){
                //System.out.println(term);
                matchTerms.add(term);
            }else{
                break;
            }
        }
        return matchTerms;
    }

    /** Returns true if and only if the given term matches the given prefix. */
    private static boolean isPrefixOf(CharSequence prefix, CharSequence term) {
        if (prefix.length() <= term.length()) {
            CharSequence part = term.subSequence(0, prefix.length());
            return CharSequence.compare(prefix, part) == 0;
        }
        return false;
    }

}
