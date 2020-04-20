import java.util.TreeSet;
import java.util.Set;

/**
 * Skeleton implementation for classes capable of providing spelling corrections.
 * <p>
 * The matchCase method is code that is shared among all Correctors.
 * <p>
 * Concrete subclasses of this abstract class must implement the getCorrections method (which will
 * usually call matchCase).
 */
public abstract class Corrector {

    /**
     * Returns a new set that contains the same words as the argued set but with the case matching
     * that of the argued misspelled word (either all lowercase or first letter uppercase). The case
     * to match is determined by the first letter of the misspelled word.
     *
     * Examples:
     *<ul>
     * <li>matchCase("Wrng", { "wrong" }) returns { "Wrong" }
     * <li>matchCase("wRng", { "wrong" }) returns { "wrong" }
     * <li>matchCase("WRNG", { "wrong" }) returns { "Wrong" }
     * <li>matchCase("wrng", { "wrong" }) returns { "wrong" }
     *
     * @param incorrectWord The word whose case should be matched
     * @param corrections The set whose case should be fixed
     * @return Set of corrections with case appropriately modified
     * @throws IllegalArgumentException If either argument is null
     */
    public Set<String> matchCase(String incorrectWord, Set<String> corrections) {
        if (incorrectWord == null || corrections == null) {
            throw new IllegalArgumentException("null input given");
        }

        Set<String> revisedSet = new TreeSet<String>();
        boolean capitalizeFirst = Character.isUpperCase(incorrectWord.charAt(0));
        for (String s : corrections) {
            String correctCase = capitalizeFirst ?
                s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() : s.toLowerCase();
            
            revisedSet.add(correctCase);
        }

        return revisedSet;
    }

    /**
     * Returns a set of proposed corrections for an incorrectly spelled word. The corrections should
     * match the case of the input; the matchCase method is helpful here.
     * <p>
     * For any input that is *not* a valid word, throw an IllegalArgumentException. A valid word is
     * any sequence of letters (as determined by Character.isLetter) or apostrophes characters.
     *
     * @param wrong The misspelled word
     * @return A (potentially empty) set of proposed corrections
     * @throws IllegalArgumentException If the input is not a valid word (i.e. not composed of only
     *                                  letters and/or apostrophes) 
     */
    public abstract Set<String> getCorrections(String wrong);
}
