public class Assertion {
    /* You'll need to change the return type of the assertThat methods */



    static Object assertThat(Object o) {
        return new Res<>(o);
    }

    static Res<String> assertThat(String s) {
        return new Res<>(s);
    }

    static Res<Boolean> assertThat(boolean b) {
        return new Res<>(b);
    }

    static Res<Integer> assertThat(int i) {
        return new Res<>(i);
    }
}