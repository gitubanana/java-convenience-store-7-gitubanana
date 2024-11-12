package store.constant;

public enum Answer {
    YES("Y"),
    NO("N"),
    ;

    private final String message;

    Answer(String message) {
        this.message = message;
    }

    public static Answer getMatchingAnswer(String toFind) {
        for (Answer answer : Answer.values()) {
            if (answer.matches(toFind)) {
                return answer;
            }
        }
        return null;
    }

    private boolean matches(String answer) {
        return message.equals(answer);
    }

    public String getMessage() {
        return message;
    }
}
