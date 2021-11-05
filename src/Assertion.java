public class Assertion {
    /* You'll need to change the return type of the assertThat methods */
    static Object assertThat(Object o) {
        String className = o.getClass().getName();
        throw new UnsupportedOperationException();
    }

    static Object assertThat(String s) {
        throw new UnsupportedOperationException();
    }

    static Object assertThat(boolean b) {
        throw new UnsupportedOperationException();
    }

    static Object assertThat(int i) {
        throw new UnsupportedOperationException();
    }
}