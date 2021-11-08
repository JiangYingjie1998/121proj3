/**
 * @author Felix Jiang
 * @since 2021/11/6 17:34
 */
public class Res<E> {
    E e;

    public Res(E e) {
        this.e = e;
    }

    public E isNotNull() {
        if (e == null) {
            throw new IllegalArgumentException(e + " cannot be null");
        }
        return e;
    }

    public E isNull() {
        if (e != null) {
            throw new IllegalArgumentException("Object should be null");
        }
        return e;
    }

    public E isEqualTo(Object o2) {
        if (!e.equals(o2)) {
            throw new IllegalArgumentException(e + " does not equal to " + o2);
        }
        return e;
    }

    public E isNotEqualTo(Object o2) {
        if (e.equals(o2)) {
            throw new IllegalArgumentException("Object should be null");
        }
        return e;
    }

    public E isInstanceOf(Class<?> c) {
        if (!c.isInstance(e)) {
            throw new IllegalArgumentException("Object e is not an instance of class " + c.getName());
        }
        return e;
    }

    public E startWith(String s2) {
        Assertion.assertThat(s2).isNotNull();

        this.isNotNull();

        if (!(e instanceof String s)) {
            throw new IllegalArgumentException("The params should be Type String");
        }
        if (!s.startsWith(s2)) {
            throw new IllegalArgumentException(s + "does not start with " + s2);
        }
        return e;
    }

    public E isEmpty() {
        this.isNotNull();
        if (!(e instanceof String s)) {
            throw new IllegalArgumentException("The params should be Type String");
        }
        if (s.length() > 0) {
            throw new IllegalArgumentException("The String " + s + " should be en empty string");
        }
        return e;
    }

    public E contains(String s2) {
        Assertion.assertThat(s2).isNotNull();
        this.isNotNull();
        if (!(e instanceof String s)) {
            throw new IllegalArgumentException("The params should be Type String");
        }
        if (!s.contains(s2)) {
            throw new IllegalArgumentException("The String " + s + "does not contain " + s2);
        }
        return e;
    }

    public E isEqualTo(boolean b2) {
        if (!(e instanceof Boolean b1)) {
            throw new IllegalArgumentException("The params should be Type Boolean");
        }
        if (b1 != b2) {
            throw new IllegalArgumentException(e + " does not equal to " + b2);
        }
        return e;
    }

    public E isTrue() {
        if (!(e instanceof Boolean b)) {
            throw new IllegalArgumentException("Param must be Type-Boolean");
        }
        if (!b) {
            throw new IllegalArgumentException("must be True.");
        }
        return e;
    }

    public E isFalse() {
        if (!(e instanceof Boolean b)) {
            throw new IllegalArgumentException("Param must be Type-Boolean");
        }
        if (b) {
            throw new IllegalArgumentException("must be True.");
        }
        return e;
    }

    public E isEqualTo(int i2) {
        Assertion.assertThat(i2).isNotNull();
        if (!(e instanceof Integer i)) {
            throw new IllegalArgumentException("The params should be Type Integer");
        }
        if (i != i2) {
            throw new IllegalArgumentException(e + " does not equal to " + i2);
        }
        return e;
    }

    public E isLessThan(int i2) {
        Assertion.assertThat(i2).isNotNull();
        if (!(e instanceof Integer i)) {
            throw new IllegalArgumentException("The params should be Type Integer");
        }
        if (i >= i2) {
            throw new IllegalArgumentException(e + " is not less then " + i2);
        }
        return e;
    }

    public E isGreaterThan(int i2) {
        Assertion.assertThat(i2).isNotNull();
        if (!(e instanceof Integer i)) {
            throw new IllegalArgumentException("The params should be Type Integer");
        }
        if (i <= i2) {
            throw new IllegalArgumentException(e + " is not greater than " + i2);
        }
        return e;
    }


}
