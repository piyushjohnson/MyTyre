package piyushjohnson.mytyre.util;

public class Event<T> {

    private boolean hasBeenHandled = false;
    T value;

    public Event(T value) {

    }

    /*
     * Returns value and prevents its use again
     */
    public T getContentIfNeeded() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return value;
        }
    }

    /*
     * Returns the value even if its already handled
     */
}
