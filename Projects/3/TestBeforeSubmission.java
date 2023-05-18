public class TestBeforeSubmission {
    public static void main(String[] args) {
        Assertion.assertThat(true).isTrue().isEqualTo(true);
        Assertion.assertThat(new Object()).isNotNull().isInstanceOf(Object.class);
        Assertion.assertThat("String").isNotNull().startsWith("S");
        Assertion.assertThat(1).isLessThan(2).isGreaterThan(0);
    }
}
