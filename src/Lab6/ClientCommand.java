package Lab6;

public enum ClientCommand {
    remove_all (false),
    remove (false),
    show (true),
    add_if_max (false),
    info (true),
    insert (false),
    remove_lower (false),
    exit (false),
    help (true),
    other (true),
    get_map (false),

    auth(false);
    private final boolean local;

    ClientCommand(boolean local) { this.local = local; }

    public boolean isLocal() { return local;  }
}
