package World.Groups.Culture.Religion.Categories;

public enum ReligionCategory {
    //Belief in at least one god
    THEISM(new Sub[]{Sub.MONOTHEISM, Sub.POLYTHEISM,
            Sub.HENOTHEISM, Sub.MONOLATRISM, Sub.DEISM}),
    //Instead of belief in a superior, divine being, belief in a set of ethical or moral values
    NONTHEISM(new Sub[]{Sub.ENLIGHTENMENT, Sub.HARMONY}),
    //Belief in spirits which influence human behavior and nature
    ANIMISM(new Sub[]{Sub.PLURALIST_ANIMISM, Sub.PANTHEISM}),
    //Worship of a particular symbol that provides unity or supernatural protections
    TOTEMISM(new Sub[]{Sub.COMMUNITY, Sub.INDIVIDUAL});

    ReligionCategory(final Sub[] subcategories) {
        this.subs = subcategories;
    }

    private Sub[] subs;

    public Sub[] getSubs(){ return subs; }

    public enum Sub {
        //------------THEISM-------------
        MONOTHEISM(),
        POLYTHEISM(),
        //Believe in one god but cool with others
        HENOTHEISM(),
        //Many gods but only one worth worshipping
        MONOLATRISM(),
        //God created the world and then peaced out forever
        DEISM(),
        //---------NON-THEISM-------------
        //Focus on individual transcendence
        ENLIGHTENMENT(),
        //Focus on social harmony between people
        HARMONY(),
        //----------ANIMISM---------------
        //Ancestor worship, naturism, supernaturalism (demons, etc), or any combination thereof
        PLURALIST_ANIMISM(),
        //Everything is a part of the same spirit
        PANTHEISM(),
        //---------TOTEMISM---------------
        //Totemism representing clans or other social groups for the sake of unity
        COMMUNITY(),
        //Totems as symbols, protectors, and companions of individuals
        INDIVIDUAL()
    }
}
