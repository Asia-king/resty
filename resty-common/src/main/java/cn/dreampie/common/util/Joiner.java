package cn.dreampie.common.util;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import static cn.dreampie.common.util.Checker.checkNotNull;

/**
 * Created by ice on 14-12-29.
 */
public class Joiner {
  private final String separator;

  private Joiner(String separator) {
    this.separator = checkNotNull(separator);
  }

  private Joiner(Joiner prototype) {
    this.separator = prototype.separator;
  }

  /**
   * Returns a joinKit which automatically places {@code separator} between consecutive elements.
   * @param separator s
   * @return this a
   */
  public static Joiner on(String separator) {
    return new Joiner(separator);
  }

  /**
   * Returns a joinKit which automatically places {@code separator} between consecutive elements.
   * @param separator s
   * @return this a
   */
  public static Joiner on(char separator) {
    return new Joiner(String.valueOf(separator));
  }

  private static Iterable<Object> iterable(
      final Object first, final Object second, final Object[] rest) {
    checkNotNull(rest);
    return new AbstractList<Object>() {
      public int size() {
        return rest.length + 2;
      }

      public Object get(int index) {
        switch (index) {
          case 0:
            return first;
          case 1:
            return second;
          default:
            return rest[index - 2];
        }
      }
    };
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code appendable}.
   * @param <A> s
   * @param appendable s
   * @param parts s
   * @return this a
   * @throws IOException s
   */
  public <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
    return appendTo(appendable, parts.iterator());
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code appendable}.
   *@param <A> s
   *@param appendable s
   *@param parts s
   *@return s s
   *@throws IOException f
   * @since 11.0
   */
  public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
    checkNotNull(appendable);
    if (parts.hasNext()) {
      appendable.append(toString(parts.next()));
      while (parts.hasNext()) {
        appendable.append(separator);
        appendable.append(toString(parts.next()));
      }
    }
    return appendable;
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code appendable}.
   *@param <A> s
   *@param appendable s
   *@param parts s
   *@return s s
   *@throws IOException f
   */
  public final <A extends Appendable> A appendTo(A appendable, Object[] parts) throws IOException {
    return appendTo(appendable, Arrays.asList(parts));
  }

  /**
   * Appends to {@code appendable} the string representation of each of the remaining arguments.
   * @param appendable s
   * @param first s
   * @param second s
   * @param <A> s
   * @param rest s
   * @throws IOException s
   * @return s
   */
  public final <A extends Appendable> A appendTo(
      A appendable, Object first, Object second, Object... rest)
      throws IOException {
    return appendTo(appendable, iterable(first, second, rest));
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code builder}. Identical to {@link #appendTo(Appendable,
   * Iterable)}, except that it does not throw {@link IOException}.
   *@param builder s
   *@param parts s
   *@return s s
   */
  public final StringBuilder appendTo(StringBuilder builder, Iterable<?> parts) {
    return appendTo(builder, parts.iterator());
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code builder}. Identical to {@link #appendTo(Appendable,
   * Iterable)}, except that it does not throw {@link IOException}.
   * @param builder s
   * @param parts s
   * @return s
   * @since 11.0
   */
  public final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts) {
    try {
      appendTo((Appendable) builder, parts);
    } catch (IOException impossible) {
      throw new AssertionError(impossible);
    }
    return builder;
  }

  /**
   * Appends the string representation of each of {@code parts}, using the previously configured
   * separator between each, to {@code builder}. Identical to {@link #appendTo(Appendable,
   * Iterable)}, except that it does not throw {@link IOException}.
   * @param builder s
   * @param parts s
   * @return s
   */
  public final StringBuilder appendTo(StringBuilder builder, Object[] parts) {
    return appendTo(builder, Arrays.asList(parts));
  }

  /**
   * Appends to {@code builder} the string representation of each of the remaining arguments.
   * Identical to {@link #appendTo(Appendable, Object, Object, Object...)}, except that it does not
   * throw {@link IOException}.
   * @param builder s
   * @param first s
   * @param second s
   * @param rest s
   * @return s
   */
  public final StringBuilder appendTo(
      StringBuilder builder, Object first, Object second, Object... rest) {
    return appendTo(builder, iterable(first, second, rest));
  }

  /**
   * Returns a string containing the string representation of each of {@code parts}, using the
   * previously configured separator between each.
   * @param parts s 
   * @return s s
   */
  public final String join(Iterable<?> parts) {
    return join(parts.iterator());
  }

  /**
   * Returns a string containing the string representation of each of {@code parts}, using the
   * previously configured separator between each.
   *
   * @since 11.0
   * @param parts s
   * @return s s
   */
  public final String join(Iterator<?> parts) {
    return appendTo(new StringBuilder(), parts).toString();
  }

  /**
   * Returns a string containing the string representation of each of {@code parts}, using the
   * previously configured separator between each.
   * @param parts s
   * @return s s
   */
  public final String join(Object[] parts) {
    return join(Arrays.asList(parts));
  }

  /**
   * Returns a string containing the string representation of each argument, using the previously
   * configured separator between each.
    * @param first s
    *  @param second s
    *   @param rest s
   * @return s s
   */
  public final String join(Object first, Object second, Object... rest) {
    return join(iterable(first, second, rest));
  }

  /**
   * Returns a joinKit with the same behavior as this one, except automatically substituting {@code
   * nullText} for any provided null elements.
   *@param nullText n
   *@return s s
   */

  public Joiner useForNull(final String nullText) {
    checkNotNull(nullText);
    return new Joiner(this) {
      CharSequence toString(Object part) {
        return (part == null) ? nullText : Joiner.this.toString(part);
      }

      public Joiner useForNull(String nullText) {
        throw new UnsupportedOperationException("already specified useForNull");
      }

      public Joiner skipNulls() {
        throw new UnsupportedOperationException("already specified useForNull");
      }
    };
  }

  /**
   * Returns a joinKit with the same behavior as this joinKit, except automatically skipping over any
   * provided null elements.
   * @return s s
   */

  public Joiner skipNulls() {
    return new Joiner(this) {
      public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts)
          throws IOException {
        checkNotNull(appendable, "appendable");
        checkNotNull(parts, "parts");
        while (parts.hasNext()) {
          Object part = parts.next();
          if (part != null) {
            appendable.append(Joiner.this.toString(part));
            break;
          }
        }
        while (parts.hasNext()) {
          Object part = parts.next();
          if (part != null) {
            appendable.append(separator);
            appendable.append(Joiner.this.toString(part));
          }
        }
        return appendable;
      }

      public Joiner useForNull(String nullText) {
        throw new UnsupportedOperationException("already specified skipNulls");
      }

      public MapJoiner withKeyValueSeparator(String kvs) {
        throw new UnsupportedOperationException("can't use .skipNulls() with maps");
      }
    };
  }

  /**
   * Returns a {@code MapJoiner} using the given key-value separator, and the same configuration as
   * this {@code Joiner} otherwise.
   * @param keyValueSeparator f
   * @return s s
   */

  public MapJoiner withKeyValueSeparator(String keyValueSeparator) {
    return new MapJoiner(this, keyValueSeparator);
  }

  CharSequence toString(Object part) {
    checkNotNull(part);  // checkNotNull for GWT (do not optimize).
    return (part instanceof CharSequence) ? (CharSequence) part : part.toString();
  }

  /**
   * An object that joins map entries in the same manner as {@code Joiner} joins iterables and
   * arrays. Like {@code Joiner}, it is thread-safe and immutable.
   * 
   * <p>In addition to operating on {@code Map} instances, {@code MapJoiner} can operate on {@code</p>
   * Multimap} entries in two distinct modes:
   * 
   * <ul>
   * <li>To output a separate entry for each key-value pair, pass {@code multimap.entries()} to a
   * {@code MapJoiner} method that accepts entries as input, and receive output of the form
   * {@code key1=A&key1=B&key2=C}.
   * <li>To output a single entry for each key, pass {@code multimap.asMap()} to a {@code MapJoiner}
   * method that accepts a map as input, and receive output of the form {@code
   * key1=[A, B]&key2=C}.
   * </ul>
   *
   * @since 2.0 (imported from Google Collections Library)
   */
  public static final class MapJoiner {
    private final Joiner joiner;
    private final String keyValueSeparator;

    private MapJoiner(Joiner joiner, String keyValueSeparator) {
      this.joiner = joiner; // only "this" is ever passed, so don't checkNotNull
      this.keyValueSeparator = checkNotNull(keyValueSeparator);
    }

    /**
     * Appends the string representation of each entry of {@code map}, using the previously
     * configured separator and key-value separator, to {@code appendable}.
     * @param <A> a
     * @param appendable a
     * @param map a
     * @throws IOException s
     * @return s s
     */
    public <A extends Appendable> A appendTo(A appendable, Map<?, ?> map) throws IOException {
      return appendTo(appendable, map.entrySet());
    }

    /**
     * Appends the string representation of each entry of {@code map}, using the previously
     * configured separator and key-value separator, to {@code builder}. Identical to {@link
     * #appendTo(Appendable, Map)}, except that it does not throw {@link IOException}.
     * @param builder a
     * @param map a
     * @return s s
     */
    public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
      return appendTo(builder, map.entrySet());
    }

    /**
     * Returns a string containing the string representation of each entry of {@code map}, using the
     * previously configured separator and key-value separator.
     * @param map a
     * @return s s
     */
    public String join(Map<?, ?> map) {
      return join(map.entrySet());
    }

    /**
     * Appends the string representation of each entry in {@code entries}, using the previously
     * configured separator and key-value separator, to {@code appendable}.
     * @param <A> a
     * @param appendable a
     * @param entries a
     * @throws IOException s
     * @return s s
     */

    public <A extends Appendable> A appendTo(A appendable, Iterable<? extends Map.Entry<?, ?>> entries)
        throws IOException {
      return appendTo(appendable, entries.iterator());
    }

    /**
     * Appends the string representation of each entry in {@code entries}, using the previously
     * configured separator and key-value separator, to {@code appendable}.
     * 
     * @param <A> a
     * @param appendable a
     * @param parts a
     * @throws IOException s
     * @return s s
     */

    public <A extends Appendable> A appendTo(A appendable, Iterator<? extends Map.Entry<?, ?>> parts)
        throws IOException {
      checkNotNull(appendable);
      if (parts.hasNext()) {
        Map.Entry<?, ?> entry = parts.next();
        appendable.append(joiner.toString(entry.getKey()));
        appendable.append(keyValueSeparator);
        appendable.append(joiner.toString(entry.getValue()));
        while (parts.hasNext()) {
          appendable.append(joiner.separator);
          Map.Entry<?, ?> e = parts.next();
          appendable.append(joiner.toString(e.getKey()));
          appendable.append(keyValueSeparator);
          appendable.append(joiner.toString(e.getValue()));
        }
      }
      return appendable;
    }

    /**
     * Appends the string representation of each entry in {@code entries}, using the previously
     * configured separator and key-value separator, to {@code builder}. Identical to {@link
     * #appendTo(Appendable, Iterable)}, except that it does not throw {@link IOException}.  
     * @param builder a
     * @param entries a
     * @return s s
     */

    public StringBuilder appendTo(StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> entries) {
      return appendTo(builder, entries.iterator());
    }

    /**
     * Appends the string representation of each entry in {@code entries}, using the previously
     * configured separator and key-value separator, to {@code builder}. Identical to {@link
     * #appendTo(Appendable, Iterable)}, except that it does not throw {@link IOException}.
          * @param builder a
     * @param entries a
     * @return s s
     */

    public StringBuilder appendTo(StringBuilder builder, Iterator<? extends Map.Entry<?, ?>> entries) {
      try {
        appendTo((Appendable) builder, entries);
      } catch (IOException impossible) {
        throw new AssertionError(impossible);
      }
      return builder;
    }

    /**
     * Returns a string containing the string representation of each entry in {@code entries}, using
     * the previously configured separator and key-value separator.
     * @param entries a
     * @return s s
     */

    public String join(Iterable<? extends Map.Entry<?, ?>> entries) {
      return join(entries.iterator());
    }

    /**
     * Returns a string containing the string representation of each entry in {@code entries}, using
     * the previously configured separator and key-value separator.
     * @param entries a
     * @return s s
     */

    public String join(Iterator<? extends Map.Entry<?, ?>> entries) {
      return appendTo(new StringBuilder(), entries).toString();
    }

    /**
     * Returns a map joinKit with the same behavior as this one, except automatically substituting
     * {@code nullText} for any provided null keys or values.
     * @param nullText a
     * @return s s
     */

    public MapJoiner useForNull(String nullText) {
      return new MapJoiner(joiner.useForNull(nullText), keyValueSeparator);
    }
  }
}
