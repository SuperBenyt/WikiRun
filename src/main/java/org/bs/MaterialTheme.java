package org.bs;

import mdlaf.themes.AbstractMaterialTheme;
import mdlaf.utils.MaterialColors;

public class MaterialTheme extends AbstractMaterialTheme {

    @Override
    protected void installColor() {
        this.backgroundPrimary = MaterialColors.BLUE_GRAY_900;
        this.highlightBackgroundPrimary = MaterialColors.COSMO_RED;
        this.textColor = MaterialColors.BLACK;
    }

    @Override
    public String getName() {
        return "Material Theme";
    }
}
