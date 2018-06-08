package tech.coinflip.utils.jiblib;

public class CoinFlipJibberishDetector implements JibberishDetector {

    private static final CoinFlipJibberishDetector INSTANCE = new CoinFlipJibberishDetector();

    /**
     * Accessor for the singleton instance.
     *
     * @return the singleton instance of <bb>CoinFlipJibberishDetector</bb>
     */
    public static CoinFlipJibberishDetector getInstance() {
        return INSTANCE;
    }

    private static final int MAX_VOWELS = 4; // Louie
    private static final int MAX_CONSONANTS = 7; // HIRSCHSPRUNG, SCHTSCHUROWSKIA, TSKTSKS
    private static final String CONSONANTS = "bcdfghjklmnpqrstvwxyz";
    private static final String VOWELS = "aeiou";
    private static final String VOWELS_INCLUSIVE = "aeiouy";

    @Override
    public DetectionResult detectIsJibberish(String input) {
        String[] tokens = input.split("\\s|-");
        if (tokens.length <= 0) {
            // no valid tokens to analyze
            return DetectionResult.NO_VALID_TOKENS;
        }
        for (String token : tokens) {
            String part = token.toLowerCase();
            if (part.length() < 2) {
                // length is too short
                return DetectionResult.TOKEN_TOO_SHORT;
            }
            if (part.matches("[a-z]\\.")) {
                // string is an initial
                continue;
            }
            for (int i = 0; i < part.length(); i++) {
                if (!Character.isAlphabetic(part.codePointAt(i))) {
                    // string has invalid characters
                    return DetectionResult.HAS_INVALID_CHARACTERS;
                }
            }
            if (part.length() >= 3) {
                boolean hasVowel = false;
                for (int i = 0; i < part.length(); i++) {
                    if (isVowelInclusive(part.charAt(i))) {
                        hasVowel = true;
                        break;
                    }
                }
                if (!hasVowel) {
                    // name must contain a vowel
                    return DetectionResult.MISSING_VOWEL;
                }
                int max = part.length() - 1;
                for (int i = 1; i < max; i++) {
                    char a = part.charAt(i - 1);
                    char b = part.charAt(i);
                    char c = part.charAt(i + 1);
                    if (a == b && b == c) {
                        // three consecutive letters should not be equal
                        return DetectionResult.CONSECUTIVE_CHARACTERS;
                    }
                }
                if (countVowels(part) > MAX_VOWELS) {
                    // no more than 3 consecutive vowels allowed
                    return DetectionResult.CONSECUTIVE_VOWELS;
                }
                if (countConsonants(part) > MAX_CONSONANTS) {
                    // no more than 7 consecutive consonants allowed
                    return DetectionResult.CONSECUTIVE_CONSONANTS;
                }
            } else {
                char a = part.charAt(0);
                char b = part.charAt(1);
                if (a == b) {
                    // 2 letter name must not consist of two of the same letter
                    return DetectionResult.REPETATIVE_COUPLE;
                }
            }
        }
        return DetectionResult.IS_VALID;
    }

    private static int countVowels(String token) {
        int max = 0, count = 0;
        for (int i = 0; i < token.length(); i++) {
            if (isVowel(token.charAt(i))) {
                count++;
                if (max < count) {
                    max = count;
                }
            } else {
                count = 0;
            }
        }
        return max;
    }

    private static boolean isVowel(char c) {
        for (int i = 0; i < VOWELS.length(); i++) {
            if (VOWELS.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    private static boolean isVowelInclusive(char c) {
        for (int i = 0; i < VOWELS_INCLUSIVE.length(); i++) {
            if (VOWELS_INCLUSIVE.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    private static int countConsonants(String token) {
        int max = 0, count = 0;
        for (int i = 0; i < token.length(); i++) {
            if (isConsonant(token.charAt(i))) {
                count++;
                if (max < count) {
                    max = count;
                }
            } else {
                count = 0;
            }
        }
        return max;
    }

    private static boolean isConsonant(char c) {
        for (int i = 0; i < CONSONANTS.length(); i++) {
            if (CONSONANTS.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

}
