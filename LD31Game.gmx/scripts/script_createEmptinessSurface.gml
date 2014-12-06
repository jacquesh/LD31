///script_createEmptinessSurface()
global.emptinessSurface = surface_create(global.backgroundWidth,
                                         global.backgroundHeight);
surface_set_target(global.emptinessSurface);
draw_clear_alpha(c_black, 0);
surface_reset_target();
