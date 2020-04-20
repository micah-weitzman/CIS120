import java.util.HashSet;
import java.util.Set;

/**
 * A smart Corrector that uses "edit distance" to generate corrections.
 * <p>
 * NOTE: This corrector is a "challenge" problem and does not count towards your grade.
 * <p>
 * The Levenshtein distance between two words is the smallest number of edits needed to transform
 * one word to the other. An "edit" can be either an:
 * <ul>
 * <li> insertion of a letter
 * <li> deletion of a letter
 * <li> substitution of one letter for one other letter
 * </ul>
 *
 * A "letter" is one of a-z (we do not count apostrophes here).
 * Note that swaps (thsi -> this) do <it>not</it> count as an edit.
 * <p>
 * This Corrector suggests any words in the dictionary that are exactly a distance of one edit away
 * from the given incorrect word.
 */
public class Levenshtein extends Corrector {
    

    /**
    * Constructs a Levenshtein Corrector using the argued Dictionary. Should throw an
    * <code>IllegalArgumentException</code> if the aruged Dictionary is null.
    *
    * @param dict The Dictionary for this corrector
    * @throws IllegalArgumentException If the argued Dictionary is null
    */
    public Levenshtein(Dictionary dict) {
        throw new UnsupportedOperationException();
    }

    /**
     * A deletion is defined as a removing a single character. For example, deletions for the word
     * "yes" are { "es", "ys", "ye" }
     *
     * @param s The input string
     * @return All the words that are one deletion away from s
     */
    public Set<String> getDeletions(String s) {
        throw new UnsupportedOperationException();
    }

    /**
     * A substitution is defined as replacing a single character with another. Here, we will only
     * substitute with the lowercase letters a-z. For example, some of the substitutions for "yes"
     * include { "aes", "bes", "ces", ... "yex", "yey", "yez" }
     *
     * @param s The input string
     * @return All the words that are one substitution away from s
     */
    public Set<String> getSubstitutions(String s) {
        throw new UnsupportedOperationException();
    }

    
    /**
     * An insertion is defined as inserting a single character. Here, we will only insert lowercase
     * letters a-z. For example, some of insertions for "yes" include
     * { "ayes", "byes", "cyes", ... "yaes", "ybes", "yces", ... "yesx", "yesy", "yesz" }
     *
     * @param s The input string
     * @return All words that are one insertion away from s
     */
    public Set<String> getInsertions(String s) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a set of proposed corrections for an incorrectly spelled word. The corrections should
     * match the case of the input; the matchCase method is helpful here.
     * <p>
     * For any input that is *not* a valid word, throw an IllegalArgumentException. A valid word is
     * any sequence of letters (as determined by Character.isLetter) or apostrophes characters.
     *
     * The set of corrections will be all of the corrections that are a single deletion or single
     * insertion or single substitution away from the argued word according to the above defintions.
     *
     * @param wrong The misspelled word
     * @return A (potentially empty) set of proposed corrections
     * @throws IllegalArgumentException If the input is not a valid word (i.e. not composed of only
     *                                  letters and/or apostrophes) 
     */
    public Set<String> getCorrections(String wrong) {
        throw new UnsupportedOperationException();
    }
}
