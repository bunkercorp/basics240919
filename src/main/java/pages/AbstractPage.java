package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.*;

abstract class AbstractPage {
    public static class ReturnResult {
        private Object result;
        private ArrayList<String> errors;

        private ReturnResult(Object r, ArrayList<String> e) {
            result = r;
            errors = e;
        }

        public static class Builder {
            private Object result = null;
            private ArrayList<String> errors = new ArrayList<>();

            public Builder setResult(Object r) {
                result = r;
                return this;
            }

            public Builder addError(ReturnResult other) {
                errors.addAll(other.errors);
                return this;
            }

            public Builder addError(String errorString) {
                errors.add(errorString);
                return this;
            }

            public Builder addError(Throwable t) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("\t%s", t.getMessage()));
                Arrays.stream(t.getStackTrace()).forEach(sti -> {
                    sb.append(String.format("\t\t%s", sti.toString()));
                });
                errors.add(sb.toString());
                return this;
            }

            public Object getResult() {
                return result;
            }

            public ReturnResult compose() {
                return new ReturnResult(result, errors);
            }
        }

        public Object getResult() {
            return result;
        }

        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }

    }


    protected WebDriver storedDriver;

    protected final Map<String, By> selectors = new HashMap<>();

    public AbstractPage with(WebDriver driver) {
        storedDriver = driver;
        return this;
    }

    public abstract ReturnResult visit(Object... pageSpecificArguments);

    public abstract ReturnResult isOnScreen();

}
