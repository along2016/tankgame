package util;

public enum TankLevel {
    PRIMARY("PRIMARY", 1), MIDDLE("MIDDLE", 2), SENIOR("SENIOR", 3);

    TankLevel(String code, int level){
        this.code = code;
        this.level = level;
    }
    private String code;
    private int level;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
