package main;

public enum Missile {
    CLASSIC(0, new boolean[][] { { true } }),
    RECO(3, new boolean[][] { { true, true, true }, { true, true, true }, { true, true, true } }),
    HEAVY(3, new boolean[][] { { false, true, false }, { false, true, false }, { false, true, false } });

    private boolean[][] zone;
    private int delay;

    private Missile(int delay, boolean[][] zone) {
        this.delay = delay;
        this.zone = zone;
    }

    public String getAllMissiles() {
        Missile[] missiles = Missile.values();
        String res = new String();
        int idx = 1;
        for (Missile missile : missiles) {
            res += idx + ") " + missile.name() + '\n';
            idx++;
        }
        return res;
    }

    public int getDelay() {
        return this.delay;
    }

    public boolean[][] getZone() {
        return this.zone;
    }
}