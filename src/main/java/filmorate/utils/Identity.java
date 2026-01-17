package filmorate.utils;

public enum Identity {
    INSTANCE;
    private int IdentityFilm = 1;
    private int IdentityUser = 1;

    public int generatedIdFilm() {
        return IdentityFilm++;
    }

    public int generatedIdUser() {
        return IdentityUser++;
    }

    public void setIdentityFilm(int identityFilm) {
        IdentityFilm = identityFilm;
    }

    public void setIdentityUser(int identityUser) {
        IdentityUser = identityUser;
    }
}
