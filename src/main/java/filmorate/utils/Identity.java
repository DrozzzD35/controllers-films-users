package filmorate.utils;

import java.util.concurrent.atomic.AtomicInteger;

public enum Identity {
    INSTANCE;
    private final AtomicInteger identityFilm = new AtomicInteger(1);
    private final AtomicInteger identityUser = new AtomicInteger(1);

    public int generatedIdFilm() {
        return identityFilm.getAndIncrement();
    }

    public int generatedIdUser() {
        return identityUser.getAndIncrement();
    }

}
