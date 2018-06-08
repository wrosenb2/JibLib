package tech.coinflip.utils.jiblib;

/**
 * Interface wrapper for the gibberish detection methods, so that you may create a way to implement other techniques later.
 * It is recommended that for now you use the <bb>CoinFlipJibberishDetector</bb> provided with this interface.
 * @see CoinFlipJibberishDetector
 * @see CoinFlipJibberishDetector#getInstance()
 */
public interface JibberishDetector {

    /**
     * Detect if the given input string is gibberish.
     *
     * @param input The string (propbably name(s)) that should be tested.
     * @return true if <bb>input</bb> is gibberish and false if not
     * @see #detectIsJibberish(String)
     * @implNote The default implementation is to use <code>detectIsJibberish(input).isValid()</code>.
     */
    default boolean isJibberish(String input) {
        return detectIsJibberish(input).isValid();
    }

    /**
     * Detect if the given input string is gibberish, returning useful error reporting.
     *
     * @param input The string (propbably name(s)) that should be tested.
     * @return <bb>DetectionResult</bb> representing the validity of the input.
     * @see #isJibberish(String)
     */
    DetectionResult detectIsJibberish(String input);


    /**
     *  <bb>Enum</bb> representing the result of testing input as either gibberish or not.
     */
    enum DetectionResult {
        /**
         * The input was not gibberish.
         */
        IS_VALID(true, "The input was not gibberish."),
        /**
         * The input could not be tokenized into valid parts.
         */
        NO_VALID_TOKENS(false, "The input could not be tokenized into valid parts."),
        /**
         * Found a token part with a length less than the minimum of two.
         */
        TOKEN_TOO_SHORT(false, "Found a token part with a length less than the minimum of two."),
        /**
         * Found a token with invalid (non-alphabetic) characters.
         */
        HAS_INVALID_CHARACTERS(false, "Found a token with invalid (non-alphabetic) characters."),
        /**
         * Tokens with length > 2 must have at least one vowel (including 'y').
         */
        MISSING_VOWEL(false, "Tokens with length > 2 must have at least one vowel (including 'y')."),
        /**
         * Tokens cannot include three consecutive equal characters.
         */
        CONSECUTIVE_CHARACTERS(false, "Tokens cannot include three consecutive equal characters."),
        /**
         * Tokens cannot include more than four consecutive vowels.
         */
        CONSECUTIVE_VOWELS(false, "Tokens cannot include more than four consecutive vowels."),
        /**
         * Tokens cannot include more than seven consecutive consonants.
         */
        CONSECUTIVE_CONSONANTS(false, "Tokens cannot include more than seven consecutive consonants."),
        /**
         * Tokens of length = 2 cannot be repetitions of the same letter.
         */
        REPETATIVE_COUPLE(false, "Tokens of length = 2 cannot be repetitions of the same letter.");

        private boolean isValid;
        private String message;

        DetectionResult(boolean isValid, String message) {
            this.isValid = isValid;
            this.message = message;
        }

        /**
         * Determines whether this result represents that an input is successfully validated as not gibberish.
         *
         * @return true if the input was not gibberish and false if it was.
         */
        public boolean isValid() {
            return isValid;
        }

        @Override
        public String toString() {
            return message;
        }
    }
}
