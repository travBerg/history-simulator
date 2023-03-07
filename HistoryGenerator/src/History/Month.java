package History;

public enum Month {
    MID_WINTER(0, "Mid Winter"),
    LATE_WINTER(1, "Late Winter"),
    EARLY_SPRING(2, "Early Spring"),
    MID_SPRING(3, "Mid Spring"),
    LATE_SPRING(4, "Late Spring"),
    EARLY_SUMMER(5, "Early Summer"),
    MID_SUMMER(6, "Mid Summer"),
    LATE_SUMMER(7, "Late Summer"),
    EARLY_FALL(8, "Early Fall"),
    MID_FALL(9, "Mid Fall"),
    LATE_FALL(10, "Late Fall"),
    EARLY_WINTER(11, "Early Winter");

    private int order;
    private String name;

    Month(final int order, final String name) {
        this.order = order;
        this.name = name;
    }

    public String getName() { return name; }
    public int getOrder() { return order; }
    public static Month getMonthByOrder(final int order) {
        return Month.values()[order];
    }
}
