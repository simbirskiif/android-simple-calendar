package com.govno228.pon;

import java.util.Date;

public class SelectionStaticData {
    static boolean isSelected;
    static SelectionData selectionData;
    static Date firstSelection;
    static Date secondSelection;
    static Selections selection = Selections.Single;
    static Date SingleSelection;

    public static void setSingleSelection() {
        selection = Selections.Single;
    }

    public static void setMultiplySelection() {
        selection = Selections.Multiply;
    }
}
