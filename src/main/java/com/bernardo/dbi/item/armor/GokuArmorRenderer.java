package com.bernardo.dbi.item.armor;

import software.bernie.geckolib.renderer.GeoArmorRenderer;

/** Renderer da armadura do Goku no corpo do player. */
public class GokuArmorRenderer extends GeoArmorRenderer<GokuArmorItem> {

    public GokuArmorRenderer() {
        super(new GokuArmorModel());
    }
}
