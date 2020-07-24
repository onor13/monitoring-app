package parser;

public interface Parser<T> {
  T parse(String value);
}
