package store.util;

import store.view.OutputView;

public interface Task<T> {
    static <T> T retryTillNoException(Task<T> task) {
        while (true) {
            try {
                return task.run();
            } catch (IllegalArgumentException e) {
                OutputView.println(e.getMessage());
            }
        }
    }

    T run();
}
