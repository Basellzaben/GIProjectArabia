package com.cds_jo.GalaxySalesApp;

public enum Companies {
    bustanji (1),
    bristage(2),
    salsel(3),
    goodsystem(4),
    tariget(5),
    Arabian (6) ,
    Ukrania(7),
    beutyLine(8),
    Saad(9),
    Ma8bel(10),
    Sector(11),
    Afrah(12),
    abohaltam(13),
   electronic(14),
    diamond(16),
    Atls(17),
    nwaah(18);
    private final int value;
    Companies(int type)
    {
        this.value = type;
    }


    public int getValue() {

        return this.value;

    }

}
