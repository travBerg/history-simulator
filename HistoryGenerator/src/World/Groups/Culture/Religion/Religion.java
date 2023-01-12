package World.Groups.Culture.Religion;

import World.Groups.Culture.Religion.Categories.ReligionCategory;

public class Religion {
    private final ReligionCategory category;
    private final ReligionCategory.Sub subcategory;

    public Religion(final ReligionCategory category, final ReligionCategory.Sub subcategory) {
        this.category = category;
        this.subcategory = subcategory;
    }

    public ReligionCategory getCategory() { return category; }
    public ReligionCategory.Sub getSubcategory() {return subcategory;}
}
